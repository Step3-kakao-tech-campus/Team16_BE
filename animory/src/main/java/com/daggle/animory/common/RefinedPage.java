package com.daggle.animory.common;

import lombok.Getter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Min;

@Getter
public abstract class RefinedPage {

    @Min(0)
    protected int pageNumber;
    protected int size;
    protected int totalPages;

    protected RefinedPage(final Page<?> page) {
        this.pageNumber = page.getNumber();
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
    }
}
