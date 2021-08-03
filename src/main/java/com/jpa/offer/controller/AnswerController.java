package com.jpa.offer.controller;

import com.jpa.offer.dto.AnswerCreateRequestDto;
import com.jpa.offer.service.AnswerService;
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
    @GetMapping("/{offerId}")
    public ResponseEntity create(@PathVariable Long offerId,
                                 @RequestBody AnswerCreateRequestDto answerCreateRequestDto){
        return new ResponseEntity(answerService.create(offerId, answerCreateRequestDto), HttpStatus.CREATED);
    }


}
