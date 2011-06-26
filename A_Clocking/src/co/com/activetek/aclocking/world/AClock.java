package co.com.activetek.aclocking.world;

import java.awt.TrayIcon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.Properties;

import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

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
        // schedules.add( new Schedule( 1, "Turno 1", "08:00", "08:00", "08:00", "08:00", "08:00", "08:00", "08:00", "08:00" ) );
        // schedules.add( new Schedule( 2, "Turno 2", "07:00", "09:00", "07:00", "09:00", "07:00", "09:00", "07:00", "09:00" ) );
        // schedules.add( new Schedule( 3, "Turno 3", "07:00", "09:00", "07:00", "09:00", "07:00", "09:00", "07:00", "09:00" ) );
        employees = new ArrayList<Employee>( );
        // employees.add( new Employee( 1, "1032027089", "Carlos", schedules.get( 0 ) ) );
        // employees.add( new Employee( 2, "1032027589", "Andres", schedules.get( 1 ) ) );
        // employees.add( new Employee( 3, "1032027689", "Mateo", schedules.get( 2 ) ) );

        FileInputStream fis = new FileInputStream( "./data/aclocking.properties" );
        configuration = new Properties( );
        configuration.load( fis );
        fis.close( );
        File directorioData = new File( "./data/DB" );
        System.setProperty( "derby.system.home", directorioData.getAbsolutePath( ) );

        connectADB( );
        loadSchedules( );
        loadEmployees( );
        loadTemplates( );
    }
    private void loadTemplates( )
    {
        try
        {
            File templatesDir = new File( "./templates" );
            File[] templates = templatesDir.listFiles( );
            if( templates != null )
            {
                for( File file : templates )
                {
                    Employee employee = findEmployeeById( Integer.parseInt( file.getName( ).split( "=" )[ 0 ] ) );
                    DPFPFingerIndex index = DPFPFingerIndex.valueOf( file.getName( ).split( "==" )[ 1 ] );
                    FileInputStream fis = new FileInputStream( file );
                    byte[] data = new byte[fis.available( )];
                    fis.read( data );
                    DPFPTemplate template = DPFPGlobal.getTemplateFactory( ).createTemplate( );
                    template.deserialize( data );
                    employee.addTemplate( index, template );
                }
            }
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }

    private void removeTemplates( Employee employee )
    {
        try
        {
            File templatesDir = new File( "./templates" );
            File[] templates = templatesDir.listFiles( );
            if( templates != null )
            {
                for( File file : templates )
                {
                    if( file.getName( ).split( "==" )[ 0 ].equals( Integer.toString( employee.getId( ) ) ) )
                        file.delete( );
                }
            }
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
    private void loadEmployees( ) throws SQLException
    {
        Statement st = connection.createStatement( );
        String sql = "select * from employee";
        ResultSet rs = st.executeQuery( sql );
        while( rs.next( ) )
        {
            int id = rs.getInt( "employee_id" );
            Employee e = new Employee( id, rs.getString( "cedula" ), rs.getString( "name" ), findScheduleById( rs.getInt( "schedule_id" ) ) );
            employees.add( e );
        }
    }
    private void loadSchedules( ) throws SQLException
    {
        Statement st = connection.createStatement( );
        String sql = "select * from schedule";
        ResultSet rs = st.executeQuery( sql );
        while( rs.next( ) )
        {
            int id = rs.getInt( "schedule_id" );
            Schedule s = new Schedule( rs.getInt( "schedule_id" ), rs.getString( "schedule_name" ), rs.getString( "lunes" ), rs.getString( "martes" ), rs.getString( "miercoles" ), rs.getString( "jueves" ), rs.getString( "viernes" ),
                    rs.getString( "sabado" ), rs.getString( "domingo" ), rs.getString( "festivo" ), rs.getString( "lunes_out" ), rs.getString( "martes_out" ), rs.getString( "miercoles_out" ), rs.getString( "jueves_out" ),
                    rs.getString( "viernes_out" ), rs.getString( "sabado_out" ), rs.getString( "domingo_out" ), rs.getString( "festivo_out" ), rs.getBoolean( "is_lunes" ), rs.getBoolean( "is_martes" ), rs.getBoolean( "is_miercoles" ),
                    rs.getBoolean( "is_jueves" ), rs.getBoolean( "is_viernes" ), rs.getBoolean( "is_sabado" ), rs.getBoolean( "is_domingo" ), rs.getBoolean( "is_festivo" ) );
            schedules.add( s );
        }
    }
    public Employee findEmployeeById( int id )
    {
        for( Employee employee : employees )
        {
            if( employee.getId( ) == id )
                return employee;
        }
        return null;
    }
    public Schedule findScheduleById( int id )
    {
        for( Schedule schedule : schedules )
        {
            if( schedule.getId( ) == id )
                return schedule;
        }
        return null;
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

        initialiaceADB( );
    }

    private void initialiaceADB( ) throws SQLException
    {
        Statement st = connection.createStatement( );
        boolean createTables = false;
        try
        {
            st.executeQuery( "select * from EMPLOYEE where 1 = 3" );
        }
        catch( Exception e )
        {
            createTables = true;
        }
        if( createTables )
        {
            System.out.println( "creando tablas .. .." );
            String sql = "create table employee (employee_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),cedula varchar(200),name varchar(200),schedule_id int not null, primary key(employee_id))";
            st.execute( sql );

            sql = "create table event (employee_id INTEGER NOT NULL, x_time timestamp, primary key (employee_id,x_time))";
            st.execute( sql );

            sql = "create table schedule (schedule_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),schedule_name varchar(200),"
                    + "is_lunes int not null,is_martes int not null,is_miercoles int not null,is_jueves int not null,is_viernes int not null,is_sabado int not null,is_domingo int not null,is_festivo int not null,"
                    + "lunes time not null default '08:00:00',martes time not null default '08:00:00',miercoles time not null default '08:00:00',jueves time not null default '08:00:00',viernes time not null default '08:00:00',sabado time not null default '08:00:00',domingo time not null default '08:00:00',festivo time not null default '08:00:00',"
                    + "lunes_out time not null default '08:00:00',martes_out time not null default '08:00:00',miercoles_out time not null default '08:00:00',jueves_out time not null default '08:00:00',viernes_out time not null default '08:00:00',sabado_out time not null default '08:00:00',domingo_out time not null default '08:00:00',festivo_out time not null default '08:00:00',"
                    + "primary key(schedule_id))";
            System.out.println( sql );
            st.execute( sql );
            // sql =
            // "create table fingertemplate (fingertemplate_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),dir varchar(200), primary key(fingertemplate_id))";
            // st.execute( sql );
            //
            // sql = "create table employee_figertemplate (employee_id INTEGER NOT NULL,fingertemplate_id INTEGER NOT NULL)";
            // st.execute( sql );
        }
        st.close( );

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
            if( temp.getId( ) == ( e.getId( ) ) )
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

    public void deleteEmployee( Employee employee ) throws SQLException
    {
        Statement st = connection.createStatement( );
        removeTemplates( employee );
        st.execute( "delete from employee where employee_id = " + employee.getId( ) );
        employees.remove( employee );

    }
    public void editCreateSchedule( Schedule s ) throws SQLException
    {
        if( s.getId( ) == -1 )
        {
            String sql = "insert into schedule (schedule_name,is_lunes,is_martes,is_miercoles,is_jueves,is_viernes,is_sabado,is_domingo,is_festivo,lunes,martes,miercoles,jueves,viernes,sabado,domingo,festivo,lunes_out,martes_out,miercoles_out,jueves_out,viernes_out,sabado_out,domingo_out,festivo_out) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement( sql );
            int i = 1;
            ps.setString( i++, s.getScheduleName( ) );
            ps.setBoolean( i++, s.isLunes( ) );
            ps.setBoolean( i++, s.isMartes( ) );
            ps.setBoolean( i++, s.isMiercoles( ) );
            ps.setBoolean( i++, s.isJueves( ) );
            ps.setBoolean( i++, s.isViernes( ) );
            ps.setBoolean( i++, s.isSabado( ) );
            ps.setBoolean( i++, s.isDomingo( ) );
            ps.setBoolean( i++, s.isFestivo( ) );
            ps.setString( i++, s.getLunes( ) );
            ps.setString( i++, s.getMartes( ) );
            ps.setString( i++, s.getMiercoles( ) );
            ps.setString( i++, s.getJueves( ) );
            ps.setString( i++, s.getViernes( ) );
            ps.setString( i++, s.getSabado( ) );
            ps.setString( i++, s.getDomingo( ) );
            ps.setString( i++, s.getFestivo( ) );
            ps.setString( i++, s.getLunes_out( ) );
            ps.setString( i++, s.getMartes_out( ) );
            ps.setString( i++, s.getMiercoles_out( ) );
            ps.setString( i++, s.getJueves_out( ) );
            ps.setString( i++, s.getViernes_out( ) );
            ps.setString( i++, s.getSabado_out( ) );
            ps.setString( i++, s.getDomingo_out( ) );
            ps.setString( i++, s.getFestivo_out( ) );
            ps.execute( );

            sql = "select max(schedule_id) from schedule";
            Statement st = connection.createStatement( );
            ResultSet rs = st.executeQuery( sql );
            if( rs.next( ) )
            {
                s.setId( rs.getInt( 1 ) );
            }
            schedules.add( s );
        }
        else
        {
            String sql = "update schedule set schedule_name = '" + s.getScheduleName( ) + "',  is_lunes = " + ( s.isLunes( ) ? 1 : 0 ) + ",  is_martes = " + ( s.isMartes( ) ? 1 : 0 ) + ",  is_miercoles = " + ( s.isMiercoles( ) ? 1 : 0 )
                    + ", is_jueves = " + ( s.isJueves( ) ? 1 : 0 ) + ", is_viernes = " + ( s.isViernes( ) ? 1 : 0 ) + ", is_sabado = " + ( s.isSabado( ) ? 1 : 0 ) + ",  is_domingo = " + ( s.isDomingo( ) ? 1 : 0 ) + ",  is_festivo = "
                    + ( s.isFestivo( ) ? 1 : 0 ) + ", lunes = '" + s.getLunes( ) + "',  martes = '" + s.getMartes( ) + "',  miercoles = '" + s.getMiercoles( ) + "', jueves = '" + s.getJueves( ) + "',  viernes = '" + s.getViernes( ) + "', sabado = '"
                    + s.getSabado( ) + "',  domingo = '" + s.getDomingo( ) + "', festivo = '" + s.getFestivo( ) + "', lunes_out = '" + s.getLunes_out( ) + "', martes_out = '" + s.getMartes_out( ) + "', miercoles_out = '" + s.getMiercoles_out( )
                    + "',  jueves_out = '" + s.getJueves_out( ) + "',  viernes_out = '" + s.getViernes_out( ) + "', sabado_out = '" + s.getSabado_out( ) + "' , domingo_out = '" + s.getDomingo_out( ) + "',  festivo_out = '" + s.getFestivo_out( )
                    + "' where schedule_id = " + s.getId( ) + "";
            Statement st = connection.createStatement( );
            System.out.println( sql );
            st.execute( sql );
        }

    }

    public void editCreateEmployee( Employee employee ) throws SQLException
    {
        Statement st = connection.createStatement( );
        if( employee.getId( ) == -1 )
        {
            String sql = "insert into employee (cedula,name,schedule_id) values ('" + employee.getCedula( ) + "','" + employee.getNombre( ) + "'," + employee.getSchedule( ).getId( ) + ")";
            st.execute( sql );

            sql = "select max(employee_id) from employee";
            ResultSet rs = st.executeQuery( sql );
            if( rs.next( ) )
            {
                employee.setId( rs.getInt( 1 ) );
            }

            EnumMap<DPFPFingerIndex, DPFPTemplate> templates = employee.getTemplates( );

            for( DPFPFingerIndex finger : DPFPFingerIndex.values( ) )
            {
                if( templates.containsKey( finger ) )
                {
                    try
                    {
                        File templateFile = new File( "./templates/" + employee.getId( ) + "==" + finger );
                        FileOutputStream fos = new FileOutputStream( templateFile );
                        fos.write( templates.get( finger ).serialize( ) );
                    }
                    catch( Exception e )
                    {
                        e.printStackTrace( );
                    }
                }
            }

            employees.add( employee );
        }
        else
        {
            String sql = "update employee set cedula = '" + employee.getCedula( ) + "', name = '" + employee.getNombre( ) + "',schedule = " + employee.getSchedule( ).getId( ) + " where employee_id = " + employee.getId( );
            System.out.println( sql );
            st.executeUpdate( sql );
        }

    }

    public void deleteSchedule( Schedule schedule ) // Throw exception si hay algun usuario que tenga asociado este scheule
    {
        // TODO
        schedules.remove( schedule );
    }
    public void addEvent( Employee empl, String time ) throws SQLException
    {
        Statement st = connection.createStatement( );
        String sql = "insert into event (employee_id,x_time) values (" + empl.getId( ) + ",'" + time + "')";
        st.execute( sql );
    }

}
