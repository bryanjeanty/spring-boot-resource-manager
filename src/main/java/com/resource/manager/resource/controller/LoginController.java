package com.resource.manager.resource.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {

    @PostMapping("/api/v1/login")
    @ResponseBody
    public String handler() {
        return "logged in";
    }
}