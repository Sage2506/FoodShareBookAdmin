package com.example.sage.foodsharebook.Tests;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sage.foodsharebook.Dishes.FoodListActivity;
import com.example.sage.foodsharebook.Ingredients.IngredientListActivity;
import com.example.sage.foodsharebook.R;
import com.example.sage.foodsharebook.Splash;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    Button createIngredient, viewIngredients, viewDishes, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        createIngredient = findViewById(R.id.btn_createIngredient);
        viewIngredients = findViewById(R.id.btn_viewIngredients);
        viewDishes = findViewById(R.id.btn_viewDishes);
        logout = findViewById(R.id.btn_logout);

        createIngredient.setOnClickListener(this);
        viewIngredients.setOnClickListener(this);
        viewDishes.setOnClickListener(this);
        logout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent option;
        switch (Integer.parseInt(v.getTag().toString())){
            case 1 : option = new Intent(getApplicationContext(), CreateIngredient.class);
                startActivity(option);
                break;
            case 2 : option = new Intent(getApplicationContext(), IngredientListActivity.class);
                startActivity(option);
                break;
            case 3 : option = new Intent(getApplicationContext(), FoodListActivity.class);
                startActivity(option);
                break;
            case 4 : logOut();
                option = new Intent(getApplicationContext(), Splash.class);
                startActivity(option);
                break;
        }

    }

    public void logOut(){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPrefs",0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token","");
        editor.commit();
        finish();
    }
}
