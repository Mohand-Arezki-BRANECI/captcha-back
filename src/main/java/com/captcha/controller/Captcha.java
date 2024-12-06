package com.captcha.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Captcha {
    @GetMapping("/greet")
    public Map<String, String> greeting(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "hello from controller");
        return response;
    }

}
