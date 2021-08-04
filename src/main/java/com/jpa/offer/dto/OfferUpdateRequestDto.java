package com.jpa.offer.dto;

import com.jpa.offer.entity.OfferServiceType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OfferUpdateRequestDto {

    @ApiModelProperty(value = "유저키", required = true, example = "6")
    private Long userId;
    @ApiModelProperty(value = "제목", example = "제안합니다")
    private String title;
    @ApiModelProperty(value = "내용", example = "저희 00의 상품 판매를 제안합니다")
    private String content;
    @ApiModelProperty(value = "서비스 타입", example = "SNACK24")
    private OfferServiceType serviceType;
    @ApiModelProperty(value = "기업", example = "기업A")
    private String companyName;
    @ApiModelProperty(value = "담당자", example = "담당자A")
    private String managerName;
    @ApiModelProperty(value = "전화번호", example = "010-1111-3333")
    private String phone;

    @ApiModelProperty(value = "파일 삭제 여부")
    private List<FileDelRequestDto> fileDelYns = new ArrayList<>();

}
