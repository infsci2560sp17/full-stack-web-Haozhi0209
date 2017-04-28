package edu.infsci2560.services;

import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;
import edu.infsci2560.models.UserInfo;

public interface UserinfoService {

    UserInfo getUserinfo(Integer uid);

    Paginator<UserInfo> getPageList(Take take);

    boolean save(Integer uid);

    void update(UserInfo userInfo);

    boolean delete(Integer uid);

}
