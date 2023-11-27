package com.example.productdemo.entity;

import com.example.productdemo.model.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends AbstractEntity {
    @Column(name = "login")
    private String login;

    @Column(name = "hash")
    private String hash;

    @Column(name = "role")
    private UserRole role;

    @Column(name = "salt")
    private String salt;
}
