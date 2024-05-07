package com.green.greengramver1.feed.model;

import lombok.*;
import java.util.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedPostRes {
    private long feedId;
    private List<String> pics;
}
