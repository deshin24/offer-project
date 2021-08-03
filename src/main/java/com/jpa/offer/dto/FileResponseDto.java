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

    @Builder
    public FileResponseDto(Long id, String title, String path) {
        this.id = id;
        this.title = title;
        this.path = path;
    }
}
