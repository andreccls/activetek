package co.com.activetek.aclocking.world;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import co.com.activetek.aclocking.entitybeans.Employee;
import co.com.activetek.aclocking.entitybeans.Schedule;

public class AClock
{

    private ArrayList<Employee> employees;
    private ArrayList<Schedule> schedules;
    private Properties configuration;
    private Connection connection;
    public AClock( ) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
    {
        schedules = new ArrayList<Schedule>( );
        schedules.add( new Schedule( 1, "Turno 1", "08:00", "08:00", "08:00", "08:00", "08:00", "08:00", "08:00", "08:00" ) );
        schedules.add( new Schedule( 2, "Turno 2", "07:00", "09:00", "07:00", "09:00", "07:00", "09:00", "07:00", "09:00" ) );
        schedules.add( new Schedule( 3, "Turno 3", "07:00", "09:00", "07:00", "09:00", "07:00", "09:00", "07:00", "09:00" ) );
        employees = new ArrayList<Employee>( );
        employees.add( new Employee( 1, "1032027089", "Carlos", schedules.get( 0 ) ) );
        employees.add( new Employee( 2, "1032027589", "Andres", schedules.get( 1 ) ) );
        employees.add( new Employee( 3, "1032027689", "Mateo", schedules.get( 2 ) ) );

        FileInputStream fis = new FileInputStream( "./data/aclocking.properties" );
        configuration = new Properties( );
        configuration.load( fis );
        fis.close( );
        connectADB( );
        File directorioData = new File( "./data/DB" );
        System.setProperty( "derby.system.home", directorioData.getAbsolutePath( ) );
    }

    public ArrayList<Employee> getEmployees( )
    {
        return employees;
    }
    public ArrayList<Schedule> getSchedules( )
    {
        return schedules;
    }
    public void connectADB( ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
    {
        String driver = configuration.getProperty( "aclocking.db.driver" );
        Class.forName( driver ).newInstance( );

        String url = configuration.getProperty( "aclocking.db.url" );
        connection = DriverManager.getConnection( url );
    }

    public void setEmployee( Employee e )
    {
        boolean existing = false;
        for( int i = 0; i < employees.size( ); i++ )
        {
            Employee temp = employees.get( i );
            if( temp.getCedula( ).equals( e.getCedula( ) ) )
            {
                temp.setNombre( e.getNombre( ) );
                temp.setSchedule( e.getSchedule( ) );
                existing = true;
            }
        }
        if( !existing )
        {
            employees.add( e );
        }
    }

    public void setSchedule( Schedule e )
    {
        boolean existing = false;
        for( int i = 0; i < schedules.size( ); i++ )
        {
            Schedule temp = schedules.get( i );
            if( temp.getCode( ) == ( e.getCode( ) ) )
            {
                temp.setLunes( e.getLunes( ) );
                temp.setMartes( e.getMartes( ) );
                temp.setMiercoles( e.getMiercoles( ) );
                temp.setJueves( e.getJueves( ) );
                temp.setViernes( e.getViernes( ) );
                temp.setSabado( e.getSabado( ) );
                temp.setDomingo( e.getDomingo( ) );
                temp.setFestivo( e.getFestivo( ) );
                existing = true;
            }
        }
        if( !existing )
        {
            schedules.add( e );
        }
    }

    public void deleteEmployee( Employee employee )
    {
        // TODO
        employees.remove( employee );

    }

    public void editCreateEmployee( Employee employee )
    {
        if( employee.getId( ) == -1 )
        {
            // TODO hacer toda la logica para editarlo (el id es -1 en caso de ser nuevo)
        }
        else
        {
            // TODO editar la instancia en memoria y en BD
        }

    }

    public void deleteSchedule( Schedule schedule ) // Throw exception si hay algun usuario que tenga asociado este scheule
    {
        // TODO
        schedules.remove( schedule );
    }

}
