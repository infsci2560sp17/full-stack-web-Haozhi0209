package edu.infsci2560.services;

import edu.infsci2560.models.*;

public interface TopicCountService {

    TopicCount getCount(Integer tid);

    void update(String type, Integer tid, int count);

    void save(Integer tid, Integer create_time);

}