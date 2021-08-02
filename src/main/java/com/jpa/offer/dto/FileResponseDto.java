package com.jpa.offer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FileResponseDto {

    private Long id;
    private String title;
    private String path;
    private String fullPath;

    @Builder
    public FileResponseDto(Long id, String title, String path, String fullPath) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.fullPath = fullPath;
    }
}
