package com.kanikaratra.javaspringsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping("/helloworld")
    String helloworld()
    {
        return "hello kanika";
    }
}
