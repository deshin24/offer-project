package com.jpa.offer.controller;

import com.jpa.offer.dto.OfferRequestDto;
import com.jpa.offer.dto.SearchCondition;
import com.jpa.offer.service.OfferService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/offer")
public class OfferController {

    private final OfferService offerService;

    /**
     * 제안글 등록
     * @param offerRequestDto
     * @return
     */
    @ApiOperation(value = "제안글 등록")
    @PostMapping(value = "/", produces = "application/json; charset=UTF-8")
    public ResponseEntity create(OfferRequestDto offerRequestDto,
                                 @RequestPart(value = "file1", required = false) MultipartFile file1,
                                 @RequestPart(value = "file2", required = false) MultipartFile file2) throws IOException {
        List<MultipartFile> files = new ArrayList<>();
        if(file1 != null) files.add(file1);
        if(file2 != null) files.add(file2);
        return new ResponseEntity(offerService.create(offerRequestDto, files), HttpStatus.CREATED);
    }

    /**
     * 목록 조회 (+검색)
     * @param condition
     * @param pageable
     * @return
     */
    @ApiOperation(value = "목록 조회")
    @GetMapping(value = "/")
    public ResponseEntity list(SearchCondition condition, Pageable pageable){
        return new ResponseEntity(offerService.search(condition, pageable), HttpStatus.OK);
    }

    /**
     * 상세 조회
     * @param id
     * @return
     */
    @ApiOperation(value = "상세 조회",
                  notes = "제안 상세 내용을 조회합니다.( 제안내용(+첨부한 파일), 답변내용, 등록자정보 확인 가능 )"  )
    @GetMapping(value = "/{id}")
    public ResponseEntity detail(@PathVariable Long id){
        return new ResponseEntity(offerService.detail(id), HttpStatus.OK);
    }


}
