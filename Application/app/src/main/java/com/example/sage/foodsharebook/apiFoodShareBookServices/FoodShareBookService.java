package com.example.sage.foodsharebook.apiFoodShareBookServices;

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
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Marisa on 20/12/2017.
 */

public interface FoodShareBookService {
    //------------------------------- Users -------------------------------
    @POST("users/login")
    Call<LoginResponse> uerLogin(@Body UserLogin body);

    //------------------------------- Dishes -------------------------------
    @GET("dishes")
    Call<ArrayList<DishResponse>> getAllDishes();

    @POST("dishes")
    Call<DishResponse> newDish(@Header("Authorization") String token, @Body Dish body);

    //------------------------------- Ingredients -------------------------------
    @GET("ingredients")
    Call<ArrayList<IngredientResponse>> getAllIngredients(@Header("Authorization") String token);

    @POST("ingredients")
    Call<IngredientResponse> newIngredient(@Header("Authorization") String token, @Body Ingredient body);

    @GET("ingredients/{INGREDIENT_ID}")
    Call<IngredientResponse> getIngredient(@Header("Authorization") String token,@Path("INGREDIENT_ID") int ingredient_id);

    @PUT("ingredients/{INGREDIENT_ID}")
    Call<IngredientResponse> updateIngredient(@Header("Authorization") String token,@Path("INGREDIENT_ID") int ingredient_id,@Body Ingredient body);

    //------------------------------- Dish Ingredients -------------------------------
    @POST("dish_ingredients")
    Call<DishIngredientResponse> newDishIngredient(@Header("Authorization") String token, @Body DishIngredient body);

    //------------------------------- Ingredient Measure -------------------------------
    @POST("ingredient_measures")
    Call<IngredientMeasureResponse> newIngredientMeasure(@Header("Authorization") String token, @Body IngredientMeasure body);
    //------------------------------- Ingredient Measure -------------------------------
    @GET("measures")
    Call<ArrayList<Measure>> getAllMeasures();
}
