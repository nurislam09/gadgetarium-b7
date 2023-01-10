package com.example.gadgetariumb7.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "subproducts")
@Getter
@Setter
@NoArgsConstructor
public class SubProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subproduct-seq")
    @SequenceGenerator(name = "subproduct_seq", sequenceName = "subproduct_seq", allocationSize = 1)
    private Long id;
    private int price;
    private int memory;
    private String laptopCPU; //(процессор ноутбука)

    private Color color;
    @ElementCollection
    @CollectionTable(name = "subproduct_images", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "image_url")
    List<String> images;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = FetchType.EAGER)
    private Product product;
}
