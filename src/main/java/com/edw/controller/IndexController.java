package com.edw.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <pre>
 *     com.edw.controller.IndexController
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 21 Mar 2023 20:09
 */
@Controller
public class IndexController {

    @GetMapping(path = "/")
    public String index(Model model) {
        // get a successful user login
        OAuth2User user = ((OAuth2User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        new HashMap(){{
//            put("hello", user.getAttribute("name"));
//            put("your email is", user.getAttribute("email"));
//        }};

        model.addAttribute("user", user);
        return "index";
    }


    @GetMapping(path = "/unauthenticated")
    public HashMap unauthenticatedRequests() {
        return new HashMap(){{
            put("this is", "unauthenticated endpoint");
        }};
    }

}
