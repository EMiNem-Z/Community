package com.community.community.controller;

import com.community.community.Service.QuestionService;
import com.community.community.dto.QuestionDTO;
import com.community.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, HttpServletRequest request,
                           Model model,
                           User user){

        request.getSession().setAttribute("user", user);
        QuestionDTO questionDTO = questionService.getById(id);
        questionService.incViewCount(id);
        model.addAttribute("questionDto",questionDTO);
        return "question";

    }
}
