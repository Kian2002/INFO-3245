package com.example.recipekeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        super(context, 0, recipes);
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        }

        Recipe currentRecipe = recipes.get(position);

        TextView titleTextView = listItem.findViewById(R.id.titleTextView);
        titleTextView.setText(currentRecipe.getTitle());
        // white text color
        titleTextView.setTextColor(context.getResources().getColor(R.color.white));

        return listItem;
    }
}
