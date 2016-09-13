package com.thomasvansevenant.diary.models;

public class Diary {
    private String title;
    private String context;
    private String date;

    public Diary(String context, String date, String title) {
        this.context = context;
        this.date = date;
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }


    @Override
    public String toString() {
        return String.format("title: %s\ncontext: %s\ndate: %s", title, context, date);
    }
}
