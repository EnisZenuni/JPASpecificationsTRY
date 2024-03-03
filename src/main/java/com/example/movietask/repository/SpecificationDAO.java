package com.example.movietask.repository;

import com.example.movietask.domain.model.Movie;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpecificationDAO {

    public static Specification<Movie> filterByMovieTitle(String movie) {
        return (root, query, criteriaBuilder) ->
                movie != null ? criteriaBuilder.like(root.get("movieName"), "%" + movie + "%") : null;
    }

    public static Specification<Movie> filterByMovieGenre(String genre) {
        return (root, query, criteriaBuilder) ->
                genre != null ? criteriaBuilder.like(root.get("genre"), "%" + genre + "%") : null;
    }

    public static Specification<Movie> filterMoviesByGenres(List<String> genres) {
        return (root, query, criteriaBuilder) -> {
            if (genres == null || genres.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            Join<Movie, String> genreJoin = root.join("genre", JoinType.LEFT);

            Predicate predicate = genreJoin.in(genres);

            query.distinct(true);

            return predicate;
        };
    }

    public static Specification<Movie> filterMovieByReleaseYear(String releaseYear) {
        return (root, query, criteriaBuilder) ->
                releaseYear != null ? criteriaBuilder.like(root.get("releaseYear"), "%" + releaseYear + "%") : null;
    }

    public static Specification<Movie> filterMoviesInBetweenYears(String from, String to) {
        return (root, query, criteriaBuilder) ->
                (from != null && to != null) ? criteriaBuilder.between(root.get("releaseYear"), from, to) : null;
    }
}
