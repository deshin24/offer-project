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
    private String company;
    private String manager;
    private String phone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime modifiedTime;

    public OfferListResponseDto(Offer offer) {
        this.id = offer.getId();
        this.title = offer.getTitle();
        this.content = offer.getContent();
        this.serviceType = offer.getServiceType();
        this.company = offer.getCompany();
        this.manager = offer.getManager();
        this.phone = offer.getPhone();
        this.createdTime = offer.getCreatedDate();
        this.modifiedTime = offer.getModifiedDate();
    }

}
