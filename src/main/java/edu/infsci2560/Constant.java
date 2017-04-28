package edu.infsci2560;

import com.blade.kit.base.Config;
//import jetbrick.template.JetGlobalContext;

import java.util.HashMap;
import java.util.Map;

public final class Constant {

    public static Config config = new Config();

    public static final String LOGIN_SESSION_KEY = "login_user";

    public static final String USER_IN_COOKIE = "SH_SIGNIN_USER";

    public static final String JC_REFERRER_COOKIE = "JC_REFERRER_COOKIE";

    public static final String C_HOME_NODE_KEY = "home:nodes";
    public static final String C_HOME_FAMOUS_KEY = "home:famousDay";
    public static final String C_HOT_TOPICS = "topic:hot";
    public static final String C_HOT_NODES = "node:hot";
    public static final String C_TOPIC_VIEWS = "topic:views";

    public static String SITE_URL;

    public static String CDN_URL;

    public static String AES_SALT;

    public static String UPLOAD_DIR;

    public static String GITHUB_CLIENT_ID;
    public static String GITHUB_CLIENT_SECRET;
    public static String GITHUB_REDIRECT_URL;

    public static String MAIL_HOST;
    public static String MAIL_USER;
    public static String MAIL_USERNAME;
    public static String MAIL_PASS;

    //public static JetGlobalContext VIEW_CONTEXT = null;
    public static Map<String, Object> SYS_INFO = new HashMap<>();

}
