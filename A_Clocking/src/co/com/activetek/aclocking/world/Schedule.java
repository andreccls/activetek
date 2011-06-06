package co.com.activetek.aclocking.world;


public class Schedule {
	
	private String lunes;
	private String martes;
	private String miercoles;
	private String jueves;
	private String viernes;
	private String sabado;
	private String domingo;
	public String getLunes() {
		return lunes;
	}

	public void setLunes(String lunes) {
		this.lunes = lunes;
	}

	public String getMartes() {
		return martes;
	}

	public void setMartes(String martes) {
		this.martes = martes;
	}

	public String getMiercoles() {
		return miercoles;
	}

	public void setMiercoles(String miercoles) {
		this.miercoles = miercoles;
	}

	public String getJueves() {
		return jueves;
	}

	public void setJueves(String jueves) {
		this.jueves = jueves;
	}

	public String getViernes() {
		return viernes;
	}

	public void setViernes(String viernes) {
		this.viernes = viernes;
	}

	public String getSabado() {
		return sabado;
	}

	public void setSabado(String sabado) {
		this.sabado = sabado;
	}

	private String festivo;
	private int code;
	
	public Schedule(int cod,String l, String m, String i, String j, String v, String s, String d, String f)
	{
		setCode(cod);
		lunes=l;
		martes=m;
		miercoles=i;
		jueves=j;
		viernes=v;
		sabado=s;
		setDomingo(d);
		setFestivo(f);
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setFestivo(String festivo) {
		this.festivo = festivo;
	}

	public String getFestivo() {
		return festivo;
	}

	public void setDomingo(String domingo) {
		this.domingo = domingo;
	}

	public String getDomingo() {
		return domingo;
	}
	
	

}
