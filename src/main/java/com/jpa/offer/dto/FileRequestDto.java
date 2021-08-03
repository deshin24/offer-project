package com.jpa.offer.dto;

import com.jpa.offer.entity.File;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileRequestDto {

    private String orgTitle;
    private String awsTitle;
    private String path;

    public File toEntity(){
        return File.builder()
                .orgTitle(orgTitle)
                .awsTitle(awsTitle)
                .path(path)
                .build();
    }

}
