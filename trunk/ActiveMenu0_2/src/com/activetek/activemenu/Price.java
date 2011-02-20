package com.activetek.activemenu;

public class Price {
	
	private int cost;
	private int units;
	private int id;
	
	public Price(int costo,int unidades, int ide)
	{
		cost=costo;
		units=unidades;
		id=ide;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getCost() {
		return cost;
	}
	public void setUnits(int units) {
		this.units = units;
	}
	public int getUnits() {
		return units;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	

}
