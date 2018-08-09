package com.example.sage.foodsharebook.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Dish{

	@SerializedName("name")
	private String name;

	@SerializedName("recipe")
	private String recipe;

	@SerializedName("description")
	private String description;

	public Dish(String name, String recipe, String description){
		this.name = name;
		this.recipe = recipe;
		this.description = description;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setRecipe(String recipe){
		this.recipe = recipe;
	}

	public String getRecipe(){
		return recipe;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

}