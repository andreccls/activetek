package co.com.activetek.aclocking.world;

public class Employee {
	
	private String cedula;
	private String nombre;
	private Schedule schedule;

	
	public Employee(String ced, String nom,Schedule sch)
	{
		setCedula(ced);
		setNombre(nom);
		setSchedule(sch);
	}


	public void setCedula(String cedula) {
		this.cedula = cedula;
	}


	public String getCedula() {
		return cedula;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getNombre() {
		return nombre;
	}


	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}


	public Schedule getSchedule() {
		return schedule;
	}
	
	public String toString()
	{
	    return nombre;
	}

}
