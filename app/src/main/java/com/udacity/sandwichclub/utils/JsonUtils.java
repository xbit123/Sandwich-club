package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    //parses JSON string with sandwich data. Returns null if an error occurred while parsing.
    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwichJson = new JSONObject(json);
            Sandwich sandwich = new Sandwich();

            JSONObject sandwichName = sandwichJson.getJSONObject("name");
            sandwich.setMainName(sandwichName.getString("mainName"));

            JSONArray alsoKnownAsJson = sandwichName.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJson.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJson.getString(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownAs);

            sandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));

            sandwich.setDescription(sandwichJson.getString("description"));

            sandwich.setImage(sandwichJson.getString("image"));

            JSONArray ingredientsJson = sandwichJson.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJson.length(); i++) {
                ingredients.add(ingredientsJson.getString(i));
            }
            sandwich.setIngredients(ingredients);

            return sandwich;
        } catch (JSONException e) {
            return null;
        }
    }
}
