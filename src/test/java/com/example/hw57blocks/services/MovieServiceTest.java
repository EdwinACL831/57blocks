package com.example.hw57blocks.services;

import com.example.hw57blocks.enums.Visibility;
import com.example.hw57blocks.models.Movie;
import com.example.hw57blocks.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovieServiceTest {
    private MovieService movieService;
    private MovieRepository movieRepository;

    @BeforeEach
    public void setup() {
        movieRepository = mock(MovieRepository.class);
        movieService = new MovieService(movieRepository);
    }

    @Test
    @DisplayName("getAllPublicMovies -> returns empty list when not found any public movie in DB")
    public void getAllPublicMovies() {
        when(movieRepository.findMoviesByVisibility(Visibility.PUBLIC)).thenReturn(Optional.empty());

        List<Movie> movies = movieService.getAllPublicMovies();

        assertTrue(movies.isEmpty());
    }

    @Test
    @DisplayName("getAllPublicMovies -> returns empty list when the repository returns null")
    public void getAllPublicMovies_null() {
        when(movieRepository.findMoviesByVisibility(Visibility.PUBLIC)).thenReturn(null);

        List<Movie> movies = movieService.getAllPublicMovies();

        assertTrue(movies.isEmpty());
    }

    @Test
    @DisplayName("getUserPrivateMovies -> returns empty list when not found any public movie in DB")
    public void getUserPrivateMovies() {
        when(movieRepository.findMoviesByVisibility(Visibility.PRIVATE)).thenReturn(Optional.empty());

        List<Movie> movies = movieService.getUserPrivateMovies();

        assertTrue(movies.isEmpty());
    }

    @Test
    @DisplayName("getUserPrivateMovies -> returns empty list when the repository returns null")
    public void getUserPrivateMovies_null() {
        when(movieRepository.findMoviesByVisibility(Visibility.PRIVATE)).thenReturn(null);

        List<Movie> movies = movieService.getUserPrivateMovies();

        assertTrue(movies.isEmpty());
    }
}
