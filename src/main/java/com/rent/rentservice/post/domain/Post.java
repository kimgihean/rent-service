package com.rent.rentservice.post.domain;

import com.querydsl.core.annotations.QueryProjection;
import com.rent.rentservice.user.domain.User;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

//todo category

@Entity
@Data
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;
    @ManyToOne
    @JoinColumn(name = "USERID")
    private User userID;
    private String title;
    @CreationTimestamp @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
    private int favorite;
    @Column(columnDefinition = "TEXT")
    private String text;
    private int viewCount;

    protected Post() {}
    @Builder @QueryProjection
    public Post(User userID,
                String title,
                int favorite,
                String text,
                int viewCount) {
        this.userID = userID;
        this.title = title;
        this.favorite = favorite;
        this.text = text;
        this.viewCount = viewCount;
    }
}
