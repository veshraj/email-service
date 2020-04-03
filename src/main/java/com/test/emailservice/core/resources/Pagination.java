package com.test.emailservice.core.resources;

import java.util.Objects;

public class Pagination {
    private final static int pageSize =  20;
    private final static int maxPageSize = 2000;


    public static int getPageNumber(Integer page) {
        if(Objects.nonNull(page) && page > 0) {
            return page - 1;
        }
        return 0;
    }

    public static int getPageSize(Integer requestPageSize) {
        if(Objects.nonNull(requestPageSize) && requestPageSize > 0 && requestPageSize < maxPageSize) {
            return requestPageSize;
        }
        else if(!Objects.nonNull(requestPageSize)) {
            return pageSize;
        }
        return  maxPageSize;
    }

    public  Pagination(){}
}
