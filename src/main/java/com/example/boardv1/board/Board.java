package com.example.boardv1.board;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.example.boardv1.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
// @Data
@Getter
@Setter
@Entity
@Table(name = "board_tb")
public class Board { // user 1 : board N
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;

    // private Integer userId;
    // 오브젝트 릴레이션 맵핑
    @ManyToOne(fetch = FetchType.EAGER) // 포링키 어노테이션(Board가 Many, User가 One 이라는 뜻) fetch가 생략되면 디폴트는 EAGER
    private User user;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Override
    public String toString() {
        return "Board [id=" + id + ", title=" + title + ", content=" + content + ", user=" + user + ", createdAt="
                + createdAt + "]";
    }

}
