package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.*;
import com.example.gadgetariumb7.db.repository.*;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<ProductAdminResponse> productAdminResponses(String productType, String fieldToSort, LocalDate startDate, LocalDate endDate) {
        List<ProductAdminResponse> productAdminResponses = sorting(fieldToSort, productRepository.getAllProductsAdmin(), startDate, endDate);

        switch (productType){
            case "Все товары" -> {
                return productAdminResponses;
            }
            case "В продаже" -> {
                return productAdminResponses.stream().filter(x -> x.getProductCount() > 0).toList();
            }
            case "В избранном" -> {
                List<Product> productList = new ArrayList<>();
                List<ProductAdminResponse> responseList = new ArrayList<>();
//                List<User> users = ;
//                for (User u: users) {
//                    if (u.getBasketList() != null){
//                        for (Product p: u.getBasketList()) {
//                            if (!productList.contains(p)){
//                                productList.add(p);
//                            }
//                        }
//                    }
//                }
                userRepository.findAll().stream().filter(u -> u.getBasketList() != null).forEach(x -> x.getBasketList().stream().filter(p -> !productList.contains(p)).forEach(productList::add));
                productList.forEach(p -> responseList.add(new ProductAdminResponse(p.getId(), p.getProductVendorCode(), p.getProductName(), p.getProductCount(), p.getSubproducts().size(), p.getCreateAt(), p.getProductPrice())));
                return sorting(fieldToSort, responseList, startDate, endDate);
            }
        }

        return productAdminResponses;
    }

    public void deleteById(Long id){
        Product product = productRepository.getById(id);
        product.setDiscount(null);
        productRepository.delete(product);
    }

    public void update(Long id, Integer vendorCode, Integer productCount, Integer productPrice){
        Product product = productRepository.findById(id).get();
        if (vendorCode != null) product.setProductVendorCode(vendorCode);
        if (productCount != null) product.setProductCount(productCount);
        if (productPrice != null) product.setProductPrice(productPrice);

        productRepository.save(product);
    }

    public List<ProductAdminResponse> search(String text){
        return productRepository.search(text);
    }


    @PostConstruct
    public void initProduct() {
//        Discount discount = new Discount();
//        discount.setAmountOfDiscount((byte) 50);
//        discount.setDiscountStartDate(LocalDate.now());
//        discount.setDiscountEndDate(LocalDate.of(2023, 9, 12));
//
//        Product product = new Product("Iphone", 6088, 12, 12243534, 125, LocalDateTime.of(2023, 1, 19, 11, 12), Arrays.asList("image1", "image2", "image3") );
//        Product product1 = new Product("Laptop", 8000, 100, 42435321, 34,LocalDateTime.of(2022, 5, 23, 16, 18), Arrays.asList("image4", "image5", "image6"));
//        Product product2 = new Product("Lenevo", 10000, 54, 9824357, 54,LocalDateTime.of(2023, 1, 18, 11, 12), Arrays.asList("image7", "image8", "image9"));
//        Product product3 = new Product("Macbook", 3000, 198, 982435247, 344,LocalDateTime.of(2021, 4, 10, 22, 54), Arrays.asList("image10", "image11", "image12"));
//        Product product4 = new Product("Apple Watch", 1000, 300, 9245287, 76,LocalDateTime.of(2020, 8, 17, 15, 16), Arrays.asList("image13", "image14", "image15"));
//
//
//        product.setDiscount(discount);
//        product1.setDiscount(discount);
//        product2.setDiscount(discount);
//
//        Subproduct subproduct1 = new Subproduct();
//        Subproduct subproduct2 = new Subproduct();
//        Subproduct subproduct3 = new Subproduct();
//        product.setSubproducts(Arrays.asList(subproduct1, subproduct2, subproduct3));
//        subproduct1.setProduct(product);
//        subproduct2.setProduct(product);
//        subproduct3.setProduct(product);
//
//
//        User user1 = userRepository.findById(2L).get();
//        Product product = productRepository.getById(1L);
//        Product product1 = productRepository.getById(4L);
//        Product product2 = productRepository.getById(2L);
//        User user2 = userRepository.findById(4L).get();
//
//        user1.setBasketList(Arrays.asList(product, product1));
//        user2.setBasketList(Arrays.asList(product, product1, product2));
//
//        discountRepository.save(discount);
//        productRepository.save(product);
//        productRepository.save(product1);
//        productRepository.save(product2);
//        productRepository.save(product3);
//        productRepository.save(product4);
//        subproductRepository.save(subproduct1);
//        subproductRepository.save(subproduct2);
//        subproductRepository.save(subproduct3);
//        userRepository.save(user1);
//        userRepository.save(user2);
    }


    private List<ProductAdminResponse> sorting(String fieldToSort, List<ProductAdminResponse> products, LocalDate startDate, LocalDate endDate){
        if (fieldToSort != null) {
            switch (fieldToSort) {
                case "Наименование товара" -> products.sort(Comparator.comparingInt(ProductAdminResponse::getProductCount).reversed());
                case "Дата создания" -> products.sort(Comparator.comparing(ProductAdminResponse::getCreateAt).reversed());
                case "Кол-во" -> products.sort(Comparator.comparing(ProductAdminResponse::getCountSubproducts).reversed());
                case "Цена товара" -> products.sort(Comparator.comparing(ProductAdminResponse::getProductPrice).reversed());
                case "Текущая цена" -> products.sort(Comparator.comparing(ProductAdminResponse::getCurrentPrice).reversed());
            }
        }

        for (ProductAdminResponse p : products) {
            Product product = productRepository.getById(p.getId());
            p.setProductImages(product.getProductImages().get(0));

            if (product.getDiscount() != null) {
                Discount discount = product.getDiscount();
                p.setDiscountPrice(discount.getAmountOfDiscount());
                p.setCurrentPrice((p.getProductPrice() * discount.getAmountOfDiscount()) / 100);
            }
        }

        if (startDate != null && endDate != null) return products.stream().filter(p -> p.getCreateAt().toLocalDate().isAfter(startDate) && p.getCreateAt().toLocalDate().isBefore(endDate)).toList();

        return products;
    }
}
