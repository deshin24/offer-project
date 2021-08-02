package com.jpa.offer.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseTimeEntity{

    // 답변 pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long Id;

    // 답변 내용
    @Column(name = "answer_content", columnDefinition = "TEXT" , nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" )
    private User user;

    @Builder
    public Answer(String content, User user) {
        this.content = content;
        if(user != null){ changeUSer(user); }
    }

    private Answer changeUSer(User user) {
        this.user = user;
        return this;
    }
}
