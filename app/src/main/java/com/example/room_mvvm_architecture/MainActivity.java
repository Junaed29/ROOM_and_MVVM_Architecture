package com.example.room_mvvm_architecture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;


    NoteViewModel noteViewModel ;

    RecyclerView recyclerView;

    //NoteRecyclerViewAdapter noteRecyclerViewAdapter;
    NoteListAdapter noteListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        /*****************Using General RecyclerView Adapter***************/
        /*
        noteRecyclerViewAdapter = new NoteRecyclerViewAdapter();
        recyclerView.setAdapter(noteRecyclerViewAdapter);
        */

        /*****************Using List Adapter***************/
        noteListAdapter = new NoteListAdapter();
        recyclerView.setAdapter(noteListAdapter);


        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //Update RecyclerView

                /*****************Using General RecyclerView Adapter***************/
                //noteRecyclerViewAdapter.setNotes(notes);

                /*****************Using List Adapter***************/
                noteListAdapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = noteListAdapter.getNoteAt(position);
                noteViewModel.deleteNote(note);
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        /*****************Using General RecyclerView Adapter***************/
        /*
        noteRecyclerViewAdapter.setOnItemClickListener(new NoteRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this,AddNoteActivity.class);
                intent.putExtra(AddNoteActivity.EXTRA_Id,note.getId());
                intent.putExtra(AddNoteActivity.EXTRA_PRIORITY,note.getPriority());
                intent.putExtra(AddNoteActivity.EXTRA_TITLE,note.getTitle());
                intent.putExtra(AddNoteActivity.EXTRA_DESCRIPTION,note.getDescription());

                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });
        */


        /*****************Using List Adapter***************/
        noteListAdapter.setOnItemClickListener(new NoteListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this,AddNoteActivity.class);
                intent.putExtra(AddNoteActivity.EXTRA_Id,note.getId());
                intent.putExtra(AddNoteActivity.EXTRA_PRIORITY,note.getPriority());
                intent.putExtra(AddNoteActivity.EXTRA_TITLE,note.getTitle());
                intent.putExtra(AddNoteActivity.EXTRA_DESCRIPTION,note.getDescription());

                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });
    }

    public void fabClick(View view) {
        Intent intent = new Intent(MainActivity.this,AddNoteActivity.class);
        startActivityForResult(intent,ADD_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST  && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1);

            Note note = new Note(title,description,priority);
            noteViewModel.insertNote(note);

            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        }else if (requestCode == EDIT_NOTE_REQUEST  && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddNoteActivity.EXTRA_Id,-1);
            
            if (id == -1){
                Toast.makeText(this, "Note Can't Be Updated", Toast.LENGTH_SHORT).show();
            }else {
                String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
                String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
                int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1);

                Note note = new Note(title,description,priority);
                note.setId(id);
                noteViewModel.updateNote(note);
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.delete_all_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAllNoteMenuItemId){
            noteViewModel.deleteAllNote();
            Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}
