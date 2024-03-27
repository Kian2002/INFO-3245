package com.example.recipekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class RecipeRepositoryTest {

    private SQLiteDatabase db;
    private RecipeRepository repository;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = new DatabaseHelper(context).getWritableDatabase();
        repository = new RecipeRepository(context);
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testInsertRecipe() {
        Recipe recipe = new Recipe("Pasta Carbonara", "Delicious pasta with creamy sauce");
        long id = repository.insertRecipe(recipe);
        assertNotEquals(-1, id);
    }

    @Test
    public void testGetAllRecipes() {
        List<Recipe> recipes = repository.getAllRecipes();
        assertNotNull(recipes);
        assertEquals(0, recipes.size());
    }

    @Test
    public void testDeleteRecipe() {
        Recipe recipe = new Recipe("Pasta Carbonara", "Delicious pasta with creamy sauce");
        long id = repository.insertRecipe(recipe);

        int rowsDeleted = repository.deleteRecipe(id);
        assertEquals(1, rowsDeleted);
    }
}

