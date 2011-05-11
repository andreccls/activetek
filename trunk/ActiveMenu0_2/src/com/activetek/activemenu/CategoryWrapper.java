package com.activetek.activemenu;

import java.util.ArrayList;

/**
 * Almacen de categorías
 * @author juan
 *
 */
public class CategoryWrapper {

	/**
	 * ArrayList de Categorías
	 */
	private ArrayList<Category> delegate;
	/**
	 * Instancia Singleton del almacen
	 */
	private static CategoryWrapper instance;
	/**
	 * Constructor del Almacen
	 * @param delegate Array de Categorías
	 */
	public CategoryWrapper(ArrayList<Category> delegate) {
		this.delegate = delegate;
	}

	// Creador vacío de la primera instancia.
	private CategoryWrapper() {
		this.delegate = new ArrayList<Category>();
	}

	/**
	 * Instanciador del almacen
	 * @return Instancia Singleton del almacen
	 */
	public static synchronized CategoryWrapper getInstance() {
		if (instance == null)
			instance = new CategoryWrapper();
		return instance;
	}

	/**
	 * Método que retorna el array de categorías
	 * @return array de categorías
	 */
	public ArrayList<Category> getCategories()
	{
		return delegate;
	}
	
	public MenuItem getMenuItemById(int itid)
	{
		for(int i=0;i<delegate.size();i++)
		{
			Category cat=delegate.get(i);
			ArrayList<MenuItem> arr=cat.getFoods();
			for(int j=0; j<arr.size();j++)
			{
				MenuItem men=arr.get(j);
				ArrayList<Price> pri=men.getPrices();
				for(int k=0; k<pri.size();k++)
				{
					Price pr=pri.get(k);
					if(pr.getId()==itid)
						return men;
				}
			}
		}
		return null;
	}
}
