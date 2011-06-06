package co.com.activetek.aclocking.entitybeans;

public class Schedule
{
    private String scheduleName;
    private String lunes;
    private String martes;
    private String miercoles;
    private String jueves;
    private String viernes;
    private String sabado;
    private String domingo;
    private String festivo;
    private int code;
    public String getScheduleName( )
    {
        return scheduleName;
    }

    public void setScheduleName( String scheduleName )
    {
        this.scheduleName = scheduleName;
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

    private boolean isLunes = true;
    private boolean isMartes = true;
    private boolean isMiercoles = true;
    private boolean isJueves = true;
    private boolean isViernes = true;
    private boolean isSabado = true;
    private boolean isDomingo = true;
    private boolean isFestivo = true;
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

    public Schedule( int cod, String name, String l, String m, String i, String j, String v, String s, String d, String f )
    {
        setCode( cod );
        lunes = l;
        martes = m;
        miercoles = i;
        jueves = j;
        viernes = v;
        sabado = s;
        setDomingo( d );
        setFestivo( f );
        scheduleName = name;
    }

    public void setCode( int code )
    {
        this.code = code;
    }

    public int getCode( )
    {
        return code;
    }

    public void setFestivo( String festivo )
    {
        this.festivo = festivo;
    }

    public String getFestivo( )
    {
        return festivo;
    }

    public void setDomingo( String domingo )
    {
        this.domingo = domingo;
    }

    public String getDomingo( )
    {
        return domingo;
    }

    public String toString( )
    {
        return scheduleName;
    }

}
