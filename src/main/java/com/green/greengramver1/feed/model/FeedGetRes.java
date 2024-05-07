package com.green.greengramver1.feed.model;

import lombok.*;

import java.util.*;

@Getter
@Setter
public class FeedGetRes {       // Res(응답) 는 JSON
    private long feedId;
    private long writerId;
    private String contents;
    private String location;
    private String createdAt;
    private String writerNm;
    private String writerPic;


    private List<String> pics;
}
