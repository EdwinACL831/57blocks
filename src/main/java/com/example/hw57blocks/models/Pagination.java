package com.example.hw57blocks.models;

public class Pagination {
    private Integer size;
    private Integer page;

    public Pagination(){}

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getPage() {
        return page;
    }
}
