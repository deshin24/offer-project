package com.jpa.offer.service;

import com.jpa.offer.dto.FileRequestDto;
import com.jpa.offer.entity.File;
import com.jpa.offer.entity.Offer;
import com.jpa.offer.file.S3Service;
import com.jpa.offer.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
            String fileName = s3Service.upload(file);
            fileRequestDto.setOrgTitle(file.getOriginalFilename());
            fileRequestDto.setAwsTitle(fileName);
            fileRequestDto.setPath("https://" + s3Service.CLOUD_FRONT_DOMAIN_NAME + "/" + fileName);
            fileRepository.save(fileRequestDto.toEntity().changeOffer(offer));
        }
    }

    /**
     * 파일 List 삭제
     * @param offerId
     */
    @Transactional
    public void deleteList(Long offerId){
        List<File> files = fileRepository.findByOfferId(offerId);
        for(File file : files){
            s3Service.delete(file.getAwsTitle());
        }

        fileRepository.deleteByOfferId(offerId);
    }


    /**
     * 파일 개별 삭제
     * @param
     */
    @Transactional
    public void delete(Long offerId, Boolean isNeedFileDel1, Boolean isNeedFileDel2) {
        List<File> files = fileRepository.findByOfferId(offerId);
        File file1 = files.get(0);
        File file2 = files.get(1);

        if(isNeedFileDel1){
            s3Service.delete(file1.getAwsTitle());
            fileRepository.deleteById(file1.getId());
        }else if(isNeedFileDel2){
            s3Service.delete(file2.getAwsTitle());
            fileRepository.deleteById(file2.getId());
        }
    }
}

