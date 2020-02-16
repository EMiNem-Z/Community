package com.community.community.controller;

import com.community.community.Service.NotificationService;
import com.community.community.entity.Notification;
import com.community.community.entity.Question;
import com.community.community.mapper.NotificationMapper;
import com.community.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/readNotify/{id}")
    public String readNotify(@PathVariable(name="id") Integer id){
        notificationService.read(id);
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        Question question = questionMapper.findById(notification.getOuterid());
        return "redirect:/question/"+question.getId();
    }
}
