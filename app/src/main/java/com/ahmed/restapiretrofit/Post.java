package com.ahmed.restapiretrofit;

import com.google.gson.annotations.SerializedName;

public class Post {

    String userId;
    String id;
    String title;

    @SerializedName("body")
    String topic;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) { this.userId = userId; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getTopic() {
        return topic;
    }
}
