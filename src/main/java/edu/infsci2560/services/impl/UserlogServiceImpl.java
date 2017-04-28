package edu.infsci2560.services.impl;

import com.blade.context.WebContextHolder;
import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.kit.DateKit;
import edu.infsci2560.kit.Utils;
import edu.infsci2560.models.Userlog;
import edu.infsci2560.services.UserlogService;

@Service
public class UserlogServiceImpl implements UserlogService {

    @Inject
    private ActiveRecord activeRecord;

    @Override
    public void save(final Integer uid, final String action, final String content) {
        final String ip = Utils.getIpAddr(WebContextHolder.me().request());
        Utils.run(() -> {
            Userlog userlog = new Userlog();
            userlog.setUid(uid);
            userlog.setAction(action);
            userlog.setContent(content);
            userlog.setIp_addr(ip);
            activeRecord.insert(userlog);
        });
    }

}