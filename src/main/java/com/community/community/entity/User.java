package com.community.community.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String login;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModify;
    private String bio;
    private String avatarUrl;

}
