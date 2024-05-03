package com.green.greengramver1.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignInRes {
    @Schema(description = "유저PK")
    private long userId;

    @Schema(description = "유저 이름")
    private String nm;

    @Schema(description = "유저 프로필 이미지")
    private String pic;
}
