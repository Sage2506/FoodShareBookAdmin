package com.example.sage.foodsharebook.models;

import com.google.gson.annotations.SerializedName;

public class IngredientMeasure{

	@SerializedName("ingredient_id")
	private int ingredientId;

	@SerializedName("measure_id")
	private int measureId;

	public IngredientMeasure(int ingredientId, int measureId){
		this.ingredientId = ingredientId;
		this.measureId = measureId;
	}

	public void setIngredientId(int ingredientId){
		this.ingredientId = ingredientId;
	}

	public int getIngredientId(){
		return ingredientId;
	}

	public void setMeasureId(int measureId){
		this.measureId = measureId;
	}

	public int getMeasureId(){
		return measureId;
	}

	@Override
 	public String toString(){
		return 
			"IngredientMeasure{" + 
			"ingredient_id = '" + ingredientId + '\'' + 
			",measure_id = '" + measureId + '\'' + 
			"}";
		}
}