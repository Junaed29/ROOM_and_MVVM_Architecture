package com.example.room_mvvm_architecture;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/********************Used to take operation on Entity********************/
@Dao
public interface NoteDao {
    /********Insert Operation************/
    @Insert
    void insert(Note note);

    /********update Operation************/
    @Update
    void  update(Note note);

    /********delete Operation************/
    @Delete
    void delete(Note note);

    /********deleteAll Operation************/
    @Query("DELETE FROM NOTE_TABLE")
    void deleteAll();

    /********getAllNotes Operation************/
    @Query("SELECT * FROM NOTE_TABLE ORDER BY priority DESC")
    LiveData <List<Note>> getAllNotes();
}
