package com.example.instafollowers.rest;

public class FollowedResponse {

    String[] followed_usernames;

    public String[] getFollowedUsernames() {
        return followed_usernames;
    }

    public void setFollowedUsernames(String[] followed_usernames) {
        this.followed_usernames = followed_usernames;
    }
}
