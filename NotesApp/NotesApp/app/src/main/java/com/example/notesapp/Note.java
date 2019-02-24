package com.example.notesapp;

/**
 * Created by mshehab on 4/27/18.
 */

public class Note {
    public long id;
    public String subject;
    public String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
