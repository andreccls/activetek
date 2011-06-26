package co.com.activetek.aclocking.entitybeans;

public class Schedule
{
    private int id;
    private String scheduleName;
    private String lunes;
    private String martes;
    private String miercoles;
    private String jueves;
    private String viernes;
    private String sabado;
    private String domingo;
    private String festivo;
    private String lunes_out;
    private String martes_out;
    private String miercoles_out;
    private String jueves_out;
    private String viernes_out;
    private String sabado_out;
    private String domingo_out;
    private String festivo_out;
    private boolean isLunes = true;
    private boolean isMartes = true;
    private boolean isMiercoles = true;
    private boolean isJueves = true;
    private boolean isViernes = true;
    private boolean isSabado = true;
    private boolean isDomingo = true;
    private boolean isFestivo = true;
    public Schedule( int id, String scheduleName, String lunes, String martes, String miercoles, String jueves, String viernes, String sabado, String domingo, String festivo, String lunes_out, String martes_out, String miercoles_out,
            String jueves_out, String viernes_out, String sabado_out, String domingo_out, String festivo_out, boolean isLunes, boolean isMartes, boolean isMiercoles, boolean isJueves, boolean isViernes, boolean isSabado, boolean isDomingo,
            boolean isFestivo )
    {
        super( );
        this.id = id;
        this.scheduleName = scheduleName;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
        this.domingo = domingo;
        this.festivo = festivo;
        this.lunes_out = lunes_out;
        this.martes_out = martes_out;
        this.miercoles_out = miercoles_out;
        this.jueves_out = jueves_out;
        this.viernes_out = viernes_out;
        this.sabado_out = sabado_out;
        this.domingo_out = domingo_out;
        this.festivo_out = festivo_out;
        this.isLunes = isLunes;
        this.isMartes = isMartes;
        this.isMiercoles = isMiercoles;
        this.isJueves = isJueves;
        this.isViernes = isViernes;
        this.isSabado = isSabado;
        this.isDomingo = isDomingo;
        this.isFestivo = isFestivo;
    }
    public int getId( )
    {
        return id;
    }
    public void setId( int id )
    {
        this.id = id;
    }
    public String getScheduleName( )
    {
        return scheduleName;
    }
    public void setScheduleName( String scheduleName )
    {
        this.scheduleName = scheduleName;
    }
    public String getLunes( )
    {
        return lunes;
    }
    public void setLunes( String lunes )
    {
        this.lunes = lunes;
    }
    public String getMartes( )
    {
        return martes;
    }
    public void setMartes( String martes )
    {
        this.martes = martes;
    }
    public String getMiercoles( )
    {
        return miercoles;
    }
    public void setMiercoles( String miercoles )
    {
        this.miercoles = miercoles;
    }
    public String getJueves( )
    {
        return jueves;
    }
    public void setJueves( String jueves )
    {
        this.jueves = jueves;
    }
    public String getViernes( )
    {
        return viernes;
    }
    public void setViernes( String viernes )
    {
        this.viernes = viernes;
    }
    public String getSabado( )
    {
        return sabado;
    }
    public void setSabado( String sabado )
    {
        this.sabado = sabado;
    }
    public String getDomingo( )
    {
        return domingo;
    }
    public void setDomingo( String domingo )
    {
        this.domingo = domingo;
    }
    public String getFestivo( )
    {
        return festivo;
    }
    public void setFestivo( String festivo )
    {
        this.festivo = festivo;
    }
    public String getLunes_out( )
    {
        return lunes_out;
    }
    public void setLunes_out( String lunes_out )
    {
        this.lunes_out = lunes_out;
    }
    public String getMartes_out( )
    {
        return martes_out;
    }
    public void setMartes_out( String martes_out )
    {
        this.martes_out = martes_out;
    }
    public String getMiercoles_out( )
    {
        return miercoles_out;
    }
    public void setMiercoles_out( String miercoles_out )
    {
        this.miercoles_out = miercoles_out;
    }
    public String getJueves_out( )
    {
        return jueves_out;
    }
    public void setJueves_out( String jueves_out )
    {
        this.jueves_out = jueves_out;
    }
    public String getViernes_out( )
    {
        return viernes_out;
    }
    public void setViernes_out( String viernes_out )
    {
        this.viernes_out = viernes_out;
    }
    public String getSabado_out( )
    {
        return sabado_out;
    }
    public void setSabado_out( String sabado_out )
    {
        this.sabado_out = sabado_out;
    }
    public String getDomingo_out( )
    {
        return domingo_out;
    }
    public void setDomingo_out( String domingo_out )
    {
        this.domingo_out = domingo_out;
    }
    public String getFestivo_out( )
    {
        return festivo_out;
    }
    public void setFestivo_out( String festivo_out )
    {
        this.festivo_out = festivo_out;
    }
    public boolean isLunes( )
    {
        return isLunes;
    }
    public void setLunes( boolean isLunes )
    {
        this.isLunes = isLunes;
    }
    public boolean isMartes( )
    {
        return isMartes;
    }
    public void setMartes( boolean isMartes )
    {
        this.isMartes = isMartes;
    }
    public boolean isMiercoles( )
    {
        return isMiercoles;
    }
    public void setMiercoles( boolean isMiercoles )
    {
        this.isMiercoles = isMiercoles;
    }
    public boolean isJueves( )
    {
        return isJueves;
    }
    public void setJueves( boolean isJueves )
    {
        this.isJueves = isJueves;
    }
    public boolean isViernes( )
    {
        return isViernes;
    }
    public void setViernes( boolean isViernes )
    {
        this.isViernes = isViernes;
    }
    public boolean isSabado( )
    {
        return isSabado;
    }
    public void setSabado( boolean isSabado )
    {
        this.isSabado = isSabado;
    }
    public boolean isDomingo( )
    {
        return isDomingo;
    }
    public void setDomingo( boolean isDomingo )
    {
        this.isDomingo = isDomingo;
    }
    public boolean isFestivo( )
    {
        return isFestivo;
    }
    public void setFestivo( boolean isFestivo )
    {
        this.isFestivo = isFestivo;
    }
    public String toString()
    {
        return scheduleName;
    }
}
