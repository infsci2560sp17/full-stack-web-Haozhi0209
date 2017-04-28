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
public class IndexController extends BasicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Inject
    private TopicService topicService;

    // @Inject
    // private NodeService nodeService;

    // @Inject
    // private NoticeService noticeService;

    // @Inject
    // private FavoriteService favoriteService;

    private MapCache mapCache = MapCache.single();

    @Route(value = "/", method = HttpMethod.GET)
    public ModelAndView show_home(Request request) {

        this.putData(request);

        String tab = request.query("tab");
        Integer page = request.queryAsInt("p");
        Integer nid = null;


        Paginator<HomeTopic> topicPage = topicService.getHomeTopics(nid, page, 20);
        request.attribute("topicPage", topicPage);

        List<HomeTopic> hotTopics = mapCache.get(Constant.C_HOT_TOPICS);
        if (null == hotTopics) {
            hotTopics = topicService.getHotTopics(1, 10);
            mapCache.set(Constant.C_HOT_TOPICS, hotTopics, 60 * 10);
        }
        request.attribute("hot_topics", hotTopics);

        return this.getView("home");
    }

    @Route(value = "/recent", method = HttpMethod.GET)
    public ModelAndView show_recent(Request request) {

        this.putData(request);

        String tab = request.query("tab");
        Integer page = request.queryAsInt("p");
        Integer nid = null;

        Paginator<HomeTopic> topicPage = topicService.getRecentTopics(nid, page, 15);
        request.attribute("topicPage", topicPage);

        List<HomeTopic> hotTopics = mapCache.get(Constant.C_HOT_TOPICS);
        if (null == hotTopics) {
            hotTopics = topicService.getHotTopics(1, 10);
            mapCache.set(Constant.C_HOT_TOPICS, hotTopics, 60 * 10);
        }
        request.attribute("hot_topics", hotTopics);

        return this.getView("recent");
    }

    private void putData(Request request) {

    }


    @Route(value = "/about", method = HttpMethod.GET)
    public String about() {
        return "about";
    }

}