package com.community.community.dto;

import java.util.ArrayList;
import java.util.List;

public class tagDTO {
    private static String tag1 = "生活";
    private static String tag2 = "娱乐";
    private static String tag3 = "学习";
    private static String tag4 = "交友";
    private static String tag5 = "闲谈";
    private static String tag6 = "体育";

    public static List<String> getTags(){
        List<String> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        tags.add(tag4);
        tags.add(tag5);
        tags.add(tag6);
        return tags;
    }
}
