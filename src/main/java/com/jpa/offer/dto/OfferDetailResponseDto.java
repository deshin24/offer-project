package com.jpa.offer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpa.offer.entity.Offer;
import com.jpa.offer.entity.OfferServiceType;
import com.jpa.offer.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class OfferDetailResponseDto {

    private Long offerId;
    private String title;
    private String content;
    private OfferServiceType serviceType;
    private String companyName;
    private String managerName;
    private String phone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime offerCreatedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime offerModifiedDate;

    private List<FileResponseDto> files;

    private Long userId;
    private String userName;
    private String userEmail;
    private Role role;

    private Long answerId;
    private String answerContent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime answerCreatedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime answerModifiedDate;


    public OfferDetailResponseDto(Offer offer) {
        this.offerId = offer.getId();
        this.title = offer.getTitle();
        this.content = offer.getContent();
        this.serviceType = offer.getServiceType();
        this.companyName = offer.getCompanyName();
        this.managerName = offer.getManagerName();
        this.phone = offer.getPhone();
        this.offerCreatedDate = offer.getCreatedDate();
        this.offerModifiedDate = offer.getModifiedDate();

        this.userId = offer.getUser().getId();
        this.userName = offer.getUser().getName();
        this.userEmail = offer.getUser().getEmail();
        this.role = offer.getUser().getRole();

        this.answerId = offer.getAnswer().getId();
        this.answerContent = offer.getAnswer().getContent();
        this.answerCreatedDate = offer.getAnswer().getCreatedDate();
        this.answerModifiedDate = offer.getAnswer().getModifiedDate();

    }
}
