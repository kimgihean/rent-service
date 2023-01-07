package com.rent.rentservice.address.town;

import lombok.Data;

import javax.persistence.*;

@Entity @Data
public class Town {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressID;
    private String addressName;
    private String latitude;
    private String longtitude;
}
