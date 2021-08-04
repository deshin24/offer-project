package com.jpa.offer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpa.offer.entity.Offer;
import com.jpa.offer.entity.OfferServiceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OfferListResponseDto {

    private Long id;
    private String title;
    private String content;
    private OfferServiceType serviceType;
    private String companyName;
    private String managerName;
    private String phone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedDate;

    public OfferListResponseDto(Offer offer) {
        this.id = offer.getId();
        this.title = offer.getTitle();
        this.content = offer.getContent();
        this.serviceType = offer.getServiceType();
        this.companyName = offer.getCompanyName();
        this.managerName = offer.getManagerName();
        this.phone = offer.getPhone();
        this.createdDate = offer.getCreatedDate();
        this.modifiedDate = offer.getModifiedDate();
    }

}
