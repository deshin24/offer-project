package com.jpa.offer.controller;

import com.jpa.offer.dto.AnswerCreateRequestDto;
import com.jpa.offer.dto.AnswerUpdateRequestDto;
import com.jpa.offer.dto.UserSessionDto;
import com.jpa.offer.service.AnswerService;
import com.jpa.offer.service.UserCheckService;
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
    private final UserCheckService userCheckService;

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

        if(!userCheckService.checkAdmin(answerCreateRequestDto.getUserId())){
            return new ResponseEntity("등록 권한이 없는 user 입니다.", HttpStatus.UNAUTHORIZED);
        }

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

        if(!userCheckService.hasAuth(answerUpdateRequestDto.getUserId(), null, answerId)){
            return new ResponseEntity("수정 권한이 없는 user 입니다.", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(answerService.update(answerId, answerUpdateRequestDto), HttpStatus.OK);
    }

    /**
     * 답변 삭제
     * @param offerId
     * @param answerId
     * @return
     */
    @ApiOperation(value = "답변 삭제",
                  notes = "답변을 삭제합니다." )
    @DeleteMapping("/{offerId}/{answerId}")
    public ResponseEntity delete(@PathVariable Long offerId, @PathVariable Long answerId,
                                 @RequestBody UserSessionDto userSessionDto){
        if(!userCheckService.hasAuth(userSessionDto.getUserId(), null, answerId)){
            return new ResponseEntity("삭제 권한이 없는 user 입니다.", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(answerService.delete(offerId, answerId), HttpStatus.OK);
    }

}
