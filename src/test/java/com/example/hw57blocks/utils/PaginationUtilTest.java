package com.example.hw57blocks.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaginationUtilTest {

    @Test
    @DisplayName("paginateList -> returns the first page of the filtered list")
    public void paginateList() {
        List<String> strings = Arrays.asList("a", "b", "c", "d", "e");
        List<String> filteredStrings = PaginationUtil.paginateList(1, 3, strings);

        assertEquals(Arrays.asList("a", "b", "c"), filteredStrings);
    }

    @Test
    @DisplayName("paginateList -> returns the second page of the filtered list")
    public void paginateList_page2() {
        List<String> strings = Arrays.asList("a", "b", "c", "d", "e");
        List<String> filteredStrings = PaginationUtil.paginateList(2, 3, strings);

        assertEquals(Arrays.asList("d", "e"), filteredStrings);
    }

    @Test
    @DisplayName("paginateList -> returns empty list since the page does not exist")
    public void paginateList_page3() {
        List<String> strings = Arrays.asList("a", "b", "c", "d", "e");
        List<String> filteredStrings = PaginationUtil.paginateList(3, 3, strings);

        assertEquals(Collections.emptyList(), filteredStrings);
    }

    @Test
    @DisplayName("paginateList -> pageSize is 1 should return the element in the ")
    public void paginateList_pageSize1() {
        List<String> strings = Arrays.asList("a", "b", "c", "d", "e");
        List<String> filteredStrings = PaginationUtil.paginateList(3, 1, strings);

        assertEquals(Arrays.asList("c"), filteredStrings);
    }


    @Test
    @DisplayName("paginateList -> should return the last page's elements ")
    public void paginateList_pageSize3() {
        List<String> strings = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
        List<String> filteredStrings = PaginationUtil.paginateList(4, 3, strings);

        assertEquals(Arrays.asList("j"), filteredStrings);
    }
}
