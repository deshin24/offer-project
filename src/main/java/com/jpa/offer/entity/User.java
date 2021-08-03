package com.jpa.offer.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    // 사용자 pk
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // 사용자 명
    @Column(name="user_name", nullable = false)
    private String name;

    // 사용자 이메일
    @Column(name="user_email", nullable = false)
    private String email;

    // 사용자 권한
    @Enumerated(EnumType.STRING)
    @Column(name="user_role", nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
