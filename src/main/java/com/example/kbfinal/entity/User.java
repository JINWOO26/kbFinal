package com.example.kbfinal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// lombok 사용할것
@Entity
@Data
@Table(name = "KB_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    // 추가로 3개의 attribute 를 만들기

}
