package edu.infsci2560.services;

import com.blade.jdbc.core.Take;
import com.blade.jdbc.model.Paginator;
import edu.infsci2560.dto.HomeTopic;
import edu.infsci2560.models.Topic;

import java.util.List;
import java.util.Map;

public interface TopicService {

    Topic getTopic(Integer tid);

    Map<String, Object> getTopicMap(Topic topic, boolean isDetail);

    Paginator<Map<String, Object>> getPageList(Take take);

    Integer publish(Topic topic);

    Integer update(Integer tid, Integer nid, String title, String content);

    boolean comment(Integer uid, Integer to_uid, Integer tid, String content, String ua);

    void delete(Integer tid);

    void refreshWeight();

    void updateWeight(Integer tid);

    void updateWeight(Integer tid, Integer loves, Integer favorites, Integer comment, Integer sinks, Integer create_time);

    Integer getTopics(Integer uid);

    Integer getLastCreateTime(Integer uid);

    Integer getLastUpdateTime(Integer uid);

    void essence(Integer tid, Integer count);

    Paginator<HomeTopic> getHomeTopics(Integer nid, int page, int limit);

    Paginator<HomeTopic> getRecentTopics(Integer nid, int page, int limit);

    Paginator<HomeTopic> getEssenceTopics(int page, int limit);

    List<HomeTopic> getHotTopics(int page, int limie);
}
