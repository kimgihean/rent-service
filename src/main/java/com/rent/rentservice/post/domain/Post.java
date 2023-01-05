package com.rent.rentservice.post.domain;

import com.rent.rentservice.user.domain.User;
import javax.persistence.*;
import java.sql.Date;

@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;
    @ManyToOne
    @JoinColumn(name = "USERID")
    private User userID;
    private String title;
    private Date period;
    private int favorite;

    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
    /*
    @OneToMany
    @JoinColumn(name = "ADDRESSID")
    private String addressID;
    @OneToMany
    @JoinColumn(name = "CATEGORYID")
    private String categoryIO;
     */
    //private String content; // text
    //private int cost; // money type

}
