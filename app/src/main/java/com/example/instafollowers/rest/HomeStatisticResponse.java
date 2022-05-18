package com.example.instafollowers.rest;

public class HomeStatisticResponse {
    int followers_percentage;
    int followers_via_app;

    String username;
    String followers_number;
    String following_number;

    int followed;
    int unfollowed;
    int liked;
    int commented;
    int stories;

    int max_followed;
    int max_unfollowed;
    int max_liked;
    int max_stories;
    int max_commented;

    int total_followed;
    int total_unfollowed;
    int total_liked;
    int total_commented;
    int total_stories;

    public String getUsername() {
        return username;
    }

    public String getFollowers_number() {
        return followers_number;
    }

    public String getFollowing_number() {
        return following_number;
    }

    public int getFollowed() {
        return followed;
    }

    public int getUnfollowed() {
        return unfollowed;
    }

    public int getLiked() {
        return liked;
    }

    public int getCommented() {
        return commented;
    }

    public int getStories() {
        return stories;
    }

    public int getMax_followed() {
        return max_followed;
    }

    public int getMax_unfollowed() {
        return max_unfollowed;
    }

    public int getMax_liked() {
        return max_liked;
    }

    public int getMax_stories() {
        return max_stories;
    }

    public int getMax_commented() {
        return max_commented;
    }

    public int getTotal_followed() {
        return total_followed;
    }

    public int getTotal_unfollowed() {
        return total_unfollowed;
    }

    public int getTotal_liked() {
        return total_liked;
    }

    public int getTotal_commented() {
        return total_commented;
    }

    public int getTotal_stories() {
        return total_stories;
    }

    public int getFollowers_percentage() {
        return followers_percentage;
    }

    public int getFollowers_via_app() {
        return followers_via_app;
    }
}
