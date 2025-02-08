package com.example.internmovietask.service;

import com.example.internmovietask.model.Movie;
import com.example.internmovietask.repo.MovieRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {
    private final MovieRepo movieRepository;

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Optional<Movie> updateMovie(Movie updatedMovie, Long movieId) {
        return movieRepository.findById(movieId).map(movie -> {
            movie.setTitle(updatedMovie.getTitle());
            movie.setAuthor(updatedMovie.getAuthor());
            movie.setProductionYear(updatedMovie.getProductionYear());
            movie.setGenre(updatedMovie.getGenre());
            movie.setImdb(updatedMovie.getImdb());
            return movieRepository.save(movie);
        });
    }

    @Override
    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }

    @Override
    public Optional<Movie> getMovieById(Long movieId) {
        return movieRepository.findById(movieId);
    }

    @Override
    public List<Movie> getAllMovieList() {
        return movieRepository.findAll();
    }
}
