package edu.infsci2560.services;

import edu.infsci2560.models.Activecode;
import edu.infsci2560.models.User;

public interface ActivecodeService {

    Activecode getActivecode(String code);

    String save(User user, String type);

    boolean useCode(String code);

    boolean resend(Integer uid);

}
