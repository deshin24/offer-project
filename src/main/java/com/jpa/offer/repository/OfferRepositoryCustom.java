package com.jpa.offer.repository;

import com.jpa.offer.dto.OfferListResponseDto;
import com.jpa.offer.dto.SearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferRepositoryCustom {
    Page<OfferListResponseDto> search(SearchCondition condition, Pageable pageable);


}
