package com.example.recipekeeper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridView recipeGridView;
    private RecipeRepository recipeRepository;
    private List<Recipe> recipes;
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase db;

    private ActivityResultLauncher<Intent> addRecipeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Recipe recipe = (Recipe) result.getData().getSerializableExtra("recipe");
                    // Insert the recipe into the database and handle the result
                    handleAddRecipeResult(recipe);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        dbHelper.onUpgrade(db, 1, 1);
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

        // Search button
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recipes = recipeRepository.searchRecipes(query);
                RecipeAdapter adapter = new RecipeAdapter(MainActivity.this, recipes);
                recipeGridView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recipes = recipeRepository.searchRecipes(newText);
                RecipeAdapter adapter = new RecipeAdapter(MainActivity.this, recipes);
                recipeGridView.setAdapter(adapter);
                return false;
            }
        });

        // Button to add new recipe
        Button addRecipeButton = findViewById(R.id.addRecipeButton);
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open activity to add new recipe
                addRecipeLauncher.launch(new Intent(MainActivity.this, AddRecipeActivity.class));
            }
        });
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setTextColor(Color.WHITE);
        textView.setHintTextColor(Color.WHITE);
    }

    public static SQLiteDatabase getDatabase() {
        return db;
    }

    private void handleAddRecipeResult(Recipe recipe) {
        // Insert the recipe into the database
        long newRowId = dbHelper.insertRecipe(recipe);

        if (newRowId != -1) {
            Toast.makeText(this, "Recipe saved successfully", Toast.LENGTH_SHORT).show();
            // Refresh the recipe list or update UI as needed
        } else {
            Toast.makeText(this, "Error saving recipe", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchRecipes() {
        // Retrieve recipes from the database
        recipes = recipeRepository.getAllRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Fetch recipes again when the activity is resumed
        fetchRecipes();
        // Update the adapter with the new data
        RecipeAdapter adapter = new RecipeAdapter(this, recipes);
        recipeGridView.setAdapter(adapter);
    }


}
