package com.community.community.controller;

import com.community.community.Service.CommentService;
import com.community.community.dto.CommentDTO;
import com.community.community.dto.ResultDTO;
import com.community.community.entity.Comment;
import com.community.community.entity.User;
import com.community.community.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value="/comment",method = RequestMethod.POST)
    public Object comment(@RequestBody CommentDTO commentDTO,
                          HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.NOLOGIN;
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setComment(commentDTO.getComment());
        comment.setCommentator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(comment.getGmtCreate());
        comment.setLikeCount(0L);
        comment.setCommentCount(0L);
        commentService.insert(comment,user);
        return ResultDTO.SUCCESS;
    }

    @ResponseBody
    @RequestMapping(value="/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List> comment(@PathVariable(name="id") Integer id ){
        List<CommentDTO> commentDTOS = commentService.listByParentIdAndType(id,2);
        return ResultDTO.okOf(commentDTOS);
    }


    @GetMapping(value="/like/{id}")
    @ResponseBody
    public Object like(@PathVariable(name="id") Integer id){
        commentService.like(id);                //通过id给该评论的点赞数+1
        return ResultDTO.SUCCESS;
    }
}
