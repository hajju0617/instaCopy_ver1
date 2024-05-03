package com.green.greengramver1.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FeedPostReq {
    @JsonIgnore private long feedId;

    private long userId;
    private String contents;
    private String location;

}
