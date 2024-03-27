package com.example.recipekeeper;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    public static SQLiteDatabase getDatabase() {
        return db;
    }
}