package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.BrandDTO;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

import java.util.List;

public interface BrandService {

    SimpleResponse addBrand(BrandDTO brandRequest);

    List<BrandDTO> getAllBrand();

}
