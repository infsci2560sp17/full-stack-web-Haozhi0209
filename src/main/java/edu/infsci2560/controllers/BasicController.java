package edu.infsci2560.controllers;

import edu.infsci2560.models.*;
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
import com.blade.kit.json.JSONObject;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.view.ModelAndView;
import com.blade.mvc.view.RestResponse;
import com.blade.patchca.DefaultPatchca;
import com.blade.patchca.Patchca;


import java.util.HashMap;
import java.util.Map;

public class BasicController {

    protected final String ERROR = "error";
    protected final String INFO = "info";
    protected final String SUCCESS = "success";
    protected final String FAILURE = "failure";

    public ModelAndView getView(String view) {
        return getView(new HashMap<>(), view);
    }

    public ModelAndView getView(Map<String, Object> map, String view) {
        return new ModelAndView(map, view + ".html");
    }

    public void success(Response response, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        jsonObject.put("data", data);
        response.json(jsonObject.toString());
    }

    public void error(Response response, String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 500);
        jsonObject.put("msg", msg);
        response.json(jsonObject.toString());
    }

    public void nosignin(Response response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 401);
        response.json(jsonObject.toString());
    }

}
