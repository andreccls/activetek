package com.activetek.activemenu;

import java.util.ArrayList;

import android.graphics.Bitmap;

/**
 * Clase que describe la categoría
 * @author juan
 *
 */
public class Category {
	/**
	 * Nombre de la categoría
	 */
	private String name;
	/**
	 * ArrayList de los MenuItems de la categoría
	 */
	private ArrayList<MenuItem> foods;
	/**
	 * Imágen de la Categoría
	 */
	private Bitmap image;
	
	/**
	 * Constructor de la categoria
	 * @param nombre
	 * @param comidas
	 * @param path Loacación absoluta del recurso de imágen
	 */
	public Category(String nombre, ArrayList<MenuItem> comidas, String path)
	{
		name=nombre;
		foods=comidas;
		image= BitmapUtils.loadBitmap(path);
	}
	// Implementacion sin imagen, revisar!
	public Category(String nombre, ArrayList<MenuItem> comidas)
	{
		name=nombre;
		foods=comidas;
		image= null;
	}
	
	/**
	 * Devolver imagen
	 * @return la imagen de la categoría
	 */
	public Bitmap getImage()
	{
		return image;
	}
	/**
	 * Devolver el array de MenuItems
	 * @return el array de MenuItems
	 */
	public ArrayList<MenuItem> getFoods()
	{
		return foods;
	}
	/**
	 * Devolver el nombre de la categoría
	 * @return Nombre de la categoría
	 */
	public String getName()
	{
		return name;
	}

}
