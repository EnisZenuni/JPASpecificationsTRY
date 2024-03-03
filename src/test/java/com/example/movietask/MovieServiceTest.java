package com.example.movietask;

import com.example.movietask.api.dto.MovieDTO;
import com.example.movietask.api.dto.MovieRequestDTO;
import com.example.movietask.api.mapper.MovieMapper;
import com.example.movietask.domain.exceptions.MovieNotFoundException;
import com.example.movietask.domain.model.Movie;
import com.example.movietask.domain.model.Rating;
import com.example.movietask.repository.MovieRepository;
import com.example.movietask.service.MovieServiceImpl;
import com.example.movietask.service.RatingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RatingService ratingService;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    void createMovie_SuccessfullyCreatesMovie() {

        MovieRequestDTO movieRequestDTO = new MovieRequestDTO();
        movieRequestDTO.setTitle("Sample Movie");

        Movie mappedMovie = new Movie();

        when(movieMapper.toDomain(movieRequestDTO)).thenReturn(mappedMovie);
        when(movieRepository.save(mappedMovie)).thenReturn(mappedMovie);

        Movie createdMovie = movieService.create(movieRequestDTO);

        assertEquals(mappedMovie, createdMovie);

        verify(movieMapper, times(1)).toDomain(movieRequestDTO);
        verify(movieRepository, times(1)).save(mappedMovie);
    }

    @Test
    public void getMovie_ReturnsMovieDTO_WhenMovieExists() {

        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setId(movieId);
        MovieDTO expectedMovieDTO = new MovieDTO();
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(movieMapper.toDTO(movie)).thenReturn(expectedMovieDTO);

        MovieDTO result = movieService.getMovie(movieId);

        assertEquals(expectedMovieDTO, result);
        verify(movieRepository, times(1)).findById(movieId);
        verify(movieMapper, times(1)).toDTO(movie);
    }

    @Test
    void getMovie_ThrowsMovieNotFoundException_WhenMovieDoesNotExist() {

        Long movieId = 1L;
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());


        assertThrows(MovieNotFoundException.class, () -> movieService.getMovie(movieId));
        verify(movieRepository, times(1)).findById(movieId);
    }

    @Test
    void addRatingToMovie_SuccessfullyAddsRating() {

        Long movieId = 1L;
        Rating rating = new Rating();
        rating.setRating(5);

        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setMovieName("Sample Movie");

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        String result = movieService.addRatingToMovie(movieId, rating);

        assertEquals("Successfully added rating 5 to the movie Sample Movie", result);
        verify(ratingService, times(1)).create(rating);
        verify(movieRepository, times(2)).findById(movieId);
        verify(movieRepository, times(1)).save(movie);

        assertEquals(1, movie.getRatingList().size());
        assertEquals(5, movie.getAverageRating());
    }

    @Test
    void filterAndPaginateMovies_ReturnsPageOfMovies() {

        Specification<Movie> spec = Specification.where(null);
        Pageable pageable = mock(Pageable.class);
        List<Movie> movieList = new ArrayList<>();
        Page<Movie> moviePage = new PageImpl<>(movieList);

        when(movieRepository.findAll(spec, pageable)).thenReturn(moviePage);

        Page<Movie> result = movieService.filterAndPaginateMovies(spec, pageable);

        assertEquals(moviePage, result);
        verify(movieRepository, times(1)).findAll(spec, pageable);
    }

    @Test
    void filterMovies_ReturnsPageOfMovieDTOs() {
        String title = "Sample Title";
        String genre = "Action";
        List<String> genres = List.of("Action", "Drama");
        String releaseYear = "2022";
        String fromYear = "2020";
        String toYear = "2023";
        Pageable pageable = PageRequest.of(0, 10);

        Specification<Movie> spec = Specification.where(null);

        when(movieRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));

        Page<MovieDTO> result = movieService.filterMovies(title, genre, genres, releaseYear, fromYear, toYear, pageable);

        assertNotNull(result);
    }
}
