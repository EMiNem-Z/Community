package com.community.community.dto;

import lombok.Data;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public ResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultDTO NOLOGIN = new ResultDTO(2001,"未登录");
    public static ResultDTO SUCCESS = new ResultDTO(200,"成功");

    public static <T> ResultDTO okOf(T t){
        ResultDTO resultDTO = new ResultDTO(200,"成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}
