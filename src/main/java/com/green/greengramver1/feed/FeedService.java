package com.green.greengramver1.feed;


import com.green.greengramver1.common.CustomFileUtils;
import com.green.greengramver1.feed.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final CustomFileUtils customFileUtils;


    /*
    트랜잭션은 데이터베이스 작업을 수행할 때, 여러 개의 쿼리를 하나의 단위로 묶어서 처리하는 것
    1.트랜잭션 범위 설정
    @Transactional 어노테이션을 메소드나 클래스에 적용하여 트랜잭션 범위를 설정할 수 있음
    메소드에 적용하면 해당 메소드 내에서 수행되는 모든 쿼리가 하나의 트랜잭션으로 묶이고
    클래스에 적용하면 해당 클래스의 모든 메소드가 하나의 트랜잭션으로 묶입니다.

    2.트랜잭션 롤백
    트랜잭션 중에 오류가 발생하면 자동으로 트랜잭션을 롤백(Rollback)함
    롤백은 이전 상태로 되돌리는 것을 말합니다. 이를 통해 데이터의 일관성을 유지할 수 있습니다.
    즉 메소드 내에서 실행되는 데이터베이스 작업들은 모두 성공해야 하며, 하나라도 실패하면 롤백됩니다.
     */
    @Transactional
    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {   // feed_id pk값 리턴이라 long (프론트쪽에서 PK 값이 필요하니깐)
        int result = mapper.postFeed(p);

        FeedPicPostDto picDto = FeedPicPostDto.builder().feedId(p.getFeedId()).build();
        try {
            String path = String.format("feed/%d", p.getFeedId());
            customFileUtils.makeFolders(path);
            /*
            FeedPicPostDto 인스턴스를 생성하는 데 있어서 "빌더 패턴(Builder Pattern)"을 사용하고 있음
            빌더 패턴은 객체의 생성 과정과 표현 방법을 분리하여 동일한 생성 절차에서 서로 다른 표현 결과를 만들 수 있게 하는 디자인 패턴
            FeedPicPostDto.builder()는 FeedPicPostDto 의 빌더 객체를 생성
            이 빌더 객체는 FeedPicPostDto 객체를 단계적으로 구성할 수 있는 메서드(예: feedId(...))를 제공 함
            .builder(): FeedPicPostDto 객체를 생성하기 위한 빌더 객체를 초기화 함.
            .feedId(p.getFeedId()): 빌더에 feedId 값을 설정 함. 여기서 p.getFeedId()는 feedId의 값을 제공합니다.
            .build(): 앞서 설정된 값들을 바탕으로 최종적인 FeedPicPostDto 객체를 생성
            이러한 방식을 통해, 필요한 속성을 설정하고, 설정된 속성들을 가지고 객체를 생성하는 과정을 더 명확하게 표현
            빌더 패턴은 특히 생성자의 매개변수가 많거나 객체의 생성 과정이 복잡할 때 유용하게 사용
             */

            for(MultipartFile pic : pics) {     // 랜덤한 파일명.확장자 만들어주고 + 이동시킴
                String saveFileName = customFileUtils.makeRandomFileName(pic);
                picDto.getFileNames().add(saveFileName);
                String target = String.format("%s/%s", path, saveFileName);
                customFileUtils.transferTo(pic, target);
            }
            int affectedRowsPics = mapper.postFeedPics(picDto);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Feed 등록 오류");
        }
        return FeedPostRes.builder()
                .feedId(p.getFeedId())
                .pics(picDto.getFileNames())
                .build();
        // 피드를 올리자마자 삭제한다거나 댓글을 쓴다거나 할때 pk값이 필요하므로 pk값을 반환 (프론트에서 필요해서)
    }   //@Transactional 범위 끝

    public List<FeedGetRes> getFeed(FeedGetReq p) {         // n + 1 발생
        List<FeedGetRes> list = mapper.getFeed(p);    // 이 부분이 1을 뜻함

        for(FeedGetRes res : list) {
            List<String> pics = mapper.getFeedPicsByFeedId(res.getFeedId());    // 이 부분이 n을 뜻함
            res.setPics(pics);
        }
        return list;
    }
}
