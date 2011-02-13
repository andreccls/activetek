package com.activetek.activemenu;

import java.util.ArrayList;

public class WaiterWrapper {
	
	ArrayList<Waiter> delegate;
	private static WaiterWrapper instance;
	
	public WaiterWrapper(ArrayList<Waiter> delegate) {
		this.delegate = delegate;
	}

	private WaiterWrapper() {
		this.delegate = new ArrayList<Waiter>();
	}

	public static synchronized WaiterWrapper getInstance() {
		if (instance == null)
			instance = new WaiterWrapper();
		return instance;
	}

	public ArrayList<Waiter> getWaiters()
	{
		return delegate;
	}

}
