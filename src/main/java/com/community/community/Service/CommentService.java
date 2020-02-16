package com.community.community.Service;

import com.community.community.dto.CommentDTO;
import com.community.community.entity.Comment;
import com.community.community.entity.Notification;
import com.community.community.entity.Question;
import com.community.community.entity.User;
import com.community.community.mapper.CommentMapper;
import com.community.community.mapper.NotificationMapper;
import com.community.community.mapper.QuestionMapper;
import com.community.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment,User user) {
        Question question;
        if(comment.getType() == 1){     //回复问题
            questionMapper.incrCommentCount(comment.getParentId());
            question = questionMapper.findById(comment.getParentId());
        }else{//回复评论
            commentMapper.incrCommentCount(comment.getParentId());
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId().longValue());
            question = questionMapper.findById(dbcomment.getParentId());
        }
        createNotify(comment, user, question);
        commentMapper.insert(comment);
    }

    //创建通知
    private void createNotify(Comment comment, User user, Question question) {
        if(comment.getCommentator() == question.getCreator()){
            return;
        }
        Notification notification = new Notification();
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(question.getCreator());
        notification.setOuterid(question.getId());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setStatus(0);
        notification.setNotifierLogin(user.getLogin());
        notification.setOuterTitle(question.getTitle());
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByParentIdAndType(Integer parentId,Integer type) {
        List<CommentDTO> commentDTOs = new ArrayList<>();
        List<Comment> comments =  commentMapper.selectByParentIdAndType(parentId,type);
        Set<Integer> set = new HashSet();
        Map<Integer, User> map = new HashMap();
        for(Comment comment : comments){
            set.add(comment.getCommentator());
        }
        for(Integer commentator : set){
            User user = userMapper.findUserById(commentator);
            map.put(commentator,user);
        }
        for(Comment comment : comments){
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(map.get(commentDTO.getCommentator()));
            commentDTOs.add(commentDTO);
        }
        return commentDTOs;
    }

    public void like(Integer id) {
        commentMapper.incrLikeCount(id);
    }
}
