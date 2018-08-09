package com.example.sage.foodsharebook.apiFoodShareBookServices;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.sage.foodsharebook.adapters.DishesListAdapter;
import com.example.sage.foodsharebook.adapters.IngredientsListAdapter;
import com.example.sage.foodsharebook.adapters.MeasuresListAdapter;
import com.example.sage.foodsharebook.models.Dish;
import com.example.sage.foodsharebook.models.DishIngredient;
import com.example.sage.foodsharebook.models.DishIngredientResponse;
import com.example.sage.foodsharebook.models.DishResponse;
import com.example.sage.foodsharebook.models.Ingredient;
import com.example.sage.foodsharebook.models.IngredientMeasure;
import com.example.sage.foodsharebook.models.IngredientMeasureResponse;
import com.example.sage.foodsharebook.models.IngredientResponse;
import com.example.sage.foodsharebook.models.LoginResponse;
import com.example.sage.foodsharebook.models.Measure;
import com.example.sage.foodsharebook.models.UserLogin;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marisa on 20/12/2017.
 */

public class ApiRetrofit {
    final static String TAG = "ApiRetrofit";
    public Retrofit retrofit;
    final FoodShareBookService service;
    SharedPreferences prefs;


    public ApiRetrofit(Context context){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://foodsharebook.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(FoodShareBookService.class);
        prefs = context.getSharedPreferences("MyPrefs",0);
    }

    //------------------------------- Users -------------------------------

    public void logIn(String email, String password, final ServiceCallBack serviceCallBack){
        Call<LoginResponse> call = service.uerLogin(new UserLogin(email, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, response.body().getAuthToken());
                    serviceCallBack.response(true, response.body().getAuthToken());

                }
                else{
                    Log.i(TAG, response.toString());
                    serviceCallBack.response(false,"");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                serviceCallBack.response(false,"");
            }
        });
    }

    public interface ServiceCallBack {
        void response(Boolean bool, String token);
    }
    //------------------------------- Dishes -------------------------------

    public void getDishes(final DishesListAdapter dishesListAdapter){
        Call<ArrayList<DishResponse>> dishesArraylistResponse = service.getAllDishes();

        dishesArraylistResponse.enqueue(new Callback<ArrayList<DishResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<DishResponse>> call, Response<ArrayList<DishResponse>> response) {
                if (response.isSuccessful()){
                    ArrayList<DishResponse> dishes = response.body();
                    dishesListAdapter.addDishesList(dishes);
                }
                else{
                    Log.e(TAG,response.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DishResponse>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }

    public void postDish(String name, String description, String recipe){
        Call<DishResponse> call = service.newDish(prefs.getString("token",null),new Dish(name, recipe, description));
        call.enqueue(new Callback<DishResponse>() {
            @Override
            public void onResponse(Call<DishResponse> call, Response<DishResponse> response) {
                if(response.isSuccessful()){
                    Log.i(TAG,"Everything fine");
                }
                else{
                    Log.i(TAG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<DishResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    //------------------------------- Ingredients -------------------------------

    public void getIngredients(final IngredientsListAdapter adapter){
        Call<ArrayList<IngredientResponse>> ingredientsArraylistResponse = service.getAllIngredients(prefs.getString("token",null));

        ingredientsArraylistResponse.enqueue(new Callback<ArrayList<IngredientResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<IngredientResponse>> call, Response<ArrayList<IngredientResponse>> response) {
                if (response.isSuccessful()){
                    ArrayList<IngredientResponse> ingredients = response.body();
                    adapter.addIngredientsList(ingredients);
                    Log.i(TAG, "Objetivo cumplido");
                }
                else {
                    Log.e(TAG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<IngredientResponse>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void getIngredientByID(int ingredientId, final IngredientsListAdapter adapter){
        Call<IngredientResponse> call = service.getIngredient(prefs.getString("token",null),ingredientId);
        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if(response.isSuccessful()){
                    adapter.addIngredientItem(response.body());

                }
                else{
                    Log.i(TAG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    public void postIngredient(String name, String description, String url, final IngredientCallBack ingredientCallBack) {
        Call<IngredientResponse> call = service.newIngredient(prefs.getString("token",null),new Ingredient(name, description, url));
        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if(response.code() == 409){
                    Log.i(TAG, response.message().toString());
                    ingredientCallBack.response(false, null);
                }
                else if(response.isSuccessful()){
                    IngredientResponse ingredient = response.body();
                    ingredientCallBack.response(true, ingredient);
                }

                else{
                    Log.i(TAG, response.toString());
                    ingredientCallBack.response(false, null);
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                    ingredientCallBack.response(false, null);
                    Log.i(TAG, t.getMessage());
            }
        });
    }

    public void putIngredient(int ingredient_id, Ingredient ingredient, final IngredientCallBack ingredientCallBack){
        Call<IngredientResponse> call = service.updateIngredient(prefs.getString("token", null),ingredient_id, ingredient);
        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                Log.i(TAG, response.body().toString());
                ingredientCallBack.response(true, response.body());
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                ingredientCallBack.response(false,null);
            }
        });
    }

    public interface IngredientCallBack{
        void response(Boolean bool, IngredientResponse ingredient);
    }

    //------------------------------- Dish Ingredients -------------------------------

    public void postDishIngredient(int dishId, int ingredientId){
        Call<DishIngredientResponse> call = service.newDishIngredient(prefs.getString("token",null), new DishIngredient(dishId, ingredientId));
        call.enqueue(new Callback<DishIngredientResponse>() {
            @Override
            public void onResponse(Call<DishIngredientResponse> call, Response<DishIngredientResponse> response) {
                if(response.isSuccessful()){
                    Log.i(TAG,response.body().toString());
                }
                else{
                    Log.i(TAG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<DishIngredientResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    //------------------------------- Ingredient Measure -------------------------------

    public void postIngredientMeasure(int ingredientId, int measureId, final IngredientMeasureCallBack ingredientMeasureCallBack){
        Call<IngredientMeasureResponse> call = service.newIngredientMeasure(prefs.getString("token",null), new IngredientMeasure(ingredientId, measureId));
        call.enqueue(new Callback<IngredientMeasureResponse>() {
            @Override
            public void onResponse(Call<IngredientMeasureResponse> call, Response<IngredientMeasureResponse> response) {
                if(response.isSuccessful()){
                    Log.i(TAG,response.body().toString());
                    ingredientMeasureCallBack.response(true, response.body());
                }
                else{
                    Log.i(TAG, response.toString());
                    ingredientMeasureCallBack.response(false, null);
                }
            }

            @Override
            public void onFailure(Call<IngredientMeasureResponse> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                ingredientMeasureCallBack.response(false, null);
            }
        });
    }

    public interface IngredientMeasureCallBack{
        void response(Boolean bool, IngredientMeasureResponse ingredientMeasure);
    }

    //------------------------------- Measures -------------------------------

    public void getMeasures(final MeasuresListAdapter measuresListAdapter){
        Call<ArrayList<Measure>> measuresArraylistResponse = service.getAllMeasures();

        measuresArraylistResponse.enqueue(new Callback<ArrayList<Measure>>() {
            @Override
            public void onResponse(Call<ArrayList<Measure>> call, Response<ArrayList<Measure>> response) {
                if (response.isSuccessful()){
                    ArrayList<Measure> measures = response.body();
                    measuresListAdapter.addMeasuresList(measures);
                }
                else{
                    Log.e(TAG,response.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Measure>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }
}
