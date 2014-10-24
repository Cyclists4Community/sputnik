package com.sputnik.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthorizationController {

    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping(method = RequestMethod.GET, value = "/authorization")
    public
    @ResponseBody
    AuthorizationResponse getAuthorization(
            Authentication authentication
    ) {
        return authorizationService.getAuthorizationResponse(authentication);
    }

}
