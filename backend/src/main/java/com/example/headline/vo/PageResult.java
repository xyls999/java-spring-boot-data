package com.example.headline.vo;

import java.util.List;

public class PageResult<T> {
    private long total;
    private long page;
    private long size;
    private List<T> records;

    public PageResult(long total, long page, long size, List<T> records) {
        this.total = total;
        this.page = page;
        this.size = size;
        this.records = records;
    }

    public long getTotal() { return total; }
    public long getPage() { return page; }
    public long getSize() { return size; }
    public List<T> getRecords() { return records; }
}
