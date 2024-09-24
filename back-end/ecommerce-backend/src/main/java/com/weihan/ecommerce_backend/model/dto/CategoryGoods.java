package com.weihan.ecommerce_backend.model.dto;

import com.weihan.ecommerce_backend.model.entity.Category;
import com.weihan.ecommerce_backend.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryGoods {

    private Category subCategory;
    private List<Product> goods;

}
