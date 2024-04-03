package com.example.recipekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipe_db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // We add new tables here
        db.execSQL(RecipeContract.RecipeEntry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RecipeContract.RecipeEntry.DELETE_TABLE);
        onCreate(db);
    }

    public void insertDummyData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Insert dummy recipes
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_TITLE, "Pasta Carbonara");
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION, "Delicious pasta with creamy sauce");
        db.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, values);

        values.clear();

        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_TITLE, "Chicken Curry");
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION, "Spicy and flavorful chicken curry");
        db.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, values);
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RecipeContract.RecipeEntry.TABLE_NAME, null, null);
        db.close();
    }
}

