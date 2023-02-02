package com.example.gadgetariumb7.db.entity;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banner_gen")
    @SequenceGenerator(name = "banner_gen", sequenceName = "banner_seq", allocationSize = 1, initialValue = 2)
    private Long id;

    @Column(length = 10000)
    String image;
}
