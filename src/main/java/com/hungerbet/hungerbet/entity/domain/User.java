package com.hungerbet.hungerbet.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String secondName;

    private double balanceMoney;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Manager manager;

    @JsonIgnore
    private String login;

    public User(String firstName, String secondName, String login, String passwd, double balance) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.login = login;
        this.password = passwd;
        this.balanceMoney = balance;
    }

    public void addMoney(double amount) {
        this.balanceMoney += amount;
    }

    private String password;
}
