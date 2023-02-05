package com.example.gadgetariumb7.db.entity;

import javax.persistence.*;

import com.example.gadgetariumb7.dto.request.SubProductRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "subproducts")
@Getter
@Setter
@NoArgsConstructor
public class Subproduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subproduct_gen")
    @SequenceGenerator(name = "subproduct_gen", sequenceName = "subproduct_seq", allocationSize = 1)
    private Long id;
    private int price;
    private int countOfSubproduct;
    private String color;

    @ElementCollection
    @CollectionTable(name = "characteristics_subproduct", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "value")
    private Map<String, String> characteristics = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "subproduct_images", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "image_url", length = 10000)
    List<String> images;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = FetchType.EAGER)
    private Product product;

    public Subproduct(SubProductRequest subProductRequest) {
        this.characteristics = subProductRequest.getCharacteristics();
        this.color = subProductRequest.getColor();
        this.images = subProductRequest.getImages();
        this.price = subProductRequest.getPrice();
        this.countOfSubproduct = subProductRequest.getCountOfProduct();
    }
}

