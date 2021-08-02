package com.example.miniworld.Models;

import android.net.Uri;

public class Users {
    String username;
    String fullname;
    String userid;
    String bio;
    String email;
    String password;
    String profilePhoto;
    String lastmessage;
    String story;
    String Uri;

    public Users(){}

    // Obviously for sign up activity. :D
    public Users(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // for profile activity. :D
    public Users (String fullname, String bio ){
        this.fullname = fullname;
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUri () {return Uri;}

    public void setUri(String Uri ) {
        this.Uri = Uri;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname){
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio){
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Users(String username, String fullname, String userid, String bio, String email,
                 String password, String profilePhoto,
                 String lastmessage, String story, String Uri) {
        this.username = username;
        this.fullname = fullname;
        this.userid = userid;
        this.bio = bio;
        this.email = email;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.lastmessage = lastmessage;
        this.story = story;
        this.Uri = Uri;
    }
}
