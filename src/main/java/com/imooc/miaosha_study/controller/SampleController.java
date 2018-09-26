package com.imooc.miaosha_study.controller;

import com.imooc.miaosha_study.domain.User;
import com.imooc.miaosha_study.result.Result;
import com.imooc.miaosha_study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    UserService userService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
      model.addAttribute("name","Joshua");
      return "hello";

    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }
}