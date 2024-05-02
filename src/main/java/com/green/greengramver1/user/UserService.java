package com.green.greengramver1.user;

import com.green.greengramver1.user.model.SignUpPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;

    // 프로필 이미지 처리
    public int postUser(MultipartFile pic, SignUpPostReq p) {
        return mapper.postUser(p);
    }

}
