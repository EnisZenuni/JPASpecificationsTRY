package com.example.movietask.api.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
     String movieName;
     String description;
     List<String> genre;
     String releaseYear;
     List<RatingDTO> ratingList;
     Integer averageRating;
}
