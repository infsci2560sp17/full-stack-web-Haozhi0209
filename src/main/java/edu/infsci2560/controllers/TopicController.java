package edu.infsci2560.controllers;

import edu.infsci2560.models.*;
import edu.infsci2560.services.*;
import edu.infsci2560.kit.*;
import edu.infsci2560.Constant;
import edu.infsci2560.Actions;
import edu.infsci2560.exception.TipException;
import edu.infsci2560.Types;
import edu.infsci2560.dto.HomeTopic;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.blade.ioc.annotation.Inject;
import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;
import com.blade.kit.DateKit;
import com.blade.kit.PatternKit;
import com.blade.kit.StringKit;
import com.blade.kit.base.MapCache;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.view.ModelAndView;
import com.blade.mvc.view.RestResponse;
import com.blade.patchca.DefaultPatchca;
import com.blade.patchca.Patchca;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Controller
public class TopicController extends BasicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicController.class);

    @Inject
    private TopicService topicService;

    @Inject
    private TopicCountService topicCountService;

    // @Inject
    // private NodeService nodeService;

    @Inject
    private CommentService commentService;

    // @Inject
    // private SettingsService settingsService;

    // @Inject
    // private FavoriteService favoriteService;

    @Inject
    private UserService userService;

    @Inject
    private UserlogService userlogService;

    @Inject
    private TopicCountService typeCountService;

    private MapCache mapCache = MapCache.single();

    @Route(value = "/topic/add", method = HttpMethod.GET)
    public ModelAndView show_add_topic(Request request, Response response) {
        LoginUser user = SessionKit.getLoginUser();
        if (null == user) {
            response.go("/");
            return null;
        }
        // this.putData(request);
        Long pid = request.queryAsLong("pid");
        request.attribute("pid", pid);
        return this.getView("topic_add");
    }


    @Route(value = "/topic/edit/:tid", method = HttpMethod.GET)
    public ModelAndView show_ediot_topic(@PathParam("tid") Integer tid, Request request, Response response) {

        LoginUser user = SessionKit.getLoginUser();
        if (null == user) {
            response.go("/");
            return null;
        }

        Topic topic = topicService.getTopic(tid);
        if (null == topic) {
            request.attribute(this.ERROR, "None existing topic.");
            return this.getView("info");
        }

        if (!topic.getUid().equals(user.getUid())) {
            request.attribute(this.ERROR, "You have no authority to edit.");
            return this.getView("info");
        }

        // this.putData(request);
        request.attribute("topic", topic);

        return this.getView("topic_edit");
    }

    @Route(value = "/topic/edit", method = HttpMethod.POST)
    @JSON
    public RestResponse edit_topic(Request request, Response response) {
        Integer tid = request.queryAsInt("tid");
        String title = request.query("title");
        String content = request.query("content");
        Integer nid = request.queryAsInt("nid");

        LoginUser user = SessionKit.getLoginUser();
        if (null == user) {
            return RestResponse.fail(401);
        }

        if (null == tid) {
            return RestResponse.fail("None existing topic.");
        }

        Topic topic = topicService.getTopic(tid);
        if (null == topic) {
            return RestResponse.fail("None existing topic.");
        }

        if (!topic.getUid().equals(user.getUid())) {
            return RestResponse.fail("You have no authority to edit.");
        }

        try {
            topicService.update(tid, nid, title, content);
            userlogService.save(user.getUid(), Actions.UPDATE_TOPIC, content);

            return RestResponse.ok(tid);
        } catch (Exception e) {
            String msg = "Editing topic failed.";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return RestResponse.fail(msg);
        }
    }

    @Route(value = "/topic/add", method = HttpMethod.POST)
    @JSON
    public RestResponse publish(@QueryParam String title,
                                @QueryParam String content,
                                @QueryParam Integer nid) {

        LoginUser user = SessionKit.getLoginUser();
        if (null == user) {
            return RestResponse.fail(401);
        }

        try {
            Topic topic = new Topic();
            topic.setUid(user.getUid());
            topic.setNid(nid);
            topic.setTitle(title);
            topic.setContent(content);
            topic.setIs_top(0);
            Integer tid = topicService.publish(topic);
            // Constant.SYS_INFO = settingsService.getSystemInfo();
            // Constant.VIEW_CONTEXT.set("sys_info", Constant.SYS_INFO);
            userlogService.save(user.getUid(), Actions.ADD_TOPIC, content);
            return RestResponse.ok(tid);
        } catch (Exception e) {
            String msg = "Editing topic failed.";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return RestResponse.fail(msg);
        }
    }

    // private void putData(Request request) {
    //     List<NodeTree> nodes = nodeService.getTree();
    //     request.attribute("nodes", nodes);
    // }

    @Route(value = "/topic/:tid", method = HttpMethod.GET)
    public ModelAndView show_topic(@PathParam("tid") Integer tid, Request request, Response response) {

        LoginUser user = SessionKit.getLoginUser();

        Integer uid = null;
        if (null != user) {
            uid = user.getUid();
        } else {
            SessionKit.setCookie(response, Constant.JC_REFERRER_COOKIE, request.url());
        }

        Topic topic = topicService.getTopic(tid);
        if (null == topic || topic.getStatus() != 1) {
            response.go("/");
            return null;
        }

        this.putDetail(request, uid, topic);

        try {
            Integer hits = mapCache.get(Constant.C_TOPIC_VIEWS + ":" + tid);
            if (null == hits) {
                hits = 0;
            }
            hits += 1;
            mapCache.set(Constant.C_TOPIC_VIEWS + ":" + tid, hits);
            if (hits >= 10) {
                typeCountService.update(Types.views.toString(), tid, 10);
                mapCache.set(Constant.C_TOPIC_VIEWS + ":" + tid, 0);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return this.getView("topic_detail");
    }

    private void putDetail(Request request, Integer uid, Topic topic) {

        Integer page = request.queryInt("p");
        if (null == page || page < 1) {
            page = 1;
        }

        Map<String, Object> topicMap = topicService.getTopicMap(topic, true);
        request.attribute("topic", topicMap);

        // boolean is_favorite = favoriteService.isFavorite(Types.topic.toString(), uid, topic.getTid());
        // request.attribute("is_favorite", is_favorite);

        // boolean is_love = favoriteService.isFavorite(Types.love.toString(), uid, topic.getTid());
        // request.attribute("is_love", is_love);

        Take cp = new Take(Comment.class);
        cp.and("tid", topic.getTid()).asc("cid").page(page, 20);
        Paginator<Map<String, Object>> commentPage = commentService.getPageListMap(cp);
        request.attribute("commentPage", commentPage);
    }


    @Route(value = "/comment/add", method = HttpMethod.POST)
    @JSON
    public RestResponse comment(Request request, Response response,
                                @QueryParam String content, @QueryParam Integer tid) {

        LoginUser user = SessionKit.getLoginUser();
        if (null == user) {
            return RestResponse.fail(401);
        }

        Integer uid = user.getUid();
        Topic topic = topicService.getTopic(tid);
        if (null == topic) {
            response.go("/");
            return null;
        }
        try {
            String ua = request.userAgent();
            topicService.comment(uid, topic.getUid(), tid, content, ua);
            // Constant.SYS_INFO = settingsService.getSystemInfo();
            // Constant.VIEW_CONTEXT.set("sys_info", Constant.SYS_INFO);
            userlogService.save(user.getUid(), Actions.ADD_COMMENT, content);
            return RestResponse.ok();
        } catch (Exception e) {
            String msg = "Committing failed.";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return RestResponse.fail(msg);
        }
    }


    @Route(value = "/essence", method = HttpMethod.POST)
    @JSON
    public RestResponse essence(Request request) {

        LoginUser user = SessionKit.getLoginUser();
        if (null == user) {
            return RestResponse.fail(401);
        }

        if (user.getRole_id() > 3) {
            return RestResponse.fail("You have no authority.");
        }

        Integer tid = request.queryInt("tid");
        if (null == tid || tid == 0) {
            return RestResponse.fail();
        }

        Topic topic = topicService.getTopic(tid);
        if (null == topic) {
            return RestResponse.fail("Topic not exists.");
        }

        try {
            Integer count = topic.getIs_essence() == 1 ? 0 : 1;
            topicService.essence(tid, count);
            userlogService.save(user.getUid(), Actions.ESSENCE, tid + ":" + count);

            return RestResponse.ok(tid);
        } catch (Exception e) {
            String msg = "Setting failed.";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return RestResponse.fail(msg);
        }
    }


    @Route(value = "/delete", method = HttpMethod.POST)
    @JSON
    public RestResponse delete(Request request, Response response) {
        LoginUser user = SessionKit.getLoginUser();
        if (null == user) {
            return RestResponse.fail(401);
        }

        Integer tid = request.queryInt("tid");
        if (null == tid || tid == 0 || user.getRole_id() > 2) {
            return RestResponse.fail();
        }

        try {
            topicService.delete(tid);
            return RestResponse.ok(tid);
        } catch (Exception e) {
            String msg = "Delete topic failed.";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return RestResponse.fail(msg);
        }
    }

    @Route(value = "/essence", method = HttpMethod.GET)
    public ModelAndView essencePage(Request request, Response response) {
        Take tp = new Take(Topic.class);
        Integer page = request.queryInt("p", 1);
        Paginator<HomeTopic> topicPage = topicService.getEssenceTopics(page, 15);
        tp.eq("status", 1).eq("is_essence", 1).desc("create_time", "update_time").page(page, 15);
        request.attribute("topicPage", topicPage);

        return this.getView("essence");
    }

}
