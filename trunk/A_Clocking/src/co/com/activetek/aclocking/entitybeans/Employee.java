package co.com.activetek.aclocking.entitybeans;

import java.util.EnumMap;

import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPTemplate;

public class Employee
{

    private String cedula;
    private String nombre;
    private Schedule schedule;
    private int id;
    private EnumMap<DPFPFingerIndex, DPFPTemplate> templates;

    public Employee( int id, String ced, String nom, Schedule sch )
    {
        templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>( DPFPFingerIndex.class );
        setCedula( ced );
        setNombre( nom );
        setSchedule( sch );
        setId( id );
    }
    public Employee( int id, String ced, String nom, Schedule sch, EnumMap<DPFPFingerIndex, DPFPTemplate> templates )
    {
        this.id = id;
        this.cedula = ced;
        this.nombre = nom;
        this.schedule = sch;
        this.templates = templates;
    }
    public EnumMap<DPFPFingerIndex, DPFPTemplate> getTemplates( )
    {
        return templates;
    }

    public int getId( )
    {
        return id;
    }
    public void setId( int id )
    {
        this.id = id;
    }
    public void setCedula( String cedula )
    {
        this.cedula = cedula;
    }

    public String getCedula( )
    {
        return cedula;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    public String getNombre( )
    {
        return nombre;
    }

    public void setSchedule( Schedule schedule )
    {
        this.schedule = schedule;
    }

    public Schedule getSchedule( )
    {
        return schedule;
    }

    public String toString( )
    {
        return nombre;
    }

}
