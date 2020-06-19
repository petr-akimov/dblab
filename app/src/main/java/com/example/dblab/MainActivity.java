package com.example.dblab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView _dogsListView;

    public class DogListItem {
        public int id;
        public String name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView empty = findViewById(R.id.empty);
        _dogsListView = findViewById(R.id.dogsListView);
        _dogsListView.setClickable(true);
        DbHelper db = new DbHelper(this);
        SQLiteDatabase con = db.getWritableDatabase();
        Cursor c = con.query(db.TABLE_NAME, null, null, null, null, null, db.KEY_ID, null);
        List<String> dogs = new ArrayList<>();
        if(c.moveToFirst()) {
            int nameIdx = c.getColumnIndex(db.KEY_NAME);
            int idIdx = c.getColumnIndex(db.KEY_ID);
            while (c.moveToNext()) {
                dogs.add(String.format("%s (%d)", c.getString(nameIdx), c.getInt(idIdx))) ;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dogs);
            _dogsListView.setAdapter(adapter);
            _dogsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    Object o = _dogsListView.getItemAtPosition(position);
                    Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                    String txt = (String)(_dogsListView.getItemAtPosition(position));
                    int id = Integer.parseInt(txt.split("\\(")[1].split("\\)")[0]);
                    intent.putExtra("ID", id);
                    startActivity(intent);
                }
            });
        }
        empty.setText(dogs.size() > 0 ? "" : "Empty");

    }

    public void onAddButtonClick(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
}
