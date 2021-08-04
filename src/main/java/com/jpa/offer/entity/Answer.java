package com.jpa.offer.entity;

import com.jpa.offer.dto.AnswerUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseTimeEntity{

    // 답변 pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    // 답변 내용
    @Column(name = "answer_content", columnDefinition = "TEXT" , nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" )
    private User user;

    @Builder
    public Answer(String content, User user) {
        this.content = content;
        this.createdDate = LocalDateTime.now();
        if(user != null){ changeUser(user); }
    }

    public Answer changeUser(User user) {
        this.user = user;
        return this;
    }

    public Answer update(AnswerUpdateRequestDto requestDto){
        this.content = requestDto.getContent();
        this.modifiedDate = LocalDateTime.now();
        return this;
    }
}
