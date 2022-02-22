package com.example.hw57blocks.entities;

import com.example.hw57blocks.enums.Category;
import com.example.hw57blocks.enums.Visibility;

import javax.persistence.*;

@Entity
@Table(name = "movie")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "release_year")
    private Integer releaseYear;
    @Column(name = "movie_name")
    private String name;
    @Column(name = "budget")
    private Integer budget;
    @Column(name = "category")
    private Category category;
    @Column(name = "director")
    private String director;
    @Column(name = "visibility")
    private Visibility visibility;
    @Column(name = "added_by")
    private String addedBy;

    protected MovieEntity(){}

    public MovieEntity(String name,
                       Integer releaseYear,
                       Integer budget,
                       Category category,
                       String director,
                       Visibility visibility,
                       String addedBy) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.budget = budget;
        this.category = category;
        this.director = director;
        this.visibility = visibility;
        this.addedBy = addedBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Integer getBudget() {
        return budget;
    }

    public Category getCategory() {
        return category;
    }

    public String getDirector() {
        return director;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public String getAddedBy() {
        return addedBy;
    }
}
