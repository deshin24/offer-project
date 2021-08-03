package com.jpa.offer.dto;

import com.jpa.offer.entity.Offer;
import com.jpa.offer.entity.OfferServiceType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OfferCreateRequestDto {

    @ApiModelProperty(value = "유저키", required = true, example = "1")
    private Long userId;
    @ApiModelProperty(value = "제목", required = true, example = "제안합니다")
    private String title;
    @ApiModelProperty(value = "내용", required = true, example = "자사와 ~~~ 제안합니다")
    private String content;
    @ApiModelProperty(value = "서비스 타입", required = true, example = "SNACK24")
    private OfferServiceType serviceType;
    @ApiModelProperty(value = "기업", required = true, example = "기업A")
    private String companyName;
    @ApiModelProperty(value = "담당자", required = true, example = "담당자A")
    private String managerName;
    @ApiModelProperty(value = "전화번호", required = true, example = "010-1111-2222")
    private String phone;

    public Offer toEntity(){
        return Offer.builder()
                .title(title)
                .content(content)
                .serviceType(serviceType)
                .companyName(companyName)
                .managerName(managerName)
                .phone(phone)
                .build();
    }
}
