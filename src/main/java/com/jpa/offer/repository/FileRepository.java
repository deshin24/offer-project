package com.jpa.offer.repository;

import com.jpa.offer.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    // 제안글의 파일 삭제
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM File f WHERE f.offer.id = :offerId")
    void deleteByOfferId(Long offerId);

    // 제안글의 파일 리스트
    List<File> findByOfferId(Long offerId);
}
