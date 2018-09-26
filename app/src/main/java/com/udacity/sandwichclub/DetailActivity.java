package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView originTv, alsoKnownTv, descriptionTv, ingredientsTv;
    LinearLayout alsoKnownLayout;
    LinearLayout originLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

    private void populateUI(Sandwich sandwich) {
        if (!sandwich.getPlaceOfOrigin().equals("")) {
            originTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            originLayout.setVisibility(View.GONE);
        }

        String description= "                  "+sandwich.getDescription();
        descriptionTv.setText(description);

        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            String alsoKnownAs = sandwich.getAlsoKnownAs().get(0);
            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                alsoKnownAs+=(", " + sandwich.getAlsoKnownAs().get(i));
            }
            alsoKnownTv.setText(alsoKnownAs);
        } else {
            alsoKnownLayout.setVisibility(View.GONE);
        }

        String ingredients = "- " + sandwich.getIngredients().get(0);
        for (String s : sandwich.getIngredients()) {
            ingredients += "\n- " + s;
        }
        ingredientsTv.setText(ingredients);
    }

    private void init() {
        originTv = findViewById(R.id.origin_tv);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        alsoKnownLayout = findViewById(R.id.also_known_layout);
        originLayout = findViewById(R.id.origin_layout);
    }
}
