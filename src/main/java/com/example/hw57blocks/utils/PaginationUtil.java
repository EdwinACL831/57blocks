package com.example.hw57blocks.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaginationUtil {

    public static <T> List<T> paginateList(Integer page, Integer pageSize, List<T> list) {
        double elementsPerPage = pageSize.doubleValue();
        double numberOfPages = Math.ceil(list.size()/elementsPerPage);

        if (page > numberOfPages || page <= 0) {
            return Collections.emptyList();
        }

        List<T> filteredList = new ArrayList<>();
        try {
            for(int i = 0; i < elementsPerPage; i++) {
                int index = pageSize * (page - 1) + i;
                filteredList.add(list.get(index));
            }

            return filteredList;
        } catch (IndexOutOfBoundsException e) {
            return filteredList;
        }
    }
}
