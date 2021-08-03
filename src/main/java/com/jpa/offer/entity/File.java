package com.jpa.offer.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    // 파일 pk
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    // 원본 파일명
    @Column(name = "file_org_title", length = 50)
    private String orgTitle;

    // aws s3 버킷에 저장하는 파일명
    @Column(name = "file_aws_title")
    private String awsTitle;

    // 파일 경로
    @Column(name = "file_path", columnDefinition = "TEXT")
    private String path;

    // 연관관계 주인
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "offer_id" )
    private Offer offer;

    @Builder
    public File(String orgTitle, String awsTitle, String path, Offer offer) {
        this.orgTitle = orgTitle;
        this.awsTitle = awsTitle;
        this.path = path;
        if(offer != null){ changeOffer(offer); }
    }

    public File changeOffer(Offer offer) {
        this.offer = offer;
        return this;
    }
}
