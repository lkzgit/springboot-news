package com.yanzheng.demo.controller;


import com.google.code.kaptcha.impl.DefaultKaptcha;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/api")
public class UserInfoController {


    private static final Logger logger= LoggerFactory.getLogger(UserInfoController.class);
    @Autowired
    private DefaultKaptcha captchaProducer;

    // 项目启动进入登录界面
    @RequestMapping(value = "tologin",method = RequestMethod.GET)
    public String getLogin(){

        return "login";
    }

    /**
     * 验证图形吗
     */
    @RequestMapping("xiaodui")
    public ModelAndView xioadui(HttpServletRequest request,HttpServletResponse response){
        ModelAndView modelAndView=new ModelAndView();
        String rightCode = (String) request.getSession().getAttribute("verifyCode");
        String tryCode = request.getParameter("tryCode");
        System.out.println("rightCode:"+rightCode+"-----tryCode:"+tryCode);
        if(!rightCode.equals(tryCode)){
            modelAndView.addObject("info","错误的验证");
            modelAndView.setViewName("index");
        }else{
            modelAndView.addObject("info","验证成功");
            modelAndView.setViewName("success");
        }
        return modelAndView;

    }

















    // 把生成的验证码以图片的形式输送到前段页面
    @GetMapping("/getVerifyCode")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String createText = captchaProducer.createText();
        request.getSession().setAttribute("verifyCode", createText);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        BufferedImage challenge = captchaProducer.createImage(createText);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(challenge, "jpg", outputStream);
        try {
            outputStream.flush();
        } finally {
            outputStream.close();
        }
    }


    @GetMapping("phoneText")
    public void captcha(HttpServletResponse response,HttpServletRequest request){
        try{
            response.setHeader("Cache-Controller","no-store,no-cache");
            response.setContentType("image/jpeg");
            // 生成文字验证码
            String text = captchaProducer.createText();
            //生成图片验证码
            BufferedImage image = captchaProducer.createImage(text);
            // 保存验证码到session
            request.getSession().setAttribute("text",text);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image,"jpg",out);
            IOUtils.closeQuietly(out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
