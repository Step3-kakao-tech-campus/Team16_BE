package com.daggle.animory.common;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public abstract class RefinedPage {

    protected int pageNumber;
    protected int size;
    protected int totalPages;

    protected RefinedPage(final Page<?> page) {
        this.pageNumber = page.getNumber() + 1; // UI 에서 1부터 시작하는 페이지에 대응하기 위함.
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
    }

    protected RefinedPage() {
    }
}
