package com.example.recipekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

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

    public long insertRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_TITLE, recipe.getTitle());
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION, recipe.getDescription());

        // Insert the new row, and return the primary key of the new row
        long newRowId = db.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }

    public void insertDummyData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // only insert dummy data if the table is empty
        String count = "SELECT count(*) FROM " + RecipeContract.RecipeEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int rowCount = cursor.getInt(0);
        cursor.close();
        if (rowCount > 0) {
            return;
        }

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

    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = RecipeContract.RecipeEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { recipe.getTitle() };
        db.delete(RecipeContract.RecipeEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    public void updateRecipe(Recipe recipe, String newTitle, String newDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_TITLE, newTitle);
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION, newDescription);
        String selection = RecipeContract.RecipeEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { recipe.getTitle() };
        db.update(RecipeContract.RecipeEntry.TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }
}

