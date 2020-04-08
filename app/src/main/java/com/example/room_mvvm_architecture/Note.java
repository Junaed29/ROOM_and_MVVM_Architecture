package com.example.room_mvvm_architecture;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/***********************This class Represent a table and its Column************************/
@Entity(tableName = "note_table")
public class Note {

    /**********Every field is a column************/
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
