package com.application.pennypal.interfaces.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/user")
    public String seeUser(){
        return "user";
    }

    @GetMapping("/something")
    public String getSomething(){
        return "something ....";

    }
}
