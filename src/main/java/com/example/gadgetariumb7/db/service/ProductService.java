package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
import com.example.gadgetariumb7.dto.request.InforgraphicsRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

public interface ProductService {

    AllProductResponse getAllProductToMP();

    SimpleResponse addProduct(ProductRequest productRequest);

    InforgraphicsRequest inforgraphics();

}
