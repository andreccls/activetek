package com.activetek.activemenu;

import java.util.ArrayList;

public class TableWrapper {

	ArrayList<Table> delegate;
	private static TableWrapper instance;
	
	public TableWrapper(ArrayList<Table> delegate) {
		this.delegate = delegate;
	}

	private TableWrapper() {
		this.delegate = new ArrayList<Table>();
	}

	public static synchronized TableWrapper getInstance() {
		if (instance == null)
			instance = new TableWrapper();
		return instance;
	}

	public ArrayList<Table> getTables()
	{
		return delegate;
	}
}
