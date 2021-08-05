package com.jpa.offer.dto;

import com.jpa.offer.entity.OfferServiceType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OfferUpdateRequestDto {

    @ApiModelProperty(value = "유저키", required = true, example = "6")
    private Long userId;

    @ApiModelProperty(value = "제목", example = "제안합니다")
    @NotNull @Size(min = 2, max = 50, message = "제목을 2~50자 사이로 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "내용", example = "저희 00의 상품 판매를 제안합니다")
    @NotNull@Size(max = 3000, message = "제목을 ~3000자 사이로 입력해주세요.")
    private String content;

    @ApiModelProperty(value = "서비스 타입", required = true, example = "SNACK24")
    @NotNull(message = "서비스 타입을 선택해 주세요")
    private OfferServiceType serviceType;

    @ApiModelProperty(value = "기업", required = true, example = "A1회사")
    @NotNull @Size(min = 2, max = 50, message = "기업명을 2~50자 사이로 입력해주세요.")
    private String companyName;

    @ApiModelProperty(value = "담당자", required = true, example = "직원1")
    @NotNull @Size(min = 2, max = 10, message = "담당자명을 2~10자 사이로 입력해주세요.")
    private String managerName;

    @ApiModelProperty(value = "전화번호", required = true, example = "010-1111-2222")
    @NotNull
    @Pattern(regexp = "^01(?:0|1|[6-9])-(\\d{3}|\\d{4})-(\\d{4})$", message = "숫자, -을 포함해 휴대전화 형식에 맞게 입력해주세요.")
    private String phone;

    @ApiModelProperty(value = "피일 삭제 여부1", required = true, example = "false")
    private Boolean isNeedFileDel1;
    @ApiModelProperty(value = "피일 삭제 여부2", required = true, example = "false")
    private Boolean isNeedFileDel2;

}
