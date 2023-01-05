package com.rent.rentservice.post.domain;

import com.rent.rentservice.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;
    @ManyToOne
    @JoinColumn(name = "USERID")
    private User userID;
    private String title;
    private Date period;
    private int favorite;

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
