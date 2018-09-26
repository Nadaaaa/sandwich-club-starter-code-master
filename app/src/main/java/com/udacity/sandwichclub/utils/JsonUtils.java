package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichDetails = new JSONObject(json);
            JSONObject sandwichName = sandwichDetails.getJSONObject("name");
            sandwich.setMainName(sandwichName.getString("mainName"));

            JSONArray alsoKnownAsArray = sandwichName.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAsList.add(alsoKnownAsArray.getString(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownAsList);
            sandwich.setPlaceOfOrigin(sandwichDetails.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichDetails.getString("description"));
            sandwich.setImage(sandwichDetails.getString("image"));

            JSONArray ingredientsArray = sandwichDetails.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredientsList.add(ingredientsArray.getString(i));
            }

            sandwich.setIngredients(ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
