package com.test.emailservice.core.responses;

import com.test.emailservice.core.resources.PaginationResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



public class PaginationResponse<T> extends BaseResponse {
    private final PaginationResource pagination;
    public T data;

    public PaginationResponse(String message, HttpStatus code, Page data) {
        super(message, code, (T) data.getContent());
        this.data = (T)((Page)data).getContent();
        Pageable pageable = ((Page)data).getPageable();
        Long to = pageable.getOffset()+ pageable.getPageSize();
        pagination = PaginationResource.builder()
                             .currentPage(pageable.getPageNumber())
                             .pageSize(pageable.getPageSize())
                             .from(pageable.getOffset()+1L)
                             .to((to > data.getTotalElements()) ? data.getTotalElements() : to)
                             .totalItems(data.getTotalElements())
                             .totalPages(data.getTotalPages())
                             .build();

    }

    public PaginationResponse<T> transform(Class className) {
        try {
            Object object = Class.forName(className.getName()).getDeclaredConstructor().newInstance();
            Method method = object.getClass().getDeclaredMethod("map", List.class);
            this.data = (T) method.invoke(object, this.data);

        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return this;
    }

    public PaginationResource getPagination() {
        return pagination;
    }


    public ResponseEntity<PaginationResponse<T>> getResponse() {
        return ResponseEntity.status(code).headers(httpHeaders).body(this);
    }
}
