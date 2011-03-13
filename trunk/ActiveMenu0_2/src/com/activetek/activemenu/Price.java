package com.activetek.activemenu;

public class Price {
	
	private int cost;
	private int units;
	private int id;
	private String description;
	
	public Price(int costo,int unidades, int ide, String descripcion)
	{
		cost=costo;
		units=unidades;
		id=ide;
		setDescription(descripcion);
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
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	

}
