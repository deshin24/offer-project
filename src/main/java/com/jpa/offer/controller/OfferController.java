package com.jpa.offer.controller;

import com.jpa.offer.dto.OfferCreateRequestDto;
import com.jpa.offer.dto.OfferUpdateRequestDto;
import com.jpa.offer.dto.SearchCondition;
import com.jpa.offer.dto.UserSessionDto;
import com.jpa.offer.service.OfferService;
import com.jpa.offer.service.UserCheckService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/offer")
public class OfferController {

    private final OfferService offerService;
    private final UserCheckService userCheckService;

    /**
성     * 제안 등록
     * @param offerRequestDto
     * @return
     */
    @ApiOperation(value = "제안 등록")
    @PostMapping(value = "/", produces = "application/json; charset=UTF-8")
    public ResponseEntity create(@Valid OfferCreateRequestDto offerRequestDto, BindingResult result,
                                 @RequestPart(value = "file1", required = false) MultipartFile file1,
                                 @RequestPart(value = "file2", required = false) MultipartFile file2) throws IOException {

        ResponseEntity sb = getResponseEntity(result);
        if (sb != null) return sb;

        List<MultipartFile> files = new ArrayList<>();
        if(file1 != null) files.add(file1);
        if(file2 != null) files.add(file2);

        if(!userCheckService.checkUser(offerRequestDto.getUserId())){
            return new ResponseEntity("제안 등록 권한이 없는 user 입니다.", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(offerService.create(offerRequestDto, files), HttpStatus.CREATED);
    }

    /**
     * 목록 조회 (+검색)
     * @param condition
     * @param pageable
     * @return
     */
    @ApiOperation(value = "목록 조회",
                  notes = "제안글 목록을 날짜 내림차순으로 정렬하여 조회합니다.")
    @GetMapping(value = "/")
    public ResponseEntity list(SearchCondition condition, Pageable pageable){
        return new ResponseEntity(offerService.search(condition, pageable), HttpStatus.OK);
    }

    /**
     * 상세 조회
     * @param offerId
     * @return
     */
    @ApiOperation(value = "상세 조회",
                  notes = "제안 상세 내용을 조회합니다.( 제안내용(+첨부한 파일), 답변내용, 등록자정보 확인 가능 )"  )
    @GetMapping(value = "/{userId}/{offerId}")
    public ResponseEntity detail(@PathVariable Long userId, @PathVariable Long offerId){
        if(!userCheckService.hasReadAuth(userId, offerId)){
            return new ResponseEntity("상세 조회 권한이 없는 user 입니다.", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(offerService.detail(offerId), HttpStatus.OK);
    }


    /**
     * 제안 수정
     * @param offerId
     * @param offerUpdateRequestDto
     * @return
     */
    @ApiOperation(value = "제안 수정",
                  notes = "제안 내용을 수정 합니다." )
    @PutMapping(value = "/{offerId}")
    public ResponseEntity update(@PathVariable Long offerId,
                                 @Valid OfferUpdateRequestDto offerUpdateRequestDto, BindingResult result,
                                 @RequestPart(value = "file1", required = false) MultipartFile file1,
                                 @RequestPart(value = "file2", required = false) MultipartFile file2) throws IOException {

        ResponseEntity sb = getResponseEntity(result);
        if (sb != null) return sb;

        List<MultipartFile> files = new ArrayList<>();
        if(file1 != null) files.add(file1);
        if(file2 != null) files.add(file2);

        if(!userCheckService.hasAuth(offerUpdateRequestDto.getUserId(), offerId, null)){
            return new ResponseEntity("수정 권한이 없는 user 입니다.", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(offerService.update(offerId, offerUpdateRequestDto, files), HttpStatus.OK);
    }

    /**
     * 제안 삭제
     * @param offerId
     * @return
     */
    @ApiOperation(value = "제안 삭제",
                  notes = "제안을 삭제합니다" )
    @DeleteMapping(value = "/{offerId}")
    public ResponseEntity delete(@PathVariable Long offerId, @RequestBody UserSessionDto userSessionDto){
        if(!userCheckService.hasAuth(userSessionDto.getUserId(), offerId, null)){
            return new ResponseEntity("삭제 권한이 없는 user 입니다.", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(offerService.delete(offerId), HttpStatus.OK);
    }

    private ResponseEntity getResponseEntity(BindingResult result) {
        if(result.hasErrors()){
            List<ObjectError> list =  result.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for(ObjectError e : list) {
                sb.append(e.getDefaultMessage()+"\n");
            }
            return new ResponseEntity(sb, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
