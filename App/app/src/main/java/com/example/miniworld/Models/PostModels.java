package com.example.miniworld.Models;

public class PostModels {
    String profilePhoto, username, location, post, caption, id;


    public  PostModels(){

    }

    public PostModels(String profilePhoto, String username, String location, String post, String caption){
        this.profilePhoto = profilePhoto;
        this.username = username;
        this.location = location;
        this.post = post;
        this.caption = caption;
    }

    public PostModels(String profilePhoto, String username, String location, String post, String caption,String id ) {
        this.profilePhoto = profilePhoto;
        this.username = username;
        this.location = location;
        this.post = post;
        this.caption = caption;
        this.id = id;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


}
