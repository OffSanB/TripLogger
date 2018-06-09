package com.assignment.android.triplogger;

import java.util.UUID;

public class Settings {

    private UUID id;
    private String name;
    private String email;
    private int gender;
    private String comment;

    public Settings(){this(UUID.randomUUID());}
    public Settings(UUID u){
        id=u;
    }
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
