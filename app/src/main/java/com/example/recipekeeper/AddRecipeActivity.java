package com.example.recipekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Initialize UI elements
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        saveButton = findViewById(R.id.saveButton);

        // Set click listener for save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input
                String title = titleEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();
                String imageUrl = "https://via.placeholder.com/150";

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
                    Toast.makeText(AddRecipeActivity.this, "Please enter title and description", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a new Recipe object
                Recipe recipe = new Recipe(title, description, imageUrl);

                // Pass the recipe back to the MainActivity
                Intent intent = new Intent();
                intent.putExtra("recipe", recipe);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
