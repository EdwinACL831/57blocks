package com.example.hw57blocks.models;

import com.example.hw57blocks.enums.Category;
import com.example.hw57blocks.enums.Visibility;

public class Movie {
    private String name;
    private Integer releaseYear;
    private Integer budget;
    private Category category;
    private String director;
    private Visibility visibility;
    private String addedBy;

    /**
     * Necessary for DGS so the schema parse can be done
     */
    public Movie(){}

    public Movie(String name,
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}
