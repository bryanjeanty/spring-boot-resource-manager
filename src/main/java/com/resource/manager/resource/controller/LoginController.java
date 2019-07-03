package com.resource.manager.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {

    @GetMapping("/")
    @ResponseBody
    public String handler() {
        return "logged in";
    }

    @GetMapping("/resource")
    @ResponseBody
	public String getResourcePage() {
		return "Welcome to the resource page";
	}
}