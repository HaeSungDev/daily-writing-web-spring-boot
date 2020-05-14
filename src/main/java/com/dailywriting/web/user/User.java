package com.dailywriting.web.user;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    public static User join(String username, String password) {
        User user = new User();
        user.username = username;
        user.password = password;
        return user;
    }
}
