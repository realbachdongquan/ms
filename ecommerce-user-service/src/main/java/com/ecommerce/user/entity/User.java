package com.ecommerce.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends IdBasedEntity implements Serializable {

    @Column(nullable = false)
    private String username;

    @Column (nullable = false)
    private String password;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private String surname;

    @Column (nullable = false)
    private String phoneNumber;

    @Column (nullable = false)
    private String email;

    @Column (nullable = false)
    private String role;

    @Column
    private LocalDateTime createdAt;

}
