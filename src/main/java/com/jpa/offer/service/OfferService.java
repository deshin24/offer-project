package com.jpa.offer.service;

import com.jpa.offer.dto.OfferListResponseDto;
import com.jpa.offer.dto.OfferRequestDto;
import com.jpa.offer.dto.OfferDetailResponseDto;
import com.jpa.offer.dto.SearchCondition;
import com.jpa.offer.entity.Offer;
import com.jpa.offer.entity.User;
import com.jpa.offer.repository.OfferRepository;
import com.jpa.offer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    /**
     * 게시글 등록
     * @param offerRequestDto
     * @return
     */
    @Transactional
    public Long create(OfferRequestDto offerRequestDto, List<MultipartFile> files) throws IOException {
        User user = userRepository.findById(offerRequestDto.getUserId())
                .orElseThrow( () ->new EntityNotFoundException("존재하지 않는 사용자 입니다."));

        Offer offer = offerRepository.save(offerRequestDto.toEntity().changeUser(user));

        if(files.size() > 0) fileService.saveList(offer, files);

        return offer.getId();
    }

    public List<OfferListResponseDto> list() {
        return offerRepository.findAll().stream()
                .map(OfferListResponseDto::new).collect(Collectors.toList());
    }

    public Page<OfferListResponseDto> search(SearchCondition condition, Pageable pageable) {
        return offerRepository.search(condition, pageable);
    }
}
