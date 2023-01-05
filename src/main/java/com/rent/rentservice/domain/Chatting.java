package com.rent.rentservice.domain;

import javax.persistence.*;
import com.rent.rentservice.domain.User;

import java.sql.Date;

@Entity
public class Chatting {
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

    public Long getChattingID() {
        return chattingID;
    }

    public void setChattingID(Long chattingID) {
        this.chattingID = chattingID;
    }

    public User getUserSellerID() {
        return userSellerID;
    }

    public void setUserSellerID(User userSellerID) {
        this.userSellerID = userSellerID;
    }

    public User getUserBuyerID() {
        return userBuyerID;
    }

    public void setUserBuyerID(User userBuyerID) {
        this.userBuyerID = userBuyerID;
    }

    public Post getPostID() {
        return postID;
    }

    public void setPostID(Post postID) {
        this.postID = postID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
