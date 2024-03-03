package com.example.movietask.service;

import com.example.movietask.api.dto.MovieDTO;
import com.example.movietask.api.dto.MovieRequestDTO;
import com.example.movietask.api.mapper.MovieMapper;
import com.example.movietask.domain.exceptions.MovieNotFoundException;
import com.example.movietask.domain.model.Movie;
import com.example.movietask.domain.model.Rating;
import com.example.movietask.repository.MovieRepository;
import com.example.movietask.repository.SpecificationDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final RatingService ratingService;
    private final MovieMapper movieMapper;

    @Override
    public MovieDTO getMovie(Long movieId) {
        return movieMapper.toDTO(movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId)));
    }

    @Override
    @Transactional
    public String addRatingToMovie(Long movieId, Rating rating) {
        rating.setMovie(movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId)));
        ratingService.create(rating);

        var rateMovie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException(movieId));
        rateMovie.getRatingList().add(rating);
        rateMovie.updateAverageRating();
        movieRepository.save(rateMovie);

        return String.format("Successfully added rating %d to the movie %s", rating.getRating(), rateMovie.getMovieName());
    }

    public Page<Movie> filterAndPaginateMovies(Specification<Movie> spec, Pageable pageable) {
        return movieRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public Movie create(MovieRequestDTO movieRequestDTO) {
        return movieRepository.save(movieMapper.toDomain(movieRequestDTO));
    }

    public Page<MovieDTO> filterMovies(String title, String genre, List<String> genres, String releaseYear, String fromYear, String toYear,Pageable pageable) {
        Specification<Movie> spec = Specification.where(null);

        if (title != null && !title.isEmpty()) {
            spec = spec.and(SpecificationDAO.filterByMovieTitle(title));
        }

        if (genre != null && !genre.isEmpty()) {
            spec = spec.and(SpecificationDAO.filterByMovieGenre(genre));
        }

        if (genres != null && !genres.isEmpty()) {
            spec = spec.and(SpecificationDAO.filterMoviesByGenres(genres));
        }

        if (releaseYear != null && !releaseYear.isEmpty()) {
            spec = spec.and(SpecificationDAO.filterMovieByReleaseYear(releaseYear));
        }

        if (fromYear != null && toYear != null && !fromYear.isEmpty() && !toYear.isEmpty()) {
            spec = spec.and(SpecificationDAO.filterMoviesInBetweenYears(fromYear, toYear));
        }
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<MovieDTO> movieDTOs = movieRepository.findAll(spec).stream().map(movieMapper::toDTO).toList();

        return new PageImpl<>(movieDTOs, pageable, moviePage.getTotalElements());
    }
}
