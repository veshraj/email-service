package com.test.emailservice.core.resources;

import lombok.Builder;

@Builder
public class PaginationResource {
    private int currentPage;

    private int lastPage;

    private int pageSize;

    private long from;

    private long to;

    private long totalItems;

    private int totalPages;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public PaginationResource(){}

    public PaginationResource(int currentPage, int lastPage, int pageSize, long from, long to, long totalItems, int totalPages){
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.pageSize = pageSize;
        this.from = from;
        this.to = to;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }


}
