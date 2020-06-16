package com.jperat.babylist.model;

import org.springframework.data.domain.Page;

import java.util.*;
import java.util.function.Consumer;

public class Pagination implements Iterable {

    private Page page;

    @Override
    public Iterator<Integer> iterator() {
        return getPagination().iterator();
    }

    public Pagination(Page page) {
        this.page = page;
    }

    public long getTotalElements() {
        return page.getTotalElements();
    }

    public int getCurrentPageNumber() {
        return page.getNumber();
    };

    public boolean hasNext() {
        return page.hasNext();
    }

    public int getNextPageNumber() {
        return page.nextOrLastPageable().getPageNumber() + 1;
    }

    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    public int getPreviousPageNumber() {
        return page.getPageable().previousOrFirst().getPageNumber() + 1;
    }

    public ArrayList<Integer> getPagination() {
        int totalPage = page.getTotalPages();
        int currentPage = page.getPageable().getPageNumber() + 1;
        int begin;
        int end;
        if (currentPage < 3) {
            begin = 1;
            end = 5;
        } else if (totalPage - currentPage < 3) {
            begin = totalPage - 5;
            end = totalPage;
        } else {
            begin = currentPage - 2;
            end = currentPage + 2;
        }
        if (begin < 1) {
            begin = 1;
        }
        if (end > totalPage) {
            end = totalPage;
        }
        ArrayList<Integer> pagination = new ArrayList<>();
        for (int i = begin; i <= end; i++) {
            pagination.add(i);
        }
        return pagination;
    }
}
