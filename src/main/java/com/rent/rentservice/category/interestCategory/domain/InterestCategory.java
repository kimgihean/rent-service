package com.rent.rentservice.category.interestCategory.domain;

import com.rent.rentservice.category.allCategory.domain.Category;
import com.rent.rentservice.user.domain.User;
import lombok.Data;

import javax.persistence.*;

@Entity @Data
public class InterestCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interestCategoryID;
    @JoinColumn(name = "CATEGORY_ID") @ManyToOne
    private Category categoryID;
    @JoinColumn(name = "USER_ID") @ManyToOne
    private User userID;
}
