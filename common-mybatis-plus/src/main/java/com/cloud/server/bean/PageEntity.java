package com.cloud.server.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PageEntity<T> implements Serializable {

    public static final int defaultCurrent = 1;
    public static final int defaultSize = 10;

    private static final long serialVersionUID = 1L;

    private Integer totalPage;

    private Integer currentPage;

    private Integer size;

    public PageEntity() {
        this.size=defaultSize;
        this.currentPage=defaultCurrent;
        this.list=new ArrayList<>();
    }

    private Integer totalElements;
    private  List<T> list;
    public PageEntity(com.baomidou.mybatisplus.plugins.Page page) {
        this.currentPage=page.getCurrent();
        this.list=page.getRecords();
        this.size=page.getSize();
        this.totalElements=page.getTotal();
        this.totalPage=page.getPages();
    }

}
