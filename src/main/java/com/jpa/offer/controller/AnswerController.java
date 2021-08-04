package com.jpa.offer.controller;

import com.jpa.offer.dto.AnswerCreateRequestDto;
import com.jpa.offer.dto.AnswerUpdateRequestDto;
import com.jpa.offer.service.AnswerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;

    /**
     * 답변 등록
     * @param offerId
     * @param answerCreateRequestDto
     * @return
     */
    @ApiOperation(value = "답변 등록",
                  notes = "답변을 등록합니다." )
    @PostMapping("/{offerId}")
    public ResponseEntity create(@PathVariable Long offerId,
                                 @RequestBody AnswerCreateRequestDto answerCreateRequestDto){
        return new ResponseEntity(answerService.create(offerId, answerCreateRequestDto), HttpStatus.CREATED);
    }


    /**
     * 답변 수정
     * @param answerId
     * @param answerUpdateRequestDto
     * @return
     */
    @ApiOperation(value = "답변 수정",
                  notes = "답변을 수정합니다." )
    @PutMapping("/{answerId}")
    public ResponseEntity update(@PathVariable Long answerId,
                                 @RequestBody AnswerUpdateRequestDto answerUpdateRequestDto){
        return new ResponseEntity(answerService.update(answerId, answerUpdateRequestDto), HttpStatus.OK);
    }
}
