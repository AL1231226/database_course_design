package com.example.Sale_Information_System.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class helloControlller {

    @RequestMapping("helloya")
    public String hello(){
        return "helloya";
    }
}
