package com.example.movietask.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @NonNull
    @NotBlank
    String movieName;

    @NonNull
    @NotBlank
    String description;

    @NonNull
    @NotBlank
    @ElementCollection(fetch = FetchType.LAZY)
    List<String> genre;

    @NonNull
    @NotBlank
    String releaseYear;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie", fetch = FetchType.LAZY)
    List<Rating> ratingList = new ArrayList<>();

    public Integer getAverageRating() {
        return (ratingList != null && !ratingList.isEmpty()) ?
                (int) ratingList.stream().mapToDouble(Rating::getRating).average().orElse(1.0) :
                0;
    }

    Integer averageRating;

    public void updateAverageRating() {
        this.averageRating = getAverageRating();
    }
}
