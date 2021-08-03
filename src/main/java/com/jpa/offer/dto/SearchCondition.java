package com.jpa.offer.dto;

import com.jpa.offer.entity.OfferServiceType;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchCondition {

    @ApiParam(value = "[검색 조건] 제목", example = "제안")
    private String title;

    @ApiParam(value = "[검색 조건] 내용", example = "제안")
    private String content;

    @ApiParam(value = "[검색 조건] 서비스 타입", example = "SNACK24")
    private OfferServiceType serviceType;

    @ApiParam(value = "[검색 조건] 기업명", example = "A기업")
    private String company;

}
