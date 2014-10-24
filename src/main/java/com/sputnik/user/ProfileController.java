package com.sputnik.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    public
    @ResponseBody
    UserResponse getProfile(Principal principal) {
        User user =  userRepository.findOne(new Long(principal.getName()));

        return new UserResponse(user.getEmail());
    }
}
