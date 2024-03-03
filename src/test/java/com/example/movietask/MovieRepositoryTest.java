package com.example.movietask;

import com.example.movietask.domain.model.Movie;
import com.example.movietask.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void findById_WhenIdExists_ReturnsMovie() {
        Movie movie = new Movie();
        movie.setMovieName("Example Movie");
        Movie savedMovie = movieRepository.save(movie);

        Optional<Movie> result = movieRepository.findById(savedMovie.getId());

        assertTrue(result.isPresent());
        assertEquals(savedMovie.getId(), result.get().getId());
        assertEquals(savedMovie.getMovieName(), result.get().getMovieName());
    }

    @Test
    void findById_WhenIdDoesNotExist_ReturnsEmpty() {
        Long nonExistentId = 999L;

        Optional<Movie> result = movieRepository.findById(nonExistentId);

        assertTrue(result.isEmpty());
    }
}
