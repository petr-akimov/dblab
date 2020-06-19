package com.example.dblab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    private DbHelper _db;
    private int _currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView infoTextView = findViewById(R.id.infoTextView);
        _currentId = getIntent().getIntExtra("ID", -1);
        _db = new DbHelper(this);

        if(_currentId != -1) {
            SQLiteDatabase con = _db.getWritableDatabase();
            Cursor c = con.query(_db.TABLE_NAME, null, _db.KEY_ID + " = ?", new String[] { String.valueOf(_currentId) }, null, null, null, "1");
            if(c.moveToFirst()) {
                String name = c.getString(c.getColumnIndex(_db.KEY_NAME));
                int age = c.getShort(c.getColumnIndex(_db.KEY_AGE));
                String breed = c.getString(c.getColumnIndex(_db.KEY_BREED));
                String desc = c.getString(c.getColumnIndex(_db.KEY_DESC));
                String info = String.format("Name: %s\nAge: %d\nBreed: %s\nInfo: %s", name, age, breed, desc);
                infoTextView.setText(info);
            }
        }
        else {
            Button delete = findViewById(R.id.deleteButton);
            delete.setEnabled(false);
            infoTextView.setText("Empty");
        }
    }

    public void onBackButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onDeleteButtonClick(View view) {
        SQLiteDatabase con = _db.getWritableDatabase();
        con.delete(_db.TABLE_NAME, _db.KEY_ID + " = ?", new String[] { String.valueOf(_currentId) });
        onBackButtonClick(null);
    }


}