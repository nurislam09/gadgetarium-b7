package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.entity.*;
import com.example.gadgetariumb7.db.repository.*;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final SubproductRepository subproductRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<ProductAdminResponse> productAdminResponses(String productType, String fieldToSort, LocalDate startDate, LocalDate endDate) {
        List<ProductAdminResponse> productAdminResponses = productRepository.getAllProductsAdmin();
        setImageAndDiscount(productAdminResponses);
        sort(fieldToSort, productAdminResponses);
        if (startDate != null && endDate != null) return sortByDates(productAdminResponses, startDate, endDate);

        switch (productType){
            case "Все товары" -> {
                return productAdminResponses;
            }
            case "В продаже" -> {
                return productAdminResponses.stream().filter(x -> x.getProductCount() > 0).toList();
            }
            case "В избранном" -> {
//                return productRepository.getAllProductsAdminFromBasketList();
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
        Role role = new Role();
        role.setRoleName("user");

        Discount discount = new Discount();
        discount.setAmountOfDiscount((byte) 50);
        discount.setDiscountStartDate(LocalDate.now());
        discount.setDiscountEndDate(LocalDate.of(2023, 9, 12));

        Product product = new Product("Iphone", 6088, 12, 12243534, 125, LocalDateTime.of(2023, 1, 19, 11, 12), Arrays.asList("image1", "image2", "image3") );
        Product product1 = new Product("Laptop", 8000, 100, 42435321, 34,LocalDateTime.of(2022, 5, 23, 16, 18), Arrays.asList("image4", "image5", "image6"));
        Product product2 = new Product("Lenevo", 10000, 54, 9824357, 54,LocalDateTime.of(2023, 1, 18, 11, 12), Arrays.asList("image7", "image8", "image9"));
        Product product3 = new Product("Macbook", 3000, 198, 982435247, 344,LocalDateTime.of(2021, 4, 10, 22, 54), Arrays.asList("image10", "image11", "image12"));
        Product product4 = new Product("Apple Watch", 1000, 300, 9245287, 76,LocalDateTime.of(2020, 8, 17, 15, 16), Arrays.asList("image13", "image14", "image15"));


        product.setDiscount(discount);
        product1.setDiscount(discount);
        product2.setDiscount(discount);

        Subproduct subproduct1 = new Subproduct();
        Subproduct subproduct2 = new Subproduct();
        Subproduct subproduct3 = new Subproduct();
        product.setSubproducts(Arrays.asList(subproduct1, subproduct2, subproduct3));
        subproduct1.setProduct(product);
        subproduct2.setProduct(product);
        subproduct3.setProduct(product);


       User user = new User();
       user.setFirstName("Syimyk");
       user.setLastName("Ravshanbekov");
       user.setPassword(passwordEncoder.encode("12345678"));
       user.setEmail("syimyk@gmail.com");
       user.setRole(role);
        role.setUsers(Arrays.asList(user));

        user.setBasketList(Arrays.asList(product, product1));

        discountRepository.save(discount);
        productRepository.save(product);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        subproductRepository.save(subproduct1);
        subproductRepository.save(subproduct2);
        subproductRepository.save(subproduct3);
        roleRepository.save(role);
        userRepository.save(user);
    }


    private void sort(String fieldToSort, List<ProductAdminResponse> productAdminResponses){
        if (fieldToSort != null) {
            switch (fieldToSort) {
                case "Наименование товара" -> productAdminResponses.sort(Comparator.comparingInt(ProductAdminResponse::getProductCount).reversed());
                case "Дата создания" -> productAdminResponses.sort(Comparator.comparing(ProductAdminResponse::getCreateAt).reversed());
                case "Кол-во" -> productAdminResponses.sort(Comparator.comparing(ProductAdminResponse::getCountSubproducts).reversed());
                case "Цена товара" -> productAdminResponses.sort(Comparator.comparing(ProductAdminResponse::getProductPrice).reversed());
                case "Текущая цена" -> productAdminResponses.sort(Comparator.comparing(ProductAdminResponse::getCurrentPrice).reversed());
            }
        }
    }

    private void setImageAndDiscount(List<ProductAdminResponse> productAdminResponses){
        for (ProductAdminResponse p : productAdminResponses) {
            Product product = productRepository.getById(p.getId());
            p.setProductImages(product.getProductImages().get(0));

            if (product.getDiscount() != null) {
                Discount discount = product.getDiscount();
                p.setDiscountPrice(discount.getAmountOfDiscount());
                p.setCurrentPrice((p.getProductPrice() * discount.getAmountOfDiscount()) / 100);
            }
        }
    }

    private List<ProductAdminResponse> sortByDates(List<ProductAdminResponse> productAdminResponses, LocalDate startDate, LocalDate endDate){
        List<ProductAdminResponse> list = new ArrayList<>();
        for (ProductAdminResponse p: productAdminResponses) {
            if (p.getCreateAt().toLocalDate().isAfter(startDate) && p.getCreateAt().toLocalDate().isBefore(endDate)){
                list.add(p);
            }
        }
        return list;
    }
}
