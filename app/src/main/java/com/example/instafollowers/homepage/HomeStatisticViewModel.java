package com.example.instafollowers.homepage;

import androidx.lifecycle.ViewModel;

public class HomeStatisticViewModel extends ViewModel {
    boolean isInitialized;

    String username;
    String followers_number;
    String following_number;

    int max_followed;
    int max_unfollowed;
    int max_liked;
    int max_commented;
    int max_stories;

    int followed;
    int unfollowed;
    int liked;
    int commented;
    int stories;

    public String getUsername() {
        return username;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    public String getFollowers_number() {
        return followers_number;
    }

    public void setFollowers_number(String followers_number) {
        this.followers_number = followers_number;
    }

    public String getFollowing_number() {
        return following_number;
    }

    public void setFollowing_number(String following_number) {
        this.following_number = following_number;
    }

    public int getMax_followed() {
        return max_followed;
    }

    public void setMax_followed(int max_followed) {
        this.max_followed = max_followed;
    }

    public int getMax_unfollowed() {
        return max_unfollowed;
    }

    public void setMax_unfollowed(int max_unfollowed) {
        this.max_unfollowed = max_unfollowed;
    }

    public int getMax_liked() {
        return max_liked;
    }

    public void setMax_liked(int max_liked) {
        this.max_liked = max_liked;
    }

    public int getMax_commented() {
        return max_commented;
    }

    public void setMax_commented(int max_commented) {
        this.max_commented = max_commented;
    }

    public int getMax_stories() {
        return max_stories;
    }

    public void setMax_stories(int max_stories) {
        this.max_stories = max_stories;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }

    public int getUnfollowed() {
        return unfollowed;
    }

    public void setUnfollowed(int unfollowed) {
        this.unfollowed = unfollowed;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getCommented() {
        return commented;
    }

    public void setCommented(int commented) {
        this.commented = commented;
    }

    public int getStories() {
        return stories;
    }

    public void setStories(int stories) {
        this.stories = stories;
    }
}
