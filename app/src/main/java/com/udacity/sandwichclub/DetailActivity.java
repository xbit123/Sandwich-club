package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //Fills in TextViews with passed sandwich data
    private void populateUI(Sandwich sandwich) {
        //"also known as" may contain 0 or several strings
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs.size() > 0) {
            TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
            alsoKnownAsTv.setText(alsoKnownAs.get(0));
            for (int i = 1; i < alsoKnownAs.size(); i++) {
                alsoKnownAsTv.append(", " + alsoKnownAs.get(i));
            }
        }

        //"ingredients" may contain 0 or several strings
        List<String> ingredients = sandwich.getIngredients();
        if (ingredients.size() > 0) {
            TextView ingredientsTv = findViewById(R.id.ingredients_tv);
            ingredientsTv.setText(ingredients.get(0));
            for (int i = 1; i < alsoKnownAs.size(); i++) {
                ingredientsTv.append(", " + alsoKnownAs.get(i));
            }
        }

        TextView placeOfOriginTv = findViewById(R.id.origin_tv);
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());

        TextView descriptionTv = findViewById(R.id.description_tv);
        descriptionTv.setText(sandwich.getDescription());
    }
}
