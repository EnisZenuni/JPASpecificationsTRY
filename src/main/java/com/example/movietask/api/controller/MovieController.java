package com.example.movietask.api.controller;

import com.example.movietask.api.dto.MovieDTO;
import com.example.movietask.api.dto.MovieRequestDTO;
import com.example.movietask.domain.model.Movie;
import com.example.movietask.domain.model.Rating;
import com.example.movietask.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("movies/{id}")
    public MovieDTO getMovieDetails(@PathVariable("id") Long movieId) {
        return movieService.getMovie(movieId);
    }


    @PostMapping("/movies")
    public Movie addMovie(@RequestBody MovieRequestDTO movieRequestDTO) {
        return movieService.create(movieRequestDTO);
    }


    @GetMapping("/movies")
    public Page<MovieDTO> listMovies(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "genre", required = false) String genre,
            @RequestParam(name = "genres", required = false) List<String> genres,
            @RequestParam(name = "releaseYear", required = false) String releaseYear,
            @RequestParam(name = "fromYear", required = false) String fromYear,
            @RequestParam(name = "toYear", required = false) String toYear,
            Pageable pageable ) {
        return movieService.filterMovies(title, genre, genres, releaseYear, fromYear, toYear,pageable);
    }

    @PostMapping("/movies/{id}/review")
    public String addRatingToMovie(@PathVariable("id") Long id,@RequestBody Rating rating) {
        return movieService.addRatingToMovie(id, rating);
    }
}
