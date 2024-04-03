package com.example.recipekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView recipeGridView;
    private RecipeRepository recipeRepository;
    private List<Recipe> recipes;
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        dbHelper.deleteAllRecords();
        dbHelper.insertDummyData();

        // Initialize repository
        recipeRepository = new RecipeRepository(this);

        // Retrieve recipes from the database
        recipes = recipeRepository.getAllRecipes();

        // Setup the grid view
        recipeGridView = findViewById(R.id.recipeGridView);
        RecipeAdapter adapter = new RecipeAdapter(this, recipes);
        recipeGridView.setAdapter(adapter);

        // Handle item clicks
        recipeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle click on recipe item
                Recipe selectedRecipe = recipes.get(position);
                Toast.makeText(MainActivity.this, "Clicked: " + selectedRecipe.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, RecipeDetailActivity.class);
                intent.putExtra("recipe", selectedRecipe);
                startActivity(intent);
            }
        });

        // Button to add new recipe
        Button addRecipeButton = findViewById(R.id.addRecipeButton);
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open activity to add new recipe
                startActivity(new Intent(MainActivity.this, AddRecipeActivity.class));
            }
        });
    }

    public static SQLiteDatabase getDatabase() {
        return db;
    }
}
