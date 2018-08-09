package com.example.sage.foodsharebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sage.foodsharebook.Dishes.FoodListActivity;
import com.example.sage.foodsharebook.Tests.CreateIngredient;
import com.example.sage.foodsharebook.Tests.MenuActivity;
import com.example.sage.foodsharebook.Users.LoginActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPrefs", 0);
        String token = prefs.getString("token", null);
        Intent intent;

        if (token == null || token.equals(null) || token.equals(""))
            intent = new Intent(this, LoginActivity.class);
        else
            intent = new Intent(this, MenuActivity.class);
            //intent = new Intent(this, FoodListActivity.class);

        startActivity(intent);
        finish();
    }
}
