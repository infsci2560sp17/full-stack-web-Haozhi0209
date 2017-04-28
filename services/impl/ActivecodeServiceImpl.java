package edu.infsci2560.services.impl;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.kit.DateKit;
import com.blade.kit.StringKit;
import edu.infsci2560.models.User;
import edu.infsci2560.Types;
import edu.infsci2560.exception.TipException;
import edu.infsci2560.models.LoginUser;
import edu.infsci2560.services.*;
import edu.infsci2560.models.Activecode;
import edu.infsci2560.services.ActivecodeService;

@Service
public class ActivecodeServiceImpl implements ActivecodeService {

    @Inject
    private ActiveRecord activeRecord;

    @Inject
    private UserService userService;

    @Inject
    private UserinfoService userinfoService;

    @Override
    public Activecode getActivecode(String code) {
        if (StringKit.isBlank(code)) {
            return null;
        }
        Activecode temp = new Activecode();
        temp.setCode(code);
        return activeRecord.one(temp);
    }

    public Activecode getActivecodeById(Integer id) {
        if (null == id) {
            return null;
        }
        return activeRecord.byId(Activecode.class, id);
    }

    @Override
    public String save(User user, String type) {
        if (null == user || StringKit.isBlank(type)) {
            throw new TipException("User information is empty.");
        }

        int time = DateKit.getCurrentUnixTime();
        int expires_time = time + 3600;
        String code = StringKit.getRandomChar(32);
        Activecode activecode = new Activecode();
        activecode.setUid(user.getUid());
        activecode.setCode(code);
        activecode.setType(type);
        activecode.setExpires_time(expires_time);
        activecode.setCreate_time(time);
        activeRecord.insert(activecode);
        return code;
    }

    @Override
    public boolean useCode(String code) {
        if (null == code) {
            throw new TipException("Active code empty.");
        }
        activeRecord.execute("update t_activecode set is_use = 1 where code = '" + code + "'");
        return true;
    }

    @Override
    public boolean resend(Integer uid) {
        User user = userService.getUser(uid);
        if (null == user) {
            throw new TipException("User not exists.");
        }

        int time = DateKit.getCurrentUnixTime();
        int expires_time = time + 3600;
        String code = StringKit.getRandomChar(32);

        Activecode activecode = new Activecode();
        activecode.setUid(user.getUid());
        activecode.setCode(code);
        activecode.setType("signup");
        activecode.setExpires_time(expires_time);
        activecode.setCreate_time(time);

        activeRecord.insert(activecode);
        return true;
    }

}
