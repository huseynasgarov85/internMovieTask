package com.example.internmovietask.service;

import com.example.internmovietask.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    Movie createMovie(Movie movie);

    Optional<Movie> updateMovie(Movie updatedMovie, Long movieId);

    void deleteMovie(Long movieId);

    Optional<Movie> getMovieById(Long movieId);

    List<Movie> getAllMovieList();
}
