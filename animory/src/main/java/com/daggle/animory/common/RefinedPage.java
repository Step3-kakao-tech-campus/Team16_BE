package com.daggle.animory.common;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public abstract class RefinedPage {
    protected int pageNumber;
    protected int size;
    protected int totalPages;
    protected boolean hasPrevious;
    protected boolean hasNext;

    protected RefinedPage(final Page<?> slice) {
        this.pageNumber = slice.getNumber();
        this.size = slice.getSize();
        this.totalPages = slice.getTotalPages();
        this.hasPrevious = slice.hasPrevious();
        this.hasNext = slice.hasNext();
    }
}
