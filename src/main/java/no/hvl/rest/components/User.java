package no.hvl.rest.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String username; // unique, and used as id
    private String password;
    private String email;

    public User(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("email") String email
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {};

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

