package com.rent.rentservice.address.userAddress;

import com.rent.rentservice.user.domain.User;
import lombok.Data;

import javax.persistence.*;

@Entity @Data @Table(name = "USER_ADDRESS")
public class UserAddress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressUserID;
    @JoinColumn(name = "USERID") @ManyToOne
    private User userID;

    //todo (Town 과 Address 어떻게 매핑?) ManyToMany 를 적용하는 방법 OneToMany + ManyToOne 방식 사용 공부하기
    //@JoinColumn(name = "ADDRESS_ID") @ManyToOne
    //private Town addressID;


}
