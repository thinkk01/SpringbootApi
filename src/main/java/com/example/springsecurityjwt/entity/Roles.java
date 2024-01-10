package com.example.springsecurityjwt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="roleId")
    private int roleId;
    @Column(name="roleName")
    @Enumerated
    private ERole roleName;

}
