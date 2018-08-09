package com.example.sage.foodsharebook.models;

import com.google.gson.annotations.SerializedName;

public class Ingredient{

	@SerializedName("image")
	private String image;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	public Ingredient(String name , String description, String url){
		this.name = name;
		this.description = description;
		this.image = url;
	}
	public Ingredient(IngredientResponse ingredient){
		this.name = ingredient.getName();
		this.description = ingredient.getDescription();
		this.image = ingredient.getImage();
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}



	@Override
 	public String toString(){
		return 
			"Ingredient{" + 
			"image = '" + image + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' +
			"}";
		}
}