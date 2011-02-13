package com.activetek.activemenu;

import java.util.ArrayList;

public class CategoryWrapper {

	ArrayList<Category> delegate;
	private static CategoryWrapper instance;
	
	public CategoryWrapper(ArrayList<Category> delegate) {
		this.delegate = delegate;
	}

	private CategoryWrapper() {
		this.delegate = new ArrayList<Category>();
	}

	public static synchronized CategoryWrapper getInstance() {
		if (instance == null)
			instance = new CategoryWrapper();
		return instance;
	}

	
	public ArrayList<Category> getCategories()
	{
		return delegate;
	}
}
