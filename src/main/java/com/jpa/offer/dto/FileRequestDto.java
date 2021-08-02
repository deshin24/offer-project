package com.jpa.offer.dto;

import com.jpa.offer.entity.File;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileRequestDto {

    private String title;
    private String path;

    public File toEntity(){
        return File.builder()
                .title(title)
                .path(path)
                .build();
    }

}
