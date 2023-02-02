package com.example.gadgetariumb7.db.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.List;

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
    private int memory;
    private String laptopCPU;

    private String color;
    @ElementCollection
    @CollectionTable(name = "subproduct_images", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "image_url",length = 10000)
    List<String> images;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = FetchType.EAGER)
    private Product product;
}
