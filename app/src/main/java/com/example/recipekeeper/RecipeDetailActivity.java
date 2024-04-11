package com.example.recipekeeper;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import com.squareup.picasso.Picasso;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Get the recipe object from the intent
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        ImageView imageView = findViewById(R.id.recipeImageView);

        titleTextView.setText(recipe.getTitle());
        descriptionTextView.setText(recipe.getDescription());
        Picasso.get().load(recipe.getImageUrl()).into(imageView);

        findViewById(R.id.editButton).setOnClickListener(v -> {
            // Create an intent to open the EditRecipeActivity
            Intent intent = new Intent(RecipeDetailActivity.this, EditRecipeActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        });

        findViewById(R.id.backButton).setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get the recipe object from the intent
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);

        titleTextView.setText(recipe.getTitle());
        descriptionTextView.setText(recipe.getDescription());
    }
}

