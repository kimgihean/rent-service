package com.rent.rentservice.chatting.domain;

import javax.persistence.*;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.user.domain.User;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Chatting {
    /*
    todo Date created 확인하기, content 부분 자료형 확인하기
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chattingID;
    @ManyToOne @JoinColumn(name = "sellerUserId")
    private User userSellerID;
    @ManyToOne @JoinColumn(name = "buyerUserId")
    private User userBuyerID;

    @ManyToOne @JoinColumn(name = "postID")
    private Post postID;

    private String content;
    private Date created;
}
