package com.rent.rentservice.category.allCategory.domain;

import lombok.Data;

import javax.persistence.*;

@Entity @Data @Table(name = "ALLCATEGORY")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;
    private String categoryName;
}
