package com.jpa.offer.controller;

import com.jpa.offer.dto.OfferRequestDto;
import com.jpa.offer.service.FileService;
import com.jpa.offer.service.OfferService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
    private final FileService fileService;

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

}
