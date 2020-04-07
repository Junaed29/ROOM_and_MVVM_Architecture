package com.example.room_mvvm_architecture;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static volatile NoteDatabase instance = null;

    public abstract NoteDao noteDao();

    private static final String TAG = "NoteDatabase";
    

    public static NoteDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (NoteDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder
                            (context.getApplicationContext(),
                                    NoteDatabase.class,
                                    "note_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            Log.d(TAG, "onCreate: " );
            super.onCreate(db);
            new populatedAsyncTask(instance).execute();
            
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            Log.d(TAG, "onOpen: ");
            super.onOpen(db);
        }
    };

    private static class populatedAsyncTask extends AsyncTask <Void,Void,Void>{
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
