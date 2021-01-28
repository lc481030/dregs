package com.dregs.project.system.project.domain;

import java.util.List;

public class ExcelPage<T> {
    /**
     * 页面信息
     */
    private String sheetName; // 每个sheet名字
    private String currentPage; // 当前页
    private String totalPage; // 总页

    /**
     * 页面遍历的数据 List 的泛型自行设置，如果所有数据都来着同一个类就写那个类，
     * 不是同一个类有继承就写继承类的泛型，没有就写问号。
     */
    private List<T> data;


    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }
}
