package com.dailywriting.web.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Constraint;

@Entity
@Getter
@NoArgsConstructor
@Table(
    name = "user",
    uniqueConstraints = @UniqueConstraint(name = "uk_user_username", columnNames = {
            "username"
    })
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Version
    private long version;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
