package com.jpa.offer.repository;

import com.jpa.offer.dto.OfferDetailResponseDto;
import com.jpa.offer.dto.OfferListResponseDto;
import com.jpa.offer.dto.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferRepositoryCustom {

    // 제안글 목록
    Page<OfferListResponseDto> search(SearchCondition condition, Pageable pageable);

    // 제안 상세 ( + 파일 리스트 )
    OfferDetailResponseDto detail(Long id);
}
