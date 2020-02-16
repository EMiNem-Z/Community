package com.community.community.Service;

import com.community.community.dto.NotificationDTO;
import com.community.community.dto.QuestionDTO;
import com.community.community.entity.Notification;
import com.community.community.entity.Question;
import com.community.community.entity.User;
import com.community.community.mapper.NotificationMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PageInfo NotificationDTOList(Integer userId, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Notification> notificationList = notificationMapper.listByStatus(userId);
        PageInfo pageInfo = new PageInfo(notificationList);
        List<NotificationDTO> notificationListDTOList = new ArrayList<>();
        for(Notification notification : notificationList){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationListDTOList.add(notificationDTO);
        }
        pageInfo.setList(notificationListDTOList);
        return pageInfo;
    }

    public void read(Integer id) {
        notificationMapper.updateStatusById(id);
    }
}
