package com.cloud.server.base;

import lombok.Data;

@Data
public class PageObj {
    private Integer size;
    private Integer current;

    public PageObj(Integer current, Integer size) {
        this.size = size;
        this.current = current;
    }

    public PageObj() {
    }
}
