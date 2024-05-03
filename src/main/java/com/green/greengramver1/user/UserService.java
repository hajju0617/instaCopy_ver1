package com.green.greengramver1.user;

import com.green.greengramver1.common.CustomFileUtils;
import com.green.greengramver1.user.model.SignInPostReq;
import com.green.greengramver1.user.model.SignInRes;
import com.green.greengramver1.user.model.SignUpPostReq;
import com.green.greengramver1.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;

    @Transactional
    // 프로필 이미지 처리
    public int postSignUp(MultipartFile pic, SignUpPostReq p) {
//        pic.getOriginalFilename();      // 업로드 된 파일의 이름을 가져올 수 있는 메서드 (문자열을 반환)
        String saveFileName = customFileUtils.makeRandomFileName(pic);
        p.setPic(saveFileName);
        String hashedPw = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        p.setUpw(hashedPw);
        int result = mapper.postUser(p);

        if(pic == null) {
            return result;
        }
        try {
            String path = String.format("user/%d", p.getUserId());  // 폴더를 만들때 필요한 정보
            customFileUtils.makeFolders(path);

            String target = String.format("%s/%s", path, saveFileName); // 폴더 + 파일명
            customFileUtils.transferTo(pic, target);    // 이동
            log.info("path: {}", path);
            log.info("target: {}", target);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일 요류");
        }
        return result;
    }

    public SignInRes postSignIn(SignInPostReq p) {

        User user = mapper.getUserById(p.getUid());
        if(user == null) {
            throw new RuntimeException("아이디를 확인해 주세요");
        } else if (!BCrypt.checkpw(p.getUpw(), user.getUpw())) {
            throw new RuntimeException("비밀번호를 확인해 주세요");
        }
        return SignInRes.builder()
                .userId(user.getUserId())
                .nm(user.getNm())
                .pic(user.getPic())
                .build();
    }
}
