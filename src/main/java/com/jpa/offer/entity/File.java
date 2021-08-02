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

    // 파일명
    @Column(name = "file_title", length = 50, nullable = false)
    private String title;

    // 파일 경로
    @Column(name = "file_path", columnDefinition = "TEXT")
    private String path;

    // 연관관계 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id" )
    private Offer offer;

    @Builder
    public File(String title, String path, Offer offer) {
        this.title = title;
        this.path = path;
        if(offer != null){ changeOffer(offer); }
    }

    public File changeOffer(Offer offer) {
        this.offer = offer;
        return this;
    }
}
