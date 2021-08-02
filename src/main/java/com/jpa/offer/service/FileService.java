package com.jpa.offer.service;

import com.jpa.offer.dto.FileRequestDto;
import com.jpa.offer.entity.Offer;
import com.jpa.offer.file.S3Service;
import com.jpa.offer.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Service s3Service;
    private final FileRepository fileRepository;

    /**
     * 파일 List 저장
     * @param offer
     * @param files
     * @throws IOException
     */
    public void saveList(Offer offer, List<MultipartFile> files) throws IOException {
        for(MultipartFile file : files){
            FileRequestDto fileRequestDto = new FileRequestDto();
            String filePath = s3Service.upload(file);
            fileRequestDto.setPath(filePath);
            fileRequestDto.setTitle(file.getOriginalFilename());
            fileRepository.save(fileRequestDto.toEntity().changeOffer(offer));
        }
    }
}

