package com.example.miniworld.Models;

public class StoryModel {
   String username, profilePhoto, Story , id;

    public StoryModel(String username, String profilePhoto, String story, String id) {
        this.username = username;
        this.profilePhoto = profilePhoto;
        this.Story = story;
        this.id = id;
    }

    public StoryModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getStory() {
        return Story;
    }

    public void setStory(String story) {
        Story = story;
    }
}
