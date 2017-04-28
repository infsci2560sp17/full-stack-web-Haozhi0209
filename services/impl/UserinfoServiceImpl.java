package edu.infsci2560.services.impl;

import com.blade.ioc.annotation.Inject;
import com.blade.ioc.annotation.Service;
import com.blade.jdbc.ActiveRecord;
import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;
import com.blade.kit.StringKit;
import edu.infsci2560.models.UserInfo;
import edu.infsci2560.services.UserinfoService;

import java.util.List;

@Service
public class UserinfoServiceImpl implements UserinfoService {

    @Inject
    private ActiveRecord activeRecord;

    @Override
    public UserInfo getUserinfo(Integer uid) {
        return activeRecord.byId(UserInfo.class, uid);
    }

    private List<UserInfo> getUserinfoList(Take queryParam) {
        if (null != queryParam) {
            return activeRecord.list(queryParam);
        }
        return null;
    }

    @Override
    public Paginator<UserInfo> getPageList(Take queryParam) {
        if (null != queryParam) {
            return activeRecord.page(queryParam);
        }
        return null;
    }

    @Override
    public boolean save(Integer uid) {
        if (null == uid) {
            return false;
        }
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setUid(uid);
            activeRecord.insert(userInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(UserInfo userInfo) {
        if (null != userInfo && null != userInfo.getUid()) {
            activeRecord.update(userInfo);
        }
    }

    @Override
    public boolean delete(Integer uid) {
        if (null != uid) {
            return activeRecord.delete(UserInfo.class, uid) > 0;
        }
        return false;
    }

}
