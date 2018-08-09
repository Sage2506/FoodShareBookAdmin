package com.example.sage.foodsharebook.models;

import com.google.gson.annotations.SerializedName;

public class IngredientMeasureResponse{

	@SerializedName("measure")
	private Measure measure;

	@SerializedName("ingredient")
	private Ingredient ingredient;

	@SerializedName("id")
	private int id;

	public void setMeasure(Measure measure){
		this.measure = measure;
	}

	public Measure getMeasure(){
		return measure;
	}

	public void setIngredient(Ingredient ingredient){
		this.ingredient = ingredient;
	}

	public Ingredient getIngredient(){
		return ingredient;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"IngredientMeasureResponse{" + 
			"measure = '" + measure + '\'' + 
			",ingredient = '" + ingredient + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}