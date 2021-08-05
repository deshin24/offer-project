package com.jpa.offer.service;

import com.jpa.offer.dto.*;
import com.jpa.offer.entity.File;
import com.jpa.offer.entity.Offer;
import com.jpa.offer.entity.User;
import com.jpa.offer.repository.FileRepository;
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
    private final FileRepository fileRepository;

    /**
     * 제안 등록
     * @param offerRequestDto
     * @return
     */
    @Transactional
    public Long create(OfferCreateRequestDto offerRequestDto, List<MultipartFile> files) throws IOException {
        User user = userRepository.findById(offerRequestDto.getUserId())
                .orElseThrow( () ->new EntityNotFoundException("존재하지 않는 사용자 입니다."));

        Offer offer = offerRepository.save(offerRequestDto.toEntity().changeUser(user));

        if(files.size() > 0) fileService.saveList(offer, files);

        return offer.getId();
    }

    /**
     * 전체 리스트
     * @return
     */
    public List<OfferListResponseDto> list() {
        return offerRepository.findAll().stream()
                .map(OfferListResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 목록 조회 (+검색)
     * @param condition
     * @param pageable
     * @return
     */
    public Page<OfferListResponseDto> search(SearchCondition condition, Pageable pageable) {
        return offerRepository.search(condition, pageable);
    }

    /**
     * 상세 조회
     * @param offerId
     * @return
     */
    public OfferDetailResponseDto detail(Long offerId) {
        return offerRepository.detail(offerId);
    }

    /**
     * 제안 수정
     * @param offerId
     * @param offerUpdateRequestDto
     * @return
     */
    @Transactional
    public Long update(Long offerId, OfferUpdateRequestDto offerUpdateRequestDto, List<MultipartFile> files) throws IOException {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 제안글 입니다."));

        // 전체 결과 파일 수 체크 => 2개 이상일 경우 exception
        List<File> findFiles = fileRepository.findByOfferId(offerId);
        int totalFile = findFiles.size();
        if(offerUpdateRequestDto.getIsNeedFileDel1()) totalFile -= 1;
        if(offerUpdateRequestDto.getIsNeedFileDel2()) totalFile -= 1;
        totalFile += files.size();
        if( totalFile > 2 ) {
             throw new IllegalArgumentException("2개 이상의 파일을 저장할 수 없습니다.");
        }

        // 기존 파일 삭제
        if(offerUpdateRequestDto.getIsNeedFileDel1() || offerUpdateRequestDto.getIsNeedFileDel2()){
            fileService.delete(offerId,offerUpdateRequestDto.getIsNeedFileDel1(), offerUpdateRequestDto.getIsNeedFileDel2());
        }

        // 새로운 파일 등록
        if(files.size() > 0) fileService.saveList(offer, files);

        return offer.update(offerUpdateRequestDto).getId();
    }

    /**
     * 제안 삭제
     * @param offerId
     * @return
     */
    @Transactional
    public Long delete(Long offerId){
        Long findOfferId = offerRepository.findById(offerId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 제안글 입니다.")).getId();

        // file 삭제
        fileService.deleteList(findOfferId);

        // offer 삭제
        offerRepository.deleteById(findOfferId);

        return findOfferId;
    }

}
