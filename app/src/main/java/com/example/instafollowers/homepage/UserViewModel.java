package com.example.instafollowers.homepage;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.instafollowers.database.userdata.UserRepository;

public class UserViewModel extends ViewModel {
    boolean isInitialized;

    int followers_percentage;
    int followers_via_app;

    String username;
    String followers_number;
    String following_number;

    int max_followed;
    int max_unfollowed;
    int max_liked;
    int max_commented;
    int max_stories;

    int total_followed;
    int total_unfollowed;
    int total_liked;
    int total_commented;
    int total_stories;

    int followed;
    int unfollowed;
    int liked;
    int commented;
    int stories;

    private final SavedStateHandle savedStateHandle;
    private final UserRepository userRepository;

    @ViewModelInject
    public UserViewModel(
            UserRepository userRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.userRepository = userRepository;
        this.savedStateHandle = savedStateHandle;

    }

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

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotal_followed() {
        return total_followed;
    }

    public void setTotal_followed(int total_followed) {
        this.total_followed = total_followed;
    }

    public int getTotal_unfollowed() {
        return total_unfollowed;
    }

    public void setTotal_unfollowed(int total_unfollowed) {
        this.total_unfollowed = total_unfollowed;
    }

    public int getTotal_liked() {
        return total_liked;
    }

    public void setTotal_liked(int total_liked) {
        this.total_liked = total_liked;
    }

    public int getTotal_commented() {
        return total_commented;
    }

    public void setTotal_commented(int total_commented) {
        this.total_commented = total_commented;
    }

    public int getTotal_stories() {
        return stories;
    }

    public void setTotal_stories(int stories) {
        this.stories = stories;
    }

    public int getFollowers_percentage() {
        return followers_percentage;
    }

    public void setFollowers_percentage(int followers_percentage) {
        this.followers_percentage = followers_percentage;
    }

    public int getFollowers_via_app() {
        return followers_via_app;
    }

    public void setFollowers_via_app(int followers_via_app) {
        this.followers_via_app = followers_via_app;
    }
}
