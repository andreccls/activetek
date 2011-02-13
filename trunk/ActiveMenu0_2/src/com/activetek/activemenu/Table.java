package com.activetek.activemenu;

public class Table {
	
	private int number;
	private boolean busy;
	
	public Table(int numero, boolean ocupado)
	{
		number=numero;
		busy=ocupado;
	}
	
	public boolean isBusy()
	{
		return busy;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public void setBusy(boolean ocu)
	{
		busy=ocu;
	}

}
