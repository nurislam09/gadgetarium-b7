package com.example.gadgetariumb7.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "banners")
@Getter
@Setter
@NoArgsConstructor
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banner_seq")
    @SequenceGenerator(name = "banner_seq", sequenceName = "banner_seq", allocationSize = 1)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "banner_images", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "image_url")
    List<String> images;
}
