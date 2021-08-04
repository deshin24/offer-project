package com.jpa.offer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSessionDto {
    @ApiModelProperty(value = "유저키", required = true, example = "1")
    private Long userId;
}
