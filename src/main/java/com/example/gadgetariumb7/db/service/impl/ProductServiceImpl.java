package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Brand;
import com.example.gadgetariumb7.db.entity.Category;
import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.entity.Subcategory;
import com.example.gadgetariumb7.db.repository.BrandRepository;
import com.example.gadgetariumb7.db.repository.CategoryRepository;
import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.db.repository.SubcategoryRepository;
import com.example.gadgetariumb7.db.service.ProductService;
import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;


    @Override
    public SimpleResponse addProduct(ProductRequest productRequest) throws IOException {
        Brand brand = brandRepository.findById(productRequest.getBrandId()).get();
        Category category = categoryRepository.findById(productRequest.getCategoryId()).get();
        Subcategory subcategory = subcategoryRepository.findById(productRequest.getCategoryId()).get();

        Product product = new Product(productRequest, brand, category, subcategory);
        repository.save(product);


//        if (productRequest.getCategory().getCategoryName().equals("Phone")) {
//            product = new Product(productRequest, productRequest.getProductName());
//            repository.save(product);
//        } else if (productRequest.getCategory().getCategoryName().equals("SmartWatch")) {
//            product = new Product(productRequest, productRequest.getGender());
//            repository.save(product);
//        } else if (productRequest.getCategory().getCategoryName().equals("Tablet")) {
//            product = new Product(productRequest, productRequest.getScreenDiagonal());
//            repository.save(product);
//        } else if (productRequest.getCategory().getCategoryName().equals("Laptop")) {
//            product = new Product(productRequest, productRequest.getVideoCardMemory());
//            repository.save(product);
//        }
        return new SimpleResponse("Successfully saved", "SAVE");
    }

    @PostConstruct
    private void init(){
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
        category.setCategoryName("Smartphone");

        Category category1 = new Category();
        category1.setCategoryName("SmartWatch");

        Category category2 = new Category();
        category2.setCategoryName("Laptop and Tablets");

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
