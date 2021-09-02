package com.dristy.WeeklyMealSpring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "user_accounts")
// Specified the table to prevent clash with the User keyword in Postgre SQL
public class User extends Persistent {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordHash) {
        this.password = passwordHash;
    }
}
