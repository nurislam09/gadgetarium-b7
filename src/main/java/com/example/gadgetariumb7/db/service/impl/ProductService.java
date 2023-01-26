package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.*;
import com.example.gadgetariumb7.db.enums.Gender;
import com.example.gadgetariumb7.db.repository.*;
import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.request.SubProductRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final SubProductRepository subProductRepository;


    public SimpleResponse addProduct(ProductRequest productRequest) throws IOException {
        Brand brand = brandRepository.findById(productRequest.getBrandId()).get();
        Category category = categoryRepository.findById(productRequest.getCategoryId()).get();
        Subcategory subcategory = subcategoryRepository.findById(productRequest.getCategoryId()).get();

        List<Subproduct> subproducts = new ArrayList<>();
        for (SubProductRequest s : productRequest.getSubProductRequests()) {
            if (categoryRepository.getById(productRequest.getCategoryId()).getCategoryName().equals("Ноутбуки и планшеты")&&
            productRequest.getLaptopCPU()!= null){
                Subproduct subproduct = new Subproduct(s.getLaptopCPU(), s.getColor(), s.getImages());
                subproducts.add(subproduct);
            }else {
                Subproduct subproduct = new Subproduct(s.getMemory(), s.getColor(), s.getImages());
                subproducts.add(subproduct);
            }
        }

        if (category.getCategoryName().equals("Смартфоны")) {
            Product product = new Product(productRequest, brand, category, subcategory, "Смартфоны");
            product.setCreateAt(LocalDateTime.now());
            product.setSubproducts(subproducts);
            subproducts.forEach(s -> s.setProduct(product));
            repository.save(product);
            subProductRepository.saveAll(subproducts);

        } else if (category.getCategoryName().equals("Смарт-часы и браслеты")) {
            Product product = new Product(productRequest, brand, category, subcategory, Gender.MALE);
            product.setCreateAt(LocalDateTime.now());
            product.setSubproducts(subproducts);
            subproducts.forEach(s -> s.setProduct(product));
            repository.save(product);
            subProductRepository.saveAll(subproducts);

        } else if (productRequest.getMemoryOfTablet() != 0) {
            Product product = new Product(productRequest, brand, category, subcategory, 1, 0.0);
            product.setCreateAt(LocalDateTime.now());
            product.setSubproducts(subproducts);
            subproducts.forEach(s -> s. setProduct(product));
            repository.save(product);
            subProductRepository.saveAll(subproducts);

        } else {
            Product product = new Product(productRequest, brand, category, subcategory, (byte) 1);
            product.setCreateAt(LocalDateTime.now());
            product.setSubproducts(subproducts);
            subproducts.forEach(s -> s.setProduct(product));
            repository.save(product);
        }
        return new SimpleResponse("Product successfully saved", "ok");
    }


    @PostConstruct
    private void init() {
        Brand brand = new Brand();
        brand.setBrandName("Apple");
        brand.setImage("Image 1");

        Brand brand1 = new Brand();
        brand1.setBrandName("Samsung");
        brand1.setImage("Image 2");

        Brand brand2 = new Brand();
        brand2.setBrandName("Lenovo");
        brand2.setImage("Image 3");


        Category category = new Category();
        category.setCategoryName("Смартфоны");

        Category category1 = new Category();
        category1.setCategoryName("Смарт-часы и браслеты");

        Category category2 = new Category();
        category2.setCategoryName("Ноутбуки и планшеты");

        Subcategory subcategory = new Subcategory();
        subcategory.setSubCategoryName("Iphone");
        category.setSubcategories(Arrays.asList(subcategory));
        subcategory.setCategory(category);


        Subcategory subcategory1 = new Subcategory();
        subcategory1.setSubCategoryName("AppleWatch");
        category1.setSubcategories(Arrays.asList(subcategory1));
        subcategory1.setCategory(category1);


        Subcategory subcategory2 = new Subcategory();
        subcategory2.setSubCategoryName("Lenovo");
        category2.setSubcategories(Arrays.asList(subcategory2));
        subcategory2.setCategory(category2);

        categoryRepository.save(category);
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        subcategoryRepository.save(subcategory);
        subcategoryRepository.save(subcategory1);
        subcategoryRepository.save(subcategory2);

        brandRepository.save(brand);
        brandRepository.save(brand1);
        brandRepository.save(brand2);
    }


}
