package com.community.community.controller;

import com.community.community.Service.QuestionService;
import com.community.community.dto.QuestionDTO;
import com.community.community.entity.Question;
import com.community.community.entity.User;
import com.community.community.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.context.SpringContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, User user,
                        Model model,
                        @RequestParam(name="pageNum",defaultValue = "1")Integer pageNum,
                        @RequestParam(name="pageSize",defaultValue = "5")Integer pageSize,
                        @RequestParam(name="search",required=false)String search){
        //登陆信息
        if (user != null) {
            request.getSession().setAttribute("user", user);
        }
        PageInfo pageInfo = questionService.questionDTOList(search,pageNum,pageSize);     //获取帖子集合
        List<Question> hotTitles = questionService.hotTitle();                            //获取热门话题集合
        model.addAttribute("hotTitles",hotTitles);
        model.addAttribute("search",search);
        model.addAttribute("pageInfo",pageInfo);
        return "index";
    }
}
