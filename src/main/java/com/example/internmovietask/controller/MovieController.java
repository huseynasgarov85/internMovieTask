package com.example.internmovietask.controller;

import com.example.internmovietask.model.Movie;
import com.example.internmovietask.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{id}")
    @Operation(summary = "Fetch movie by ID", description = "Retrieve a movie record from the database using its ID.")
    public ResponseEntity<Movie> fetchMovie(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Fetch all movies", description = "Retrieve a complete list of movies available in the database.")
    public ResponseEntity<List<Movie>> fetchAllMovies() {
        List<Movie> movies = movieService.getAllMovieList();
        return movies.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(movies);
    }

    @PostMapping
    @Operation(summary = "Create a new movie", description = "Save a new movie record into the database.")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Optional.ofNullable(movie.getId()).ifPresent(id -> {
            throw new IllegalArgumentException("ID must not be provided when creating a new Movie.");
        });

        Movie savedMovie = movieService.createMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify an existing movie", description = "Update an existing movie record in the database using its ID.")
    public ResponseEntity<Movie> modifyMovie(@PathVariable Long id, @RequestBody Movie movie) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        return movieService.updateMovie(movie, id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove a movie", description = "Delete a movie record from the database using its ID.")
    public ResponseEntity<Void> removeMovie(@PathVariable Long id) {
        if (id == null || id <= 0 || movieService.getMovieById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
