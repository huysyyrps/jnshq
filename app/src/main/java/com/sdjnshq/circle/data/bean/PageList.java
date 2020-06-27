package com.sdjnshq.circle.data.bean;

import java.util.List;

public class PageList <T>{


    /**
     * recoderCount : 5
     * currentPage : 11
     * currentPageRecoderCount : 5
     * currentPageData : []
     */

    private String recoderCount;
    private String currentPage;
    private String currentPageRecoderCount;
    private List<T> currentPageData;

    public String getRecoderCount() {
        return recoderCount;
    }

    public void setRecoderCount(String recoderCount) {
        this.recoderCount = recoderCount;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getCurrentPageRecoderCount() {
        return currentPageRecoderCount;
    }

    public void setCurrentPageRecoderCount(String currentPageRecoderCount) {
        this.currentPageRecoderCount = currentPageRecoderCount;
    }

    public List<T> getCurrentPageData() {
        return currentPageData;
    }

    public void setCurrentPageData(List<T> currentPageData) {
        this.currentPageData = currentPageData;
    }
}
