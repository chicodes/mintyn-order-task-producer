package com.chinaka.task.order.producer.mintyn.service;

import com.chinaka.task.order.producer.mintyn.dto.ProductDto;
import com.chinaka.task.order.producer.mintyn.dto.GenericResponse;

public interface ProductService {

    GenericResponse addProduct(ProductDto request);

    GenericResponse getProduct(String id);

    GenericResponse listProduct(int pageNo, int pageSize);

    GenericResponse updateProduct(String id, ProductDto request);
}
