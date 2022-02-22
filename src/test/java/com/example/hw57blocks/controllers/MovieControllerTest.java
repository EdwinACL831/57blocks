package com.example.hw57blocks.controllers;

import com.example.hw57blocks.enums.Category;
import com.example.hw57blocks.enums.Visibility;
import com.example.hw57blocks.models.Movie;
import com.example.hw57blocks.models.PaginatedMovie;
import com.example.hw57blocks.models.Pagination;
import com.example.hw57blocks.services.MovieService;
import com.example.hw57blocks.utils.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class MovieControllerTest {
    private final String MOCK_BEARER_TOKEN = "Bearer " + JWTUtil.generateToken(Map.of("email", "dummy-email@something.com"), 60000);

    private MovieController movieController;
    private MovieService movieService;
    private Pagination pagination;

    @BeforeEach
    public void setup() {
        movieService = mock(MovieService.class);
        pagination = mock(Pagination.class);
        movieController = new MovieController(movieService);
    }

    @Test
    public void getPublicMovies_retrievesAPaginatedMovie_1() {
        when(movieService.getAllPublicMovies()).thenReturn(Arrays.asList(
                new Movie("Name 1", 123, 1000,Category.COMEDY, "Director1", Visibility.PUBLIC, "Person1"),
                new Movie("Name 3", 789, 2000,Category.COMEDY, "Director3", Visibility.PRIVATE, "Person3")));

        PaginatedMovie paginatedMovie = movieController.getPublicMovies(MOCK_BEARER_TOKEN, null);

        assertEquals(2, paginatedMovie.getMovies().size());
        assertEquals(1, paginatedMovie.getPage());
        assertEquals(2, paginatedMovie.getTotalElements());
    }

    @Test
    @DisplayName("Should have totalElements = 2, page = 1, List sizes = 1")
    public void getPublicMovies_retrievesPaginatedMovie_2() {
        when(movieService.getAllPublicMovies()).thenReturn(Arrays.asList(
                new Movie("Name 1", 123, 1000,Category.COMEDY, "Director1", Visibility.PUBLIC, "Person1"),
                new Movie("Name 3", 789, 2000,Category.COMEDY, "Director3", Visibility.PRIVATE, "Person3")));
        when(pagination.getPage()).thenReturn(2);
        when(pagination.getSize()).thenReturn(1);

        PaginatedMovie paginatedMovie = movieController.getPublicMovies(MOCK_BEARER_TOKEN, pagination);
        assertEquals(1, paginatedMovie.getMovies().size());
        assertEquals(2, paginatedMovie.getPage());
        assertEquals(2, paginatedMovie.getTotalElements());

        Movie movie = paginatedMovie.getMovies().get(0);
        assertEquals("Name 3", movie.getName());
    }

    @Test
    @DisplayName("Should have totalElements = 2, page = 1, List sizes = 3 when requested page = 0")
    public void getPublicMovies_retrievesPaginatedMovie_3() {
        when(movieService.getAllPublicMovies()).thenReturn(Arrays.asList(
                new Movie("Name 1", 123, 1000,Category.COMEDY, "Director1", Visibility.PUBLIC, "Person1"),
                new Movie("Name 2", 789, 2000,Category.COMEDY, "Director2", Visibility.PRIVATE, "Person2"),
                new Movie("Name 3", 789, 2000,Category.COMEDY, "Director3", Visibility.PRIVATE, "Person3")));
        when(pagination.getPage()).thenReturn(0);
        when(pagination.getSize()).thenReturn(1);

        PaginatedMovie paginatedMovie = movieController.getPublicMovies(MOCK_BEARER_TOKEN, pagination);

        assertEquals(0, paginatedMovie.getMovies().size());
        assertEquals(0, paginatedMovie.getPage());
        assertEquals(3, paginatedMovie.getTotalElements());
    }

    @Test
    @DisplayName("addMovie -> movie added correctly with email retrieved from the jwt")
    public void addMovie() {
        Movie movie = new Movie("Name 1", 123, 1000,Category.COMEDY, "Director1", Visibility.PUBLIC, null);
        assertNull(movie.getAddedBy());

        String response = movieController.addMovie(MOCK_BEARER_TOKEN, movie);

        verify(movieService).insertNewMovie(movie);
        assertEquals(MovieController.MOVIE_ADDED_SUCCESSFULLY, response);
        assertEquals("dummy-email@something.com", movie.getAddedBy());
    }
}
