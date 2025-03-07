package com.example.room_mvvm_architecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/******AndroidViewModel class which activity tp prevent crash when rotating screen******/
/*******for this NoteViewModel extends AndroidViewModel Class******/
public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData <List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }

    public void insertNote(Note note){
        noteRepository.insert(note);
    }

    public void updateNote(Note note){
        noteRepository.update(note);
    }

    public void deleteNote(Note note){
        noteRepository.delete(note);
    }

    public void deleteAllNote(){
        noteRepository.deleteAll();
    }

    public LiveData <List<Note>> getAllNotes(){
        return allNotes;
    }
}
