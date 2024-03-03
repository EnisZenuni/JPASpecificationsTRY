package com.example.movietask.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rating {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Size(min = 1, max = 10, message = "Please insert a rating between 1 and 10")
    @NotEmpty(message = "Can't be empty, please insert a rating between 1 and 10")
    Integer rating;

    @NotEmpty(message = "Please insert review")
    String description;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;
}
