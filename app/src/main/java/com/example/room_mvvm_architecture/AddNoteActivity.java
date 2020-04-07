package com.example.room_mvvm_architecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_Id = "com.google.android.material.textfield.TextInputLayout.EXTRA_Id";
    public static final String EXTRA_TITLE = "com.google.android.material.textfield.TextInputLayout.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.google.android.material.textfield.TextInputLayout.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.google.android.material.textfield.TextInputLayout.EXTRA_PRIORITY";

    TextInputLayout titleTextInputLayout;
    TextInputLayout descTextInputLayout;
    NumberPicker numberPicker;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleTextInputLayout = findViewById(R.id.titleTextInputLayoutId);
        descTextInputLayout = findViewById(R.id.descTextInputLayoutId);
        numberPicker = findViewById(R.id.numberPickerId);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_Id)){
            actionBar.setTitle("Edit Note");
            titleTextInputLayout.getEditText().setText(intent.getStringExtra(EXTRA_TITLE));
            descTextInputLayout.getEditText().setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }else {
            actionBar.setTitle("Add Note");
        }
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close);

    }

    private void saveNote(){
        String titleString = titleTextInputLayout.getEditText().getText().toString();
        String discString = descTextInputLayout.getEditText().getText().toString();
        int priority = numberPicker.getValue();

        if (titleString.trim().isEmpty()|discString.trim().isEmpty()){
            Toast.makeText(this, "Fill all field", Toast.LENGTH_SHORT).show();
            return;
        }else {
            //Toast.makeText(this, titleString+"  "+discString+" "+priority, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.putExtra(EXTRA_TITLE,titleString);
            intent.putExtra(EXTRA_DESCRIPTION,discString);
            intent.putExtra(EXTRA_PRIORITY,priority);

            int id = getIntent().getIntExtra(EXTRA_Id,-1);

            if (id != -1){
                intent.putExtra(EXTRA_Id,id);
            }

            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.save_note_Id){
            saveNote();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}
