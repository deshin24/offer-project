package com.jpa.offer.service;

import com.jpa.offer.dto.OfferRequestDto;
import com.jpa.offer.entity.User;
import com.jpa.offer.repository.OfferRepository;
import com.jpa.offer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    /**
     * 게시글 등록
     * @param offerRequestDto
     * @return
     */
    @Transactional
    public Long create(OfferRequestDto offerRequestDto) {
        User user = userRepository.findById(offerRequestDto.getUserId())
                .orElseThrow( () ->new EntityNotFoundException("존재하지 않는 사용자 입니다."));

        return offerRepository.save(offerRequestDto.toEntity().changeUser(user)).getId();
    }
}
