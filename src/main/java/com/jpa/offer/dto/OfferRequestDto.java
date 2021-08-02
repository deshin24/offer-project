package com.jpa.offer.dto;

import com.jpa.offer.entity.Offer;
import com.jpa.offer.entity.OfferServiceType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OfferRequestDto {

    private Long userId;
    private String title;
    private String content;
    private OfferServiceType serviceType;
    private String company;
    private String manager;
    private String phone;

    public Offer toEntity(){
        return Offer.builder()
                .title(title)
                .content(content)
                .serviceType(serviceType)
                .company(company)
                .manager(manager)
                .phone(phone)
                .build();
    }
}
