package com.example.dblab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private EditText _nameEditText;
    private EditText _breedEditText;
    private EditText _ageEditText;
    private EditText _descEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        _nameEditText = findViewById(R.id.nameEditText);
        _breedEditText = findViewById(R.id.breedEditText);
        _ageEditText = findViewById(R.id.ageEditText);
        _descEditText = findViewById(R.id.descEditText);
    }

    public void onBackButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onAddButtonClick(View view) {
        String name = _nameEditText.getText().toString();
        String breed = _breedEditText.getText().toString();
        String age = _ageEditText.getText().toString();
        String desc = _descEditText.getText().toString();

        if(name.isEmpty() || breed.isEmpty() || age.isEmpty() || desc.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Необходимо заполнить все поля перед добавлением.", Toast.LENGTH_SHORT).show();
            return;
        }

        DbHelper db = new DbHelper(this);
        SQLiteDatabase con = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(db.KEY_NAME,  name);
        values.put(db.KEY_BREED,  breed);
        values.put(db.KEY_AGE,  Short.parseShort(age));
        values.put(db.KEY_DESC,  desc);
        if (con.insert(db.TABLE_NAME, null, values) != -1) {
            Toast.makeText(getApplicationContext(), "Запись добавлена", Toast.LENGTH_SHORT).show();
            onBackButtonClick(null);
        }
        else {
            Toast.makeText(getApplicationContext(), "При добавлении записи произошла ошибка", Toast.LENGTH_SHORT).show();
        }
    }
}