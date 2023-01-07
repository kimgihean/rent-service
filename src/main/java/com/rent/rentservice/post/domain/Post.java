package com.rent.rentservice.post.domain;

import com.rent.rentservice.user.domain.User;
import lombok.Builder;
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

    @Builder
    public Post(Long postID,
                User userID,
                String title,
                Date period,
                int favorite) {
        this.postID = postID;
        this.userID = userID;
        this.title = title;
        this.period = period;
        this.favorite = favorite;
    }
    //todo make category column
}
