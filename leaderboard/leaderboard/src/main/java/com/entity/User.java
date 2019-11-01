package com.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    int id;
    String name;
    String email;
    String phone;
}
