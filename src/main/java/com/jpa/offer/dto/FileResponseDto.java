package com.jpa.offer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FileResponseDto {

    private Long id;
    private String title;
    private String path;

}
