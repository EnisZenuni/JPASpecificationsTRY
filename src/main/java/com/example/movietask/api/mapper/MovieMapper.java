package com.example.movietask.api.mapper;

import com.example.movietask.api.dto.MovieDTO;
import com.example.movietask.domain.model.Movie;
import com.example.movietask.api.dto.MovieRequestDTO;

public interface MovieMapper {
    Movie toDomain(MovieRequestDTO movieRequestDTO);
    MovieDTO toDTO(Movie movie);
}
