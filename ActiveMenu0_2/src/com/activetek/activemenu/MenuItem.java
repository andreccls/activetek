package com.activetek.activemenu;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class MenuItem {

	private String name;
	private int code;
	private ArrayList<Bitmap> images;
	private ArrayList<Price> prices;
	private String description;

	public MenuItem(String nombre, int codigo, ArrayList<String> path, ArrayList<Price> precios, String descripcion)
	{
		name=nombre;
		code=codigo;
		images=new ArrayList<Bitmap>();
		for(int i=0;i<path.size();i++)
		{
			images.add(BitmapUtils.loadBitmap(path.get(i)));
		}
		prices=precios;
		description=descripcion;
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

	public void setImages(ArrayList<Bitmap> images) {
		this.images = images;
	}

	public ArrayList<Bitmap> getImages() {
		return images;
	}

	public void setPrices(ArrayList<Price> prices) {
		this.prices = prices;
	}

	public ArrayList<Price> getPrices() {
		return prices;
	}
}
