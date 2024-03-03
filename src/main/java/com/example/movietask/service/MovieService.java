package com.example.movietask.service;

import com.example.movietask.api.dto.MovieDTO;
import com.example.movietask.domain.model.Movie;
import com.example.movietask.domain.model.Rating;
import com.example.movietask.api.dto.MovieRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface MovieService {
    MovieDTO getMovie(Long movieId);

    String addRatingToMovie(Long movieId, Rating rating);

    public Page<Movie> filterAndPaginateMovies(Specification<Movie> spec, Pageable pageable);

    Movie create(MovieRequestDTO movieRequestDTO);

    Page<MovieDTO> filterMovies(String title, String genre, List<String> genres, String releaseYear, String fromYear, String toYear,Pageable pageable);
}
