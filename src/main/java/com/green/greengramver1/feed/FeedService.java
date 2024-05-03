package com.green.greengramver1.feed;


import com.green.greengramver1.common.CustomFileUtils;
import com.green.greengramver1.feed.model.FeedPicPostDto;
import com.green.greengramver1.feed.model.FeedPostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final CustomFileUtils customFileUtils;

    public long postFeed(List<MultipartFile> pics, FeedPostReq p) {   // feed_id pk값 리턴이라 long (프론트쪽에서 PK 값이 필요하니깐)
        int result = mapper.postFeed(p);

        try {
            String path = String.format("feed/%d", p.getFeedId());
            customFileUtils.makeFolders(path);

            FeedPicPostDto picDto = FeedPicPostDto.builder().feedId(p.getFeedId()).build();
            for(MultipartFile pic : pics) {     // 랜덤한 파일명.확장자 만들어주고 + 이동시킴
                String saveFileName = customFileUtils.makeRandomFileName(pic);
                picDto.getFileNames().add(saveFileName);
                String target = String.format("%s/%s", path, saveFileName);
                customFileUtils.transferTo(pic, target);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Feed 등록 오류");
        }
        return p.getFeedId();
    }
}
