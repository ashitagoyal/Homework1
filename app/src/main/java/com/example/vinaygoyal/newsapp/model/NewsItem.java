package com.example.vinaygoyal.newsapp.model;

public class NewsItem {

    private String author;
    private String title;
    private String description;
    private String url;
    private String date;




    public NewsItem(String author, String title, String description, String url, String date) {

        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.date = date;

    }



    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String tittle) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
