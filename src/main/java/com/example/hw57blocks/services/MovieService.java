package com.example.hw57blocks.services;

import com.example.hw57blocks.entities.MovieEntity;
import com.example.hw57blocks.enums.Category;
import com.example.hw57blocks.enums.Visibility;
import com.example.hw57blocks.models.Movie;
import com.example.hw57blocks.repositories.MovieRepository;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.dataloader.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private static final String MOVIE_NOT_FOUND = "The movie to update was not found, you can only update private movies you created!!";
    private static final String MOVIE_ALREADY_EXISTS = "Movie already exist, you can not create a new one with the same name";
    private final MovieRepository movieRepository;

    public MovieService(@Autowired MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllPublicMovies() {
        Optional<List<MovieEntity>> result = this.movieRepository.findMoviesByVisibility(Visibility.PUBLIC);
        return getMovieList(result);
    }

    public List<Movie> getUserPrivateMovies(String addedBy) {
        Optional<List<MovieEntity>> result =
                this.movieRepository.findMoviesByVisibilityAndAddedBy(Visibility.PRIVATE, addedBy);
        return getMovieList(result);
    }

    public void updatePrivateUserMovie(Movie movie, String addedBy) throws DgsEntityNotFoundException {
        Optional<List<MovieEntity>> result = this.movieRepository.findMoviesByName(movie.getName());
        List<MovieEntity> movies = result.orElse(new ArrayList<>());

        List<MovieEntity> filteredMovies = movies.stream()
                .filter(m -> m.getAddedBy().equals(addedBy) && m.getVisibility().equals(Visibility.PRIVATE))
                .collect(Collectors.toList());

        if (filteredMovies.isEmpty()) {
            throw new DgsEntityNotFoundException(MOVIE_NOT_FOUND);
        }

        for (MovieEntity movieEntity: filteredMovies) {
            MovieEntity updatedMovieEntity = mergeMovieData(movieEntity, movie);
            this.movieRepository.save(updatedMovieEntity);
        }
    }

    @VisibleForTesting
    protected MovieEntity mergeMovieData(MovieEntity movieEntity, Movie movie) {
        String name = movie.getName() == null ? movieEntity.getName() : movie.getName().trim();
        Integer releaseYear = movie.getReleaseYear() == null ? movieEntity.getReleaseYear() : movie.getReleaseYear();
        Integer budget = movie.getBudget() == null ? movieEntity.getBudget() : movie.getBudget();
        Category category = movie.getCategory() == null ? movieEntity.getCategory() : movie.getCategory();
        String director = movie.getDirector() == null ? movieEntity.getDirector() : movie.getDirector().trim();
        Visibility visibility = movie.getVisibility() == null ? movieEntity.getVisibility() : movie.getVisibility();
        String addedBy = movie.getAddedBy() == null ? movieEntity.getAddedBy() : movie.getAddedBy().trim();

        MovieEntity newMovieEntity = new MovieEntity(name, releaseYear, budget, category, director, visibility, addedBy);
        newMovieEntity.setId(movieEntity.getId());

        return newMovieEntity;
    }

    public void insertNewMovie(Movie movie) throws DgsBadRequestException {
        // Make sure the movie's name does not already exist in DB
        Optional<List<MovieEntity>> result =this.movieRepository.findMoviesByName(movie.getName().trim());
        List<MovieEntity> movieEntities = result.orElse(new ArrayList<>());
        if (!movieEntities.isEmpty()) {
            throw new DgsBadRequestException(MOVIE_ALREADY_EXISTS);
        }

        MovieEntity movieEntity = new MovieEntity(
                movie.getName().trim(),
                movie.getReleaseYear(),
                movie.getBudget(),
                movie.getCategory(),
                movie.getDirector().trim(),
                movie.getVisibility(),
                movie.getAddedBy().trim());

        this.movieRepository.save(movieEntity);
    }

    private List<Movie> getMovieList(Optional<List<MovieEntity>> result) {
        if (null != result) {
            List<MovieEntity> movies = result.orElse(new ArrayList<>());

            return movies.stream().map(movieEntity ->
                    new Movie(movieEntity.getName(),
                            movieEntity.getReleaseYear(),
                            movieEntity.getBudget(),
                            movieEntity.getCategory(),
                            movieEntity.getDirector(),
                            movieEntity.getVisibility(),
                            movieEntity.getAddedBy())).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
