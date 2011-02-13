package com.activetek.activemenu;

import android.graphics.Bitmap;

public class MenuItem {

	private String name;
	private int code;
	private Bitmap image;
	private int price;
	private String description;

	public MenuItem(String nombre, int codigo, String path, int precio, String descripcion)
	{
		name=nombre;
		code=codigo;
		image= BitmapUtils.loadBitmap(path);
		price=precio;
		description=descripcion;
	}

	public Bitmap getImage()
	{
		return image;
	}
	public int getCode()
	{
		return code;
	}
	public String getName()
	{
		return name;
	}
	public String getDescription()
	{
		return description;
	}
	public int getPrice()
	{
		return price;
	}
}
