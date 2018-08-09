package com.example.sage.foodsharebook.models;

import com.google.gson.annotations.SerializedName;

public class Measure{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	private boolean selected;
	public Measure(){
		selected = false;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
	public boolean isSelected(){
		return this.selected;
	}
	public void setSelected(boolean value){
		this.selected = value;
	}
	@Override
 	public String toString(){
		return 
			"Measure{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' +
			",selected = '" + selected + '\'' +
					"}";
		}

}