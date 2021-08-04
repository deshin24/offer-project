package com.jpa.offer.entity;

import com.jpa.offer.dto.OfferUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Offer extends BaseTimeEntity{

    // 제안 pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long id;

    // 제안 제목
    @Column(name = "offer_title", nullable = false)
    private String title;

    // 제안 내용
    @Column(name = "offer_content", columnDefinition = "TEXT", nullable = false)
    private String content;

    // 제안 서비스
    @Column(name = "offer_service_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferServiceType serviceType;

    // 회사명
    @Column(name = "offer_company_name", nullable = false)
    private String companyName;

    // 담당자 명
    @Column(name = "offer_manager_name", nullable = false)
    private String managerName;

    // 담당자 연락처
    @Column(name = "offer_phone", nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" )
    private User user;

    // 연관관계 주인
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id" )
    private Answer answer;

    @Builder
    public Offer(String title, String content, OfferServiceType serviceType, String companyName, String managerName, String phone, User user, Answer answer) {
        this.title = title;
        this.content = content;
        this.serviceType = serviceType;
        this.companyName = companyName;
        this.managerName = managerName;
        this.phone = phone;
        this.createdDate = LocalDateTime.now();
        if(user != null){ changeUser(user); }
        if(answer != null) { changeAnswer(answer); }
    }

    public Offer changeAnswer(Answer answer) {
        this.answer = answer;
        return this;
    }

    public Offer changeUser(User user) {
        this.user = user;
        return this;
    }

    public Offer update(OfferUpdateRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.serviceType = requestDto.getServiceType();
        this.companyName = requestDto.getCompanyName();
        this.managerName = requestDto.getManagerName();
        this.phone = requestDto.getPhone();
        this.modifiedDate = LocalDateTime.now();
        return this;
    }
}
