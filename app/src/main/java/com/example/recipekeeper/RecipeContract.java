package com.example.recipekeeper;

import android.provider.BaseColumns;

public class RecipeContract {

    private RecipeContract() {}

    public static class RecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_TITLE + " TEXT," +
                        COLUMN_NAME_DESCRIPTION + " TEXT)";

        public static final String DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}

