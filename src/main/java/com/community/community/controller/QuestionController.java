package com.community.community.controller;

import com.community.community.Service.CommentService;
import com.community.community.Service.QuestionService;
import com.community.community.dto.CommentDTO;
import com.community.community.dto.QuestionDTO;
import com.community.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, HttpServletRequest request,
                           Model model,
                           User user){

        request.getSession().setAttribute("user", user);
        QuestionDTO questionDTO = questionService.getById(id);
        questionService.incViewCount(id);
        List<QuestionDTO> relateds = questionService.selectRelated(questionDTO);
        List<CommentDTO> commentDTOs = commentService.listByParentIdAndType(id,1);
        model.addAttribute("relateds",relateds);
        model.addAttribute("questionDto",questionDTO);
        model.addAttribute("commentDtos",commentDTOs);
        return "question";

    }
}
