package com.dailywriting.web.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Table(
    name = "user",
    uniqueConstraints = @UniqueConstraint(name = "uk_user_username", columnNames = {
            "username"
    })
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Version
    private long version;

    public void changePassword(String password) {
        this.password = password;
    }
}
