package com.sputnik.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void updateEmail(String userId, String email) {
        User user = userRepository.findOne(new Long(userId));
        if(!email.equals(user.getEmail())) {
            user.setEmail(email);
            userRepository.save(user);
        }
    }
}
