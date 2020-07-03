package com.yanzheng.demo.controller;




import com.yanzheng.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RequestMapping("/sms")
@RestController
public class SesmentController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/sendsm", method = RequestMethod.POST)
    public String sendsms(@RequestParam("phone") String phone) {

        boolean b = userService.sendSms(phone);

        return null;
    }

    @RequestMapping(value = "/yanzheng", method = RequestMethod.GET)
    public String testCode(@RequestParam("phone") String phone) {

        String code = (String) redisTemplate.opsForValue().get(phone);
        System.out.println("验证手机号；" + code);

        return null;
    }
}
