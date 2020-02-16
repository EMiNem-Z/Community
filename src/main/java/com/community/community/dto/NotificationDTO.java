package com.community.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Integer outerid;
    private Long gmtCreate;
    private String notifierLogin;
    private String outerTitle;
}
