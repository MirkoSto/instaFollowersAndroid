package com.example.instafollowers.rest;

public class UsersResponse {

    String[] usernames;

    public String[] getUsernames() {
        return usernames;
    }

    public void setUsernames(String[] followed_usernames) {
        this.usernames = followed_usernames;
    }


}
