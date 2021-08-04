package com.jpa.offer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerUpdateRequestDto {

    @ApiModelProperty(value = "답변 내용", required = true, example = "답변드립니다.22")
    private String content;

}
