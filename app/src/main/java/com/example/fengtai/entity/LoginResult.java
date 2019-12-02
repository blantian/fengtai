package com.example.fengtai.entity;


public class LoginResult {
    /**
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NzI0MTk0MDIsImV4cCI6MTU3MjUwNTgwMiwidXNlcl9pZCI6IjI5NDgwIiwidXNlcm5hbWUiOiJ0ZXN0In0.nha16AuSm2rRnp7mFTApeBkbT55s-T19OXhdLxXyvYA
     * user_id : 29480
     * username : BreedDoc
     */

    private String token;
    private String user_id;
    private String username;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
