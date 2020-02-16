package com.community.community.controller;

import com.community.community.Service.QuestionService;
import com.community.community.dto.QuestionDTO;
import com.community.community.dto.tagDTO;
import com.community.community.entity.Question;
import com.community.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id") Integer id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("tags", tagDTO.getTags());
        model.addAttribute("id",id);

        return "publish";
    }

    @GetMapping("/publish")
    public String publish(HttpServletRequest request,User user,Model model){
        if (user != null) {
            request.getSession().setAttribute("user", user);
        }
        model.addAttribute("tags", tagDTO.getTags());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(String title,
                            String description,
                            String tag,
                            Integer id,
                            User user,
                            Model model,
                            HttpServletRequest request
                            ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tags", tagDTO.getTags());
        //校验
        if(title==null || title.length()>20 || title == ""){
            model.addAttribute("error","标题长度不符合规范！！");
            return "publish";
        }
        if(description==null){
            model.addAttribute("error","内容不能为空！！");
            return "publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("error","标签不能为空！！");
            return "publish";
        }
        if(!tagDTO.getTags().contains(tag)){
            model.addAttribute("error","标签不规范，请重新选择！！");
            return "publish";
        }
        if(user != null){
            request.getSession().setAttribute("user",user);
        }else{
            model.addAttribute("error","用户未登录！");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
