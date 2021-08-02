package com.jpa.offer.controller;

import com.jpa.offer.dto.OfferRequestDto;
import com.jpa.offer.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/offer")
public class OfferController {

    private final OfferService offerService;

    /**
     * 게시글 등록
     * @param offerRequestDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity create(@RequestBody OfferRequestDto offerRequestDto){

        return new ResponseEntity(offerService.create(offerRequestDto), HttpStatus.CREATED);
    }


}
