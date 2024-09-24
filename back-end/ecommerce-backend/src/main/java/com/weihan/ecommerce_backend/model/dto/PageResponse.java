package com.weihan.ecommerce_backend.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {

    private Integer page;
    private Integer pageSize;
    private Long total;
    private Integer totalPages;
    private List<T> data;

}
