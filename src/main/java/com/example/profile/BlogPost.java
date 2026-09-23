package com.example.profile;

public class BlogPost {
    private String id;
    private String title;
    private String summary;
    private String content;
    private String date;

    public BlogPost(String id, String title, String summary, String content, String date) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.date = date;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public String getContent() { return content; }
    public String getDate() { return date; }
}