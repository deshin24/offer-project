package com.jpa.offer.dto;

import com.jpa.offer.entity.OfferServiceType;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchCondition {

    @ApiParam(value = "[검색 조건] 제목, 내용, 기업명, offerId(숫자)", example = "제안드려요")
    private String searchKeyword;

    @ApiParam(value = "[검색 조건] 서비스 타입", example = "SNACK24")
    private OfferServiceType serviceType;

}
