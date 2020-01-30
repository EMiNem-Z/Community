package com.community.community.controller;

import com.community.community.Service.QuestionService;
import com.community.community.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/profile/{active}")
    public String profile(@PathVariable(name="active") String active,
                          HttpServletRequest request, User user, Model model,
                          @RequestParam(name="pageNum",defaultValue = "1")Integer pageNum,
                          @RequestParam(name="pageSize",defaultValue = "5")Integer pageSize){
        //登陆信息
        if (user != null) {
            request.getSession().setAttribute("user", user);
        }else{
            return "/";
        }
        if("question".equals(active)){
            model.addAttribute("section",active);
            model.addAttribute("sectionName","我的问题");
            PageInfo pageInfo = questionService.questionDTOList(user.getId(),pageNum,pageSize);
            model.addAttribute("pageInfo",pageInfo);
        }else if("replies".equals(active)) {
            model.addAttribute("section", active);
            model.addAttribute("sectionName", "我的回复");
        }
        return "profile";
    }
}