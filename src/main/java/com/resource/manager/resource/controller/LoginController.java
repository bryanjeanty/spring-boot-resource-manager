package com.resource.manager.resource.controller;

import java.util.Map;
import java.util.LinkedHashMap;

import java.text.SimpleDateFormat;  
import java.util.Date;  

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
	
    @GetMapping
    @ResponseBody
    public ResponseEntity<Map<String, String>> authenticationFailure(HttpServletRequest request) {
    	Map<String, String> errorResponse = new LinkedHashMap<String, String>();
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    	Date date = new Date();  
    	
    	String timeStamp = (String) formatter.format(date);
    	String urlPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    	String errorMsg = "Login failed! Sorry, try again";
    	
    	errorResponse.put("timestamp", timeStamp);
    	errorResponse.put("status", "401");
    	errorResponse.put("error", "Unauthorized");
    	errorResponse.put("message", errorMsg);
    	errorResponse.put("path", urlPath);
    	
        return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}