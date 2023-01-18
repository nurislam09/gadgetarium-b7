package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

import java.io.IOException;

public interface ProductService {
    SimpleResponse addProduct(ProductRequest productRequest) throws IOException;

}
