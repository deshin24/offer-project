package com.jpa.offer.service;

import com.jpa.offer.dto.OfferRequestDto;
import com.jpa.offer.entity.Offer;
import com.jpa.offer.entity.User;
import com.jpa.offer.repository.OfferRepository;
import com.jpa.offer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

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

}
