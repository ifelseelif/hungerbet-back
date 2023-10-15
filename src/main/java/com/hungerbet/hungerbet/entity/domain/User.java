package com.hungerbet.hungerbet.entity.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String lastName;

    private String email;
    private double balanceMoney;

    private boolean isManager;

    private String login;

    public User(String firstName, String lastName, String login, String passwd, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = passwd;
        this.email = email;
        this.balanceMoney = 0;
    }

    public void addMoney(double amount) {
        this.balanceMoney += amount;
    }

    private String password;
}
