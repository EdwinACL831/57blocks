package com.example.hw57blocks.controllers;

import com.example.hw57blocks.models.Movie;
import com.example.hw57blocks.models.PaginatedMovie;
import com.example.hw57blocks.models.Pagination;
import com.example.hw57blocks.services.MovieService;
import com.example.hw57blocks.utils.JWTUtil;
import com.example.hw57blocks.utils.PaginationUtil;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@DgsComponent
public class MovieController {
    private static final String AUTH_HEADER = "authorization";
    private final MovieService movieService;

    public MovieController(@Autowired MovieService movieService) {
        this.movieService = movieService;
    }

    @DgsQuery
    public PaginatedMovie getPublicMovies(
            @RequestHeader(name = AUTH_HEADER) String bearerToken,
            @InputArgument(name = "pagination") Pagination pagination
    ) throws DgsBadRequestException {
        JWTUtil.validateBearerToken(bearerToken);
        List<Movie> publicMovies = this.movieService.getAllPublicMovies();

        return getPaginatedMovie(pagination, publicMovies);
    }

    @DgsQuery
    public PaginatedMovie getMyPrivateMovies(
            @RequestHeader(name = AUTH_HEADER) String bearerToken,
            @InputArgument(name = "pagination") Pagination pagination
    ) {
        // TODO: add the filter to only retrieve the user's private movies, not all the private movies
        JWTUtil.validateBearerToken(bearerToken);
        List<Movie> userPrivateMovies = this.movieService.getUserPrivateMovies();

        return getPaginatedMovie(pagination, userPrivateMovies);
    }

    @DgsMutation
    public String addMovie(
            @RequestHeader(name = AUTH_HEADER) String bearerToken,
            @InputArgument(name = "movie") Movie movie
    ) {
        JWTUtil.validateBearerToken(bearerToken);
        Claims claims = JWTUtil.getTokenClaims(bearerToken);
        movie.setAddedBy(claims.get(JWTUtil.JWT_EMAIL_KEY, String.class));
        this.movieService.insertNewMovie(movie);

        return "Movie added successfully!";
    }

    private PaginatedMovie getPaginatedMovie(Pagination pagination, List<Movie> movies) {
        PaginatedMovie result;
        if (null == pagination) {
            result = new PaginatedMovie(1, movies.size(), movies);
        } else {
            List<Movie> paginatedMovies = PaginationUtil
                    .paginateList(pagination.getPage(), pagination.getSize(), movies);
            result = new PaginatedMovie(pagination.getPage(), movies.size(), paginatedMovies);
        }

        return  result;
    }
}
