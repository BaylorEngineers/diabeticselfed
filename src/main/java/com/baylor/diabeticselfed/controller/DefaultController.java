package com.baylor.diabeticselfed.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clinicians")
public class DefaultController {

    @GetMapping("/")
    public String welcome() {
        return "Application is Running";
    }
}
