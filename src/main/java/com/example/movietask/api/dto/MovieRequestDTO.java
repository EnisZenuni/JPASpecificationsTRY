package com.example.movietask.api.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MovieRequestDTO {
    String title;
    String description;
    List<String> genre;
    String releaseYear;
}
