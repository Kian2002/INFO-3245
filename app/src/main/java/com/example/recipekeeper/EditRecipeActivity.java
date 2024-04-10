package com.example.recipekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;


public class EditRecipeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Get the recipe object from the intent
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText descriptionEditText = findViewById(R.id.descriptionEditText);

        titleEditText.setText(recipe.getTitle());
        descriptionEditText.setText(recipe.getDescription());

        findViewById(R.id.saveButton).setOnClickListener(v -> {
            // Save the updated recipe
            String newTitle = titleEditText.getText().toString();
            String newDescription = descriptionEditText.getText().toString();

            dbHelper.updateRecipe(recipe, newTitle, newDescription);

            recipe.setTitle(newTitle);
            recipe.setDescription(newDescription);
            // Return to the RecipeDetailActivity
            Intent intent = new Intent();
            intent.putExtra("recipe", recipe);
            setResult(RESULT_OK, intent);
            finish();
        });

        findViewById(R.id.deleteButton).setOnClickListener(v -> {
            // Delete the recipe
            dbHelper.deleteRecipe(recipe);
            // Return to the RecipeDetailActivity
            Intent intent = new Intent();
            intent.putExtra("recipe", recipe);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
