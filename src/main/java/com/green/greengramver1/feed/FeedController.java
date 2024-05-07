package com.green.greengramver1.feed;

import com.green.greengramver1.common.model.ResultDto;
import com.green.greengramver1.feed.model.FeedGetReq;
import com.green.greengramver1.feed.model.FeedGetRes;
import com.green.greengramver1.feed.model.FeedPostReq;
import com.green.greengramver1.feed.model.FeedPostRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/feed")
@RestController
@Tag(name = "Feed 피드", description = "Feed CRUD")
public class FeedController {
    private final FeedService service;

    @PostMapping
    @Operation(summary = "Feed 등록", description = "")
    public ResultDto<FeedPostRes> postFeed(@RequestPart List<MultipartFile> pics, @RequestPart FeedPostReq p) {
        System.out.println(pics);
//        @RequestParam은 요청의 쿼리 파라미터를 메소드의 매개변수로 바인딩하는 데 사용
//        @PathVariable은 URL 경로의 일부를 메소드의 매개변수로 바인딩하는 데 사용
//        @RequestBody는 요청 본문의 데이터를 메소드의 매개변수로 바인딩하는 데 사용되며, 주로 JSON 이나 XML과 같은 복잡한 데이터 구조를 처리할 때 사용

//        @RequestPart는 multipart/form-data 요청의 일부를 메소드의 매개변수로 바인딩하는 데 사용되며, 파일 업로드나 멀티파트와 관련된 데이터 처리에 유용
            /*  RequestPart
            파일 업로드와 같이 멀티파트 요청을 처리할 때 유용, 요청의 특정 부분을 메소드 매개변수로 바인딩하는 데 사용
            메소드 매개변수로 바인딩하는 데 사용"이라는 표현은, 특정 데이터나 객체를 메소드가 받는 인자(매개변수)에 자동으로 연결(바인딩)해주는 과정을 의미

            @RequestPart의 주요 사용 사례는 파일 업로드 뿐만 아니라 멀티파트 요청에서 JSON 또는 XML과 같은 복잡한 데이터 타입을 받을 때도 유용합니다
            예를 들어, 파일과 함께 JSON 형태의 메타데이터를 전송하는 경우에 사용할 수 있습니다.
             */
        FeedPostRes result = service.postFeed(pics, p);

        return ResultDto.<FeedPostRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("")
                .resultData(result)
                .build();
    }

    @GetMapping
    @Operation(summary = "Feed 리스트", description = "loginUsreId는 로그인한 사용자의 pk")
    public ResultDto<List<FeedGetRes>> getFeed(@ParameterObject @ModelAttribute FeedGetReq p) {
        List<FeedGetRes> result = service.getFeed(p);

        return ResultDto.<List<FeedGetRes>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(result)
                .build();
    }
}
