package com.rent.rentservice.chatting.domain;

import javax.persistence.*;

import com.rent.rentservice.post.domain.Post;
import com.rent.rentservice.user.domain.User;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Data
public class Chatting {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chattingID;
    @ManyToOne @JoinColumn(name = "sellerUserId")
    private User userSellerID;
    @ManyToOne @JoinColumn(name = "buyerUserId")
    private User userBuyerID;

    @ManyToOne @JoinColumn(name = "postID")
    private Post postID;
    @Column(columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
}
