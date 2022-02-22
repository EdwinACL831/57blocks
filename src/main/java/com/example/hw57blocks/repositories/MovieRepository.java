package com.example.hw57blocks.repositories;

import com.example.hw57blocks.entities.MovieEntity;
import com.example.hw57blocks.enums.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    Optional<List<MovieEntity>> findMoviesByVisibility(Visibility visibility);
}
