package com.community.community.result;

import com.github.pagehelper.PageInfo;
import lombok.Data;

@Data
public class Result<T>{
    private T data;
    private PageInfo pageInfo;

    public Result(T data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
