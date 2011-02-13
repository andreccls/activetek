package com.activetek.activemenu;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Category {
	private String name;
	private ArrayList<MenuItem> foods;
	private Bitmap image;
	
	public Category(String nombre, ArrayList<MenuItem> comidas, String path)
	{
		name=nombre;
		foods=comidas;
		image= BitmapUtils.loadBitmap(path);
	}
	
	//Solo para prueba
	public Category(String nombre, ArrayList<MenuItem> comidas)
	{
		name=nombre;
		foods=comidas;
		image= null;
	}
	
	public Bitmap getImage()
	{
		return image;
	}
	public ArrayList<MenuItem> getFoods()
	{
		return foods;
	}
	public String getName()
	{
		return name;
	}

}
