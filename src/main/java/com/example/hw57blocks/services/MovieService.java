package com.example.hw57blocks.services;

import com.example.hw57blocks.entities.MovieEntity;
import com.example.hw57blocks.enums.Visibility;
import com.example.hw57blocks.models.Movie;
import com.example.hw57blocks.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(@Autowired MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllPublicMovies() {
        Optional<List<MovieEntity>> result = this.movieRepository.findMoviesByVisibility(Visibility.PUBLIC);
        return getMovieList(result);
    }

    public List<Movie> getUserPrivateMovies() {
        Optional<List<MovieEntity>> result = this.movieRepository.findMoviesByVisibility(Visibility.PRIVATE);
        return getMovieList(result);
    }

    public void insertNewMovie(Movie movie) {
        MovieEntity movieEntity = new MovieEntity(
                movie.getName(),
                movie.getReleaseYear(),
                movie.getBudget(),
                movie.getCategory(),
                movie.getDirector(),
                movie.getVisibility(),
                movie.getAddedBy());

        this.movieRepository.save(movieEntity);
    }

    private List<Movie> getMovieList(Optional<List<MovieEntity>> result) {
        if (null != result) {
            List<MovieEntity> movies = result.orElse(new ArrayList<>());;

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
