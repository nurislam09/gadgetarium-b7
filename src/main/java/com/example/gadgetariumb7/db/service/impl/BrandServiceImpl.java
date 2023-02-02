package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Brand;
import com.example.gadgetariumb7.db.repository.BrandRepository;
import com.example.gadgetariumb7.db.service.BrandService;
import com.example.gadgetariumb7.dto.BrandDTO;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public SimpleResponse addBrand(BrandDTO brandRequest) {
        Brand brand = new Brand();
        brand.setBrandName(brandRequest.getBrandName());
        brand.setImage(brandRequest.getImage());
        brandRepository.save(brand);
        return new SimpleResponse("Brand successfully saved!", "ok");
    }

    @Override
    public List<BrandDTO> getAllBrand() {
        return brandRepository.findAll().stream().map(brand -> new BrandDTO(brand.getImage(), brand.getBrandName())).collect(Collectors.toList());
    }

}
