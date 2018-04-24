package com.rpll.okeoke.bettingplatform.Model;

public class Report {
    String email, content, category;
    String date;

    public Report(String email, String content, String category, String date) {
        this.email = email;
        this.content = content;
        this.category = category;
        this.date = date;
    }

    public Report() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
