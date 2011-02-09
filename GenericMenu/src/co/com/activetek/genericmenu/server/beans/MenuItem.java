package co.com.activetek.genericmenu.server.beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import co.com.activetek.genericmenu.server.util.GenericMenuDAO;

public class MenuItem {
	private int id;
	private String nombre;
	private String description;
	private boolean enable;
	private String icon;
	private Vector<MenuItem> sons;
	
	public MenuItem(int id, String nombre, String description, boolean enable,
			String icon) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.description = description;
		this.enable = enable;
		this.icon = icon;
		sons = new Vector<MenuItem>();
	}
	public void addSon(MenuItem son)
	{
		sons.add(son);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void loadSons( ) throws SQLException {
		sons = GenericMenuDAO.getInstance().getChildren(id);
		for (MenuItem son : sons) {
			son.loadSons();
		}
	}
	public Vector<MenuItem> getSons()
	{
		return sons;
	}
	public String toString()
	{
		return nombre;
	}
	
	
}
