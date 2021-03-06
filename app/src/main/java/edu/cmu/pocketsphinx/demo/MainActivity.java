package edu.cmu.pocketsphinx.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        readRecipeData();

        //Get Global Controller Class Object
        final Controller aController = (Controller) getApplicationContext();

        for(Recipe r : aController.getRecipes()){
            Log.v("MainActivity", "Recipe Name: " + r.getRecipeName());
        }
    }

    private void readRecipeData() {
        InputStream is = getResources().openRawResource(R.raw.recipe_database);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        //Get Global Controller Class Object
        final Controller aController = (Controller) getApplicationContext();

        String line = "";
        int numIngredientsFields = 13;
        int numStepsFields = 10;
        try {
            while ((line = reader.readLine()) != null) {
                //Split by comma
                String[] fields = line.split("@");
                System.out.println(fields.length);
                Recipe recipe = new Recipe(fields[0], fields[1]);
                for(int i = 0; i < numIngredientsFields; i++) {
                    if(fields[i+2].length() == 1 && fields[i+2].contains(" ")){
                        break;
                    }
                    else {
                        recipe.addIngredient(fields[i+2]);
                    }
                }
                for(int i = 0; i < numStepsFields; i++){
                    if(fields[i+15].length() == 1 && fields[i+15].contains(" ")){
                        break;
                    }
                    else {
                        recipe.addStep(fields[i+15]);
                    }
                }
                aController.addRecipe(recipe);
            }
        }
        catch (IOException e) {
            Log.wtf("MainActivity", "Error reading data on line: " + line);
        }
    }

    public void openIngredientsPageChocolateChipCookies(View v) {
        //Get Global Controller Class Object
        final Controller aController = (Controller) getApplicationContext();
        aController.setRecipeInformation(0);

        Intent intent = new Intent(this, StepActivity.class);
        startActivity(intent);
    }

    public void openIngredientsPageChocolateCake(View v) {
        //Get Global Controller Class Object
        final Controller aController = (Controller) getApplicationContext();
        aController.setRecipeInformation(1);

        Intent intent = new Intent(this, StepActivity.class);
        startActivity(intent);
    }

}