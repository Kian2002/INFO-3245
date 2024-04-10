package com.example.recipekeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {

    private static SQLiteDatabase database;

    public RecipeRepository(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public Recipe getRecipeById(long id) {
        String[] projection = {
                BaseColumns._ID,
                RecipeContract.RecipeEntry.COLUMN_NAME_TITLE,
                RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION
        };
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = database.query(
                RecipeContract.RecipeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor != null) {
            cursor.moveToFirst();
            String title = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION));
            Recipe recipe = new Recipe(title, description);
            recipe.setId(id);
            cursor.close();
            return recipe;
        }
        return null;
    }

    public long insertRecipe(Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_TITLE, recipe.getTitle());
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION, recipe.getDescription());
        return database.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, values);
    }

    public int deleteRecipe(long id) {
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        return database.delete(RecipeContract.RecipeEntry.TABLE_NAME, selection, selectionArgs);
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String[] projection = {
                BaseColumns._ID,
                RecipeContract.RecipeEntry.COLUMN_NAME_TITLE,
                RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION
        };
        Cursor cursor = database.query(
                RecipeContract.RecipeEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION));
                Recipe recipe = new Recipe(title, description);
                recipe.setId(id);
                recipes.add(recipe);
            }
            cursor.close();
        }
        return recipes;
    }
}

