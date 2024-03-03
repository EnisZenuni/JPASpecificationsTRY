package com.example.movietask.api.mapper;

import com.example.movietask.api.dto.MovieDTO;
import com.example.movietask.api.dto.MovieRequestDTO;
import com.example.movietask.domain.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MovieMapperImpl implements MovieMapper {
    private final RatingMapper ratingMapper;
    @Override
    public Movie toDomain(MovieRequestDTO movieRequestDTO) {
        return Movie.builder()
                .movieName(movieRequestDTO.getTitle())
                .description(movieRequestDTO.getDescription())
                .releaseYear(movieRequestDTO.getReleaseYear())
                .genre(movieRequestDTO.getGenre())
                .build();
    }

    @Override
    public MovieDTO toDTO(Movie movie) {
        return MovieDTO.builder()
                .movieName(movie.getMovieName())
                .description(movie.getDescription())
                .genre(movie.getGenre())
                .releaseYear(movie.getReleaseYear())
                .ratingList(movie.getRatingList()
                        .stream()
                        .map(ratingMapper::toDTO)
                        .collect(Collectors.toList()))
                .averageRating(movie.getAverageRating())
                .build();
    }
}
