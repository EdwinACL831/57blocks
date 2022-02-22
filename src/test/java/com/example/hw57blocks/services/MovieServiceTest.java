package com.example.hw57blocks.services;

import com.example.hw57blocks.entities.MovieEntity;
import com.example.hw57blocks.enums.Category;
import com.example.hw57blocks.enums.Visibility;
import com.example.hw57blocks.models.Movie;
import com.example.hw57blocks.repositories.MovieRepository;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
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
        when(movieRepository.findMoviesByVisibilityAndAddedBy(Visibility.PRIVATE, "addedBy"))
                .thenReturn(Optional.empty());

        List<Movie> movies = movieService.getUserPrivateMovies("addedBy");

        assertTrue(movies.isEmpty());
    }

    @Test
    @DisplayName("getUserPrivateMovies -> returns empty list when the repository returns null")
    public void getUserPrivateMovies_null() {
        when(movieRepository.findMoviesByVisibilityAndAddedBy(Visibility.PRIVATE, "addedBy"))
                .thenReturn(null);

        List<Movie> movies = movieService.getUserPrivateMovies("addedBy");

        assertTrue(movies.isEmpty());
    }

    @Test
    @DisplayName("updateUserMovie -> throws DgsEntityNotFoundException since addedBy do not match")
    public void updateUserMovie_throwsDgsEntityNotFoundException() {
        MovieEntity movieEntity = mock(MovieEntity.class);
        Movie movie = mock(Movie.class);
        when(movie.getName()).thenReturn("randomName");
        when(movieEntity.getAddedBy()).thenReturn("notMe");
        when(movieRepository.findMoviesByName("randomName")).thenReturn(Optional.of(List.of(movieEntity)));

        assertThrows(DgsEntityNotFoundException.class,() -> movieService.updatePrivateUserMovie(movie, "me"));
    }

    @Test
    @DisplayName("updateUserMovie -> throws DgsEntityNotFoundException since visibility is not Private")
    public void updateUserMovie_throwsDgsEntityNotFoundException_2() {
        MovieEntity movieEntity = mock(MovieEntity.class);
        Movie movie = mock(Movie.class);
        when(movie.getName()).thenReturn("randomName");
        when(movieEntity.getAddedBy()).thenReturn("me");
        when(movieEntity.getVisibility()).thenReturn(Visibility.PUBLIC);
        when(movieRepository.findMoviesByName("randomName")).thenReturn(Optional.of(List.of(movieEntity)));

        assertThrows(DgsEntityNotFoundException.class,() -> movieService.updatePrivateUserMovie(movie, "me"));
    }

    @Test
    @DisplayName("updateUserMovie -> movie updates successfully")
    public void updateUserMovie() {
        String movieName = "MovieName";
        String addedBy = "me";

        MovieEntity movieEntity = mock(MovieEntity.class);
        Movie movie = mock(Movie.class);

        when(movie.getName()).thenReturn(movieName);

        when(movieEntity.getAddedBy()).thenReturn(addedBy);
        when(movieEntity.getName()).thenReturn(movieName);
        when(movieEntity.getVisibility()).thenReturn(Visibility.PRIVATE);
        when(movieRepository.findMoviesByName(movieName)).thenReturn(Optional.of(List.of(movieEntity)));

        movieService.updatePrivateUserMovie(movie, addedBy);

        verify(movieRepository).save(any(MovieEntity.class));
    }

    @Test
    public void mergeMovieData() {
        MovieEntity movieEntity = new MovieEntity("Name 1", 432, 1000, Category.DOCUMENTARY, "Director1", Visibility.PRIVATE, "someGuy");
        Movie movie = new Movie("Name 1", 123, null, Category.COMEDY, "Director1", Visibility.PRIVATE, "anotherGuy");

        MovieEntity entity = movieService.mergeMovieData(movieEntity, movie);

        assertEquals("Name 1", entity.getName());
        assertEquals(123, entity.getReleaseYear());
        assertEquals(1000, entity.getBudget());
        assertEquals(Category.COMEDY, entity.getCategory());
        assertEquals("Director1", entity.getDirector());
        assertEquals(Visibility.PRIVATE, entity.getVisibility());
        assertEquals("anotherGuy", entity.getAddedBy());
    }
}
