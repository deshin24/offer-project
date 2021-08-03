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
     * 게시글 등록
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
     * @param id
     * @return
     */
    public OfferDetailResponseDto detail(Long id) {
        return offerRepository.detail(id);
    }

    /**
     * 제안글 수정
     * @param id
     * @param offerUpdateRequestDto
     * @return
     */
    @Transactional
    public Long update(Long id, OfferUpdateRequestDto offerUpdateRequestDto, List<MultipartFile> files) throws IOException {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 제안글 입니다."));

       // 파일 삭제 플래그가 Y인 경우에는 기존 파일 삭제
        List<FileDelRequestDto> fileDelYns = offerUpdateRequestDto.getFileDelYns();
        if(fileDelYns.size() > 0 ){
            for(FileDelRequestDto dto : fileDelYns){
                File file = fileRepository.findById(dto.getId())
                        .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 파일 입니다."));
                fileService.delete(file);
            }
        }

        if(files.size() > 0) fileService.saveList(offer, files);

        return offer.update(offerUpdateRequestDto).getId();
    }

    @Transactional
    public Long delete(Long id){
        Long offerId = offerRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 제안글 입니다.")).getId();

        // file 삭제
        fileService.deleteList(offerId);

        // offer 삭제
        offerRepository.deleteById(offerId);

        return offerId;
    }

}
