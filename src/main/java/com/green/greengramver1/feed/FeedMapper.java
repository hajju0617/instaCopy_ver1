package com.green.greengramver1.feed;

import com.green.greengramver1.feed.model.FeedPostReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedMapper {
    int postFeed(FeedPostReq p);

}
