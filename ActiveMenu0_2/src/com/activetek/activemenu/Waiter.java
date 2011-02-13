package com.activetek.activemenu;

import android.graphics.Bitmap;

public class Waiter {
	
	private int code;
	private String name;
	private Bitmap image;
	
	public Waiter(String nombre, int codigo, String path)
	{
		name=nombre;
		code=codigo;
		image= BitmapUtils.loadBitmap(path);
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

}
