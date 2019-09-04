package com.ahmed.restapiretrofit;

import com.google.gson.annotations.SerializedName;

public class Comment {

    String postId;
    String id;
    String title;
    String name;
    String email;

    @SerializedName("body")
    String text;

    public String getPostId() {
        return postId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }
}
