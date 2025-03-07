package com.example.room_mvvm_architecture;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


/**********All operation in database***********/
/**********This is mainly used to connect local and global database together***********/
public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        /**Create database without this we can't create database**/
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);

        /****Get NoteDao instance using NoteDatabase class for database operation****/
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAll(){
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData <List<Note>> getAllNotes(){
        return allNotes;
    }

    /*****Operation must be background thread****/
    private static class InsertNoteAsyncTask extends AsyncTask <Note,Void,Void>{
        private NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    /*****Operation must be background thread****/
    private static class UpdateNoteAsyncTask extends AsyncTask <Note,Void,Void>{
        private NoteDao noteDao;

        public UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    /*****Operation must be background thread****/
    private static class DeleteNoteAsyncTask extends AsyncTask <Note,Void,Void>{
        private NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    /*****Operation must be background thread****/
    private static class DeleteAllNoteAsyncTask extends AsyncTask <Void,Void,Void>{
        private NoteDao noteDao;

        public DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }


}
