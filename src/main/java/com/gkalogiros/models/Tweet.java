package com.gkalogiros.models;

import java.util.Date;

/**
 * This is a model class representing a Tweet Message.
 */
public class Tweet {

    private String username;
    private Date date;
    private String content;

    public Tweet(String username, Date date, String content) {
        this.username = username;
        this.date = date;
        this.content = content;
    }

    public Boolean isValid()
    {
        if (null == username) return false;
        if (null == content) return false;
        if (null == date) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "username='" + username + '\'' +
                ", date=" + date +
                ", content='" + content + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
