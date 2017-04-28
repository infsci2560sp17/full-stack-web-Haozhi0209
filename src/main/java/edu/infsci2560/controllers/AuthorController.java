package edu.infsci2560.controllers;

import edu.infsci2560.models.*;
import edu.infsci2560.services.*;
import edu.infsci2560.kit.*;
import edu.infsci2560.Constant;
import edu.infsci2560.Actions;
import edu.infsci2560.exception.TipException;
import edu.infsci2560.Types;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.blade.ioc.annotation.Inject;
import com.blade.jdbc.core.Take;
import com.blade.kit.DateKit;
import com.blade.kit.PatternKit;
import com.blade.kit.StringKit;
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

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthorController extends BasicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);

    // @Inject
    // private SettingsService settingsService;

    // @Inject
    // private ActivecodeService activecodeService;

    @Inject
    private UserService userService;

    @Inject
    private UserinfoService userinfoService;

    @Inject
    private CommentService commentService;

    // @Inject
    // private NoticeService noticeService;

    // @Inject
    // private FavoriteService favoriteService;

    @Inject
    private TopicService topicService;

    @Inject
    private UserlogService userlogService;

    private Patchca patchca = new DefaultPatchca();

    @Route(value = "/captcha", method = HttpMethod.GET)
    public void show_captcha(Request request, Response response) {
        try {
            patchca.render(request, response);
        } catch (Exception e) {
            LOGGER.error("Fail to get verification code", e);
        }
    }

    @Route(value = "/signin", method = HttpMethod.GET)
    public String show_signin() {
        return "signin";
    }

    @Route(value = "/signin", method = HttpMethod.POST)
    @JSON
    public RestResponse signin(Request request, Response response,
                               @QueryParam String login_name,
                               @QueryParam String pass_word,
                               @QueryParam String rememberme) {

        try {
            User user = userService.signin(login_name, pass_word);
            LoginUser loginUser = userService.getLoginUser(user, null);
            SessionKit.setLoginUser(request.session(), loginUser);
            if (StringKit.isNotBlank(rememberme) && rememberme.equals("on")) {
                SessionKit.setCookie(response, Constant.USER_IN_COOKIE, loginUser.getUid());
            }
            userlogService.save(user.getUid(), Actions.SIGNIN, login_name);
            String val = SessionKit.getCookie(request, Constant.JC_REFERRER_COOKIE);
            return RestResponse.ok(val);
        } catch (Exception e) {
            String msg = "Login Failed";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return RestResponse.fail(msg);
        }

    }


    @Route(value = "/signup", method = HttpMethod.GET)
    public ModelAndView show_signup(Request request) {
        Object allow_signup = Constant.SYS_INFO.get(Types.allow_signup.toString());
        return this.getView("signup");
    }

    @Route(value = "/logout")
    public void logout(Request request, Response response) {
        SessionKit.removeUser(request.session());
        SessionKit.removeCookie(response);
        response.go("/");
    }

    @Route(value = "/signup", method = HttpMethod.POST)
    public ModelAndView signup(Request request, Response response) {

        String login_name = request.query("login_name");
        String email = request.query("email");
        String pass_word = request.query("pass_word");
        String auth_code = request.query("auth_code");

        request.attribute("login_name", login_name);
        request.attribute("email", email);

        if (StringKit.isBlank(login_name) || StringKit.isBlank(pass_word)
                || StringKit.isBlank(email) || StringKit.isBlank(auth_code)) {
            request.attribute(this.ERROR, "This parameter cannot be null!");
            return this.getView("signup");
        }

        if (login_name.length() > 16 || login_name.length() < 4) {
            request.attribute(this.ERROR, "Please enter a 4 - 16 bits username.");
            return this.getView("signup");
        }

        if (!Utils.isLegalName(login_name)) {
            request.attribute(this.ERROR, "Please do not contain _, /,  .");
            return this.getView("signup");
        }

        if (!Utils.isSignup(login_name)) {
            request.attribute(this.ERROR, "Name illegal, please change it.");
            return this.getView("signup");
        }

        if (!Utils.isEmail(email)) {
            request.attribute(this.ERROR, "Please enter a legal email");
            return this.getView("signup");
        }

        if (pass_word.length() > 20 || pass_word.length() < 6) {
            request.attribute(this.ERROR, "Please enter a 6 - 20 bits password");
            return this.getView("signup");
        }

        String patchca = request.session().attribute("patchca");
        if (StringKit.isNotBlank(patchca) && !patchca.equalsIgnoreCase(auth_code)) {
            request.attribute(this.ERROR, "Wrong verification code!");
            return this.getView("signup");
        }

        Take queryParam = new Take(User.class);
        queryParam.eq("login_name", login_name);
        queryParam.in("status", 0, 1);
        User user = userService.getUser(queryParam);
        if (null != user) {
            request.attribute(this.ERROR, "Username taken, please try another one.");
            return this.getView("signup");
        }

        queryParam = new Take(User.class);
        queryParam.eq("email", email);
        queryParam.in("status", 0, 1);
        user = userService.getUser(queryParam);
        if (null != user) {
            request.attribute(this.ERROR, "Email registered, please login directly!");
            return this.getView("signup");
        }

        try {
            User user_ = userService.signup(login_name, pass_word, email);
            if (null != user_) {
                userlogService.save(user_.getUid(), Actions.SIGNUP, login_name + ":" + email);
                request.attribute(this.INFO, "Register successful! We've send an email to " + email );
            } else {
                request.attribute(this.ERROR, "Register failed.");
            }
        } catch (Exception e) {
            String msg = "Register failed.";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            request.attribute(this.ERROR, msg);
        }
        return this.getView("signup");
    }

    @Route(value = "/forgot", method = HttpMethod.GET)
    public String show_forgot() {
        return "forgot";
    }

    @Route(value = "/forgot", method = HttpMethod.POST)
    public ModelAndView forgot(Request request, @QueryParam String email) {
        if (StringKit.isBlank(email)) {
            request.attribute(this.ERROR, "Email cannot be empty.");
            return this.getView("forgot");
        }

        if (!PatternKit.isEmail(email)) {
            request.attribute(this.ERROR, "Please enter a correct email.");
            request.attribute("email", email);
            return this.getView("forgot");
        }

        User user = userService.getUser(new Take(User.class).eq("email", email));
        if (null == user) {
            request.attribute(this.ERROR, "Email not registerd, please check again.");
            request.attribute("email", email);
            return this.getView("forgot");
        }
        if (user.getStatus() == 0) {
            request.attribute(this.ERROR, "Email not activated.");
            request.attribute("email", email);
            return this.getView("forgot");
        }
        try {
            if (StringKit.isNotBlank(email)) {
                request.attribute(this.INFO, "A link has sent to your email, please check it!");
            } else {
                request.attribute(this.ERROR, "Password finding failed.");
            }
        } catch (Exception e) {
            String msg = "Password finding failed.";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            request.attribute(this.ERROR, msg);
        }
        return this.getView("forgot");
    }

}