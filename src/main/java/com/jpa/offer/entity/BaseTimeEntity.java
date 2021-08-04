package com.jpa.offer.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 공통 매핑 정보 super class
public abstract class BaseTimeEntity {

    @Column(name = "create_date")
    LocalDateTime createdDate;

    @Column(name = "modify_date")
    LocalDateTime modifiedDate;
}
