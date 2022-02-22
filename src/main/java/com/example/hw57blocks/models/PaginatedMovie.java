package com.example.hw57blocks.models;

import java.util.List;

public class PaginatedMovie {
    private final Integer page;
    private final Integer totalElements;
    private final List<Movie> movies;

    public PaginatedMovie(Integer page, Integer totalElements, List<Movie> movies) {
        this.page = page;
        this.totalElements = totalElements;
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getTotalElements() {
        return totalElements;
    }
}
