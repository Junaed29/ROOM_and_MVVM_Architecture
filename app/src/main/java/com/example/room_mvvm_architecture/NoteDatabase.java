package com.example.room_mvvm_architecture;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/********************Used to Create Database ********************/
/******************** 'entities = {Note.class, x.class, y.class}' used for which entity classes is used ********************/

/******************** 'version = 1' used for control version every edit in 'database class' need to update version********************/
@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static volatile NoteDatabase instance = null;

    /*********Used to call DML operation in NoteDao class*********/
    public abstract NoteDao noteDao();

    private static final String TAG = "NoteDatabase";


    /**************Singleton class to create onetime instance*************/

    public static NoteDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (NoteDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                            /*******"fallbackToDestructiveMigration()" is used when we update our version it will destroy old entities*******/
                            .fallbackToDestructiveMigration()
                            /*******"addCallback(callback) is optional"*******/
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        /**********Call every time when database created************/
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            Log.d(TAG, "onCreate: ");
            super.onCreate(db);
            /**********Used to create pre insertion to the table*******/
            new populatedAsyncTask(instance).execute();

        }

        /**********Call every time when we open app************/
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            Log.d(TAG, "onOpen: ");
            super.onOpen(db);
        }
    };


    /**********database operation must be in background thread************/
    private static class populatedAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        public populatedAsyncTask(NoteDatabase database) {
            noteDao = database.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Description 1", 1));
            noteDao.insert(new Note("Title 2", "Description 2", 2));
            noteDao.insert(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }

}
