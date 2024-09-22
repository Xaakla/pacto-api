package com.app.pactoapi.commons;

import java.io.Serializable;
import java.util.List;

public class ResultPageDto<T> implements Serializable {
    private long totalResults;
    private int totalPages;
    private int currentPage;
    private List<T> result;

    public ResultPageDto(long totalResults, int totalPages, int currentPage, List<T> result) {
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.result = result;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}