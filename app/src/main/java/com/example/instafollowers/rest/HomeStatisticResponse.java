package com.example.instafollowers.rest;

public class HomeStatisticResponse {
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
}
