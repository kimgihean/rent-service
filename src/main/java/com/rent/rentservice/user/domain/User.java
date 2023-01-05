package com.rent.rentservice.user.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "USER")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String email;
    //private String image;
}
