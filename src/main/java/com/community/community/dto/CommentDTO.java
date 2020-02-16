package com.community.community.dto;

import com.community.community.entity.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Integer parentId;
    private String comment;
    private Integer type;
    private Long id;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModify;
    private Long likeCount;
    private Long commentCount;
    private User user;
}
