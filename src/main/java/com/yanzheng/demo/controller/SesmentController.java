package com.yanzheng.demo.controller;

import com.yanzheng.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/sms")
@RestController
public class SesmentController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/sendsm",method= RequestMethod.POST)
    public String sendsms(@RequestParam("phone") String mobile ){
        userService.sendSms(mobile);
        return null;
    }
}
