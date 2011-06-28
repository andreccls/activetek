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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.Properties;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;

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
        employees = new ArrayList<Employee>( );

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
            String sql = "update employee set cedula = '" + employee.getCedula( ) + "', name = '" + employee.getNombre( ) + "',schedule_id = " + employee.getSchedule( ).getId( ) + " where employee_id = " + employee.getId( );
            System.out.println( sql );
            st.executeUpdate( sql );
        }

    }

    public void deleteSchedule( Schedule schedule ) throws SQLException // Throw exception si hay algun usuario que tenga asociado este scheule
    {
        Statement st = connection.createStatement( );
        String sql =  "delete from schedule where schedule_id = " + schedule.getId( );
        st.execute( sql );
        schedules.remove( schedule );
    }
    public void addEvent( Employee empl, String time ) throws SQLException
    {
        Statement st = connection.createStatement( );
        String sql = "insert into event (employee_id,x_time) values (" + empl.getId( ) + ",'" + time + "')";
        st.execute( sql );
    }
    public void generateRerport( File fileReport, Date ini_date, Date end_date ) throws SQLException, IOException
    {
        Statement st = connection.createStatement( );
        HSSFWorkbook wb = new HSSFWorkbook( );
        HSSFSheet sh = wb.createSheet( "Eventos" );
        String sql = "select name,x_time from event ev, employee em where ev.employee_id = em.employee_id order by x_time";
        saveQuery( sh, wb, st.executeQuery( sql ), 0, ( short )0, true );

        sh = wb.createSheet( "Dia-Dia" );
        generateDaylyReport( sh, wb, ini_date, end_date );

        FileOutputStream fos = new FileOutputStream( fileReport );
        wb.write( fos );
        fos.close( );
    }
    public void generateDaylyReport( HSSFSheet sh, HSSFWorkbook wb, Date ini_date, Date end_date ) throws SQLException
    {
        int numRow = 0;
        HSSFRow row = sh.createRow( numRow++ );
        CreationHelper createHelper = wb.getCreationHelper( );
        CellStyle style = wb.createCellStyle( );
        String[] headers = { "NOMBRE", "FECHA", "HORA_ENTRADA_ESPERADA", "HORA_ENTRADA", "HORA_SALIDA_ESPERADA", "HORA_SALIDA", "MINUTOS_TARDE" };
        short numColumn = 0;
        for( String header : headers )
        {
            HSSFCell cell = row.createCell( numColumn++ );
            cell.setCellValue( header );
        }
        numColumn = 0;

        Calendar c_ini = Calendar.getInstance( );
        c_ini.setTime( ini_date );
        Calendar c_end = Calendar.getInstance( );
        c_end.setTime( end_date );
        while( c_ini.before( c_end ) || c_ini.compareTo( c_end ) == 0 )
        {
            for( Employee employee : employees )
            {

                if( c_ini.get( Calendar.DAY_OF_WEEK ) == 2 )// lunes
                {
                    if( !employee.getSchedule( ).isLunes( ) )// **
                        continue;
                    // NOMBRE
                    row = sh.createRow( numRow++ );
                    HSSFCell cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getNombre( ) );

                    // FECHA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( c_ini.getTime( ) );
                    style.setDataFormat( createHelper.createDataFormat( ).getFormat( "yyy-mm-dd" ) );
                    cell.setCellStyle( style );

                    // HORA_ENTRADA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getLunes( ) );// **

                    // HORA_ENTRADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getIniHour( employee, c_ini.getTime( ) ) );

                    // HORA_SALIDA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getLunes_out( ) );// **

                    // HORA_SALIDA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );

                    // MINUTOS_TARDE TODO
                    // cell = row.createCell( numColumn++ );
                    // cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );

                }
                else if( c_ini.get( Calendar.DAY_OF_WEEK ) == 3 )// martes
                {
                    if( !employee.getSchedule( ).isMartes( ) )
                        continue;
                    // NOMBRE
                    row = sh.createRow( numRow++ );
                    HSSFCell cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getNombre( ) );

                    // FECHA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( c_ini.getTime( ) );
                    style.setDataFormat( createHelper.createDataFormat( ).getFormat( "yyy-mm-dd" ) );
                    cell.setCellStyle( style );

                    // HORA_ENTRADA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getMartes( ) );// **

                    // HORA_ENTRADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getIniHour( employee, c_ini.getTime( ) ) );

                    // HORA_SALIDA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getMartes_out( ) );// **

                    // HORA_SALIDA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );

                    // MINUTOS_TARDE TODO
                    // cell = row.createCell( numColumn++ );
                    // cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );
                }
                else if( c_ini.get( Calendar.DAY_OF_WEEK ) == 4 )// miercoles
                {
                    if( !employee.getSchedule( ).isMiercoles( ) )
                        continue;
                    // NOMBRE
                    row = sh.createRow( numRow++ );
                    HSSFCell cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getNombre( ) );

                    // FECHA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( c_ini.getTime( ) );
                    style.setDataFormat( createHelper.createDataFormat( ).getFormat( "yyy-mm-dd" ) );
                    cell.setCellStyle( style );

                    // HORA_ENTRADA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getMiercoles( ) );// **

                    // HORA_ENTRADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getIniHour( employee, c_ini.getTime( ) ) );

                    // HORA_SALIDA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getMartes_out( ) );// **

                    // HORA_SALIDA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );

                    // MINUTOS_TARDE TODO
                    // cell = row.createCell( numColumn++ );
                    // cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );
                }
                else if( c_ini.get( Calendar.DAY_OF_WEEK ) == 5 )// jueves
                {
                    if( !employee.getSchedule( ).isJueves( ) )
                        continue;
                    // NOMBRE
                    row = sh.createRow( numRow++ );
                    HSSFCell cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getNombre( ) );

                    // FECHA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( c_ini.getTime( ) );
                    style.setDataFormat( createHelper.createDataFormat( ).getFormat( "yyy-mm-dd" ) );
                    cell.setCellStyle( style );

                    // HORA_ENTRADA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getJueves( ) );// **

                    // HORA_ENTRADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getIniHour( employee, c_ini.getTime( ) ) );

                    // HORA_SALIDA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getJueves_out( ) );// **

                    // HORA_SALIDA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );

                    // MINUTOS_TARDE TODO
                    // cell = row.createCell( numColumn++ );
                    // cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );
                }
                else if( c_ini.get( Calendar.DAY_OF_WEEK ) == 6 )// viernes
                {
                    if( !employee.getSchedule( ).isViernes( ) )
                        continue;
                    // NOMBRE
                    row = sh.createRow( numRow++ );
                    HSSFCell cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getNombre( ) );

                    // FECHA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( c_ini.getTime( ) );
                    style.setDataFormat( createHelper.createDataFormat( ).getFormat( "yyy-mm-dd" ) );
                    cell.setCellStyle( style );

                    // HORA_ENTRADA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getViernes( ) );// **

                    // HORA_ENTRADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getIniHour( employee, c_ini.getTime( ) ) );

                    // HORA_SALIDA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getViernes_out( ) );// **

                    // HORA_SALIDA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );

                    // MINUTOS_TARDE TODO
                    // cell = row.createCell( numColumn++ );
                    // cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );
                }
                else if( c_ini.get( Calendar.DAY_OF_WEEK ) == 7 )// sabado
                {
                    if( !employee.getSchedule( ).isSabado( ) )
                        continue;
                    // NOMBRE
                    row = sh.createRow( numRow++ );
                    HSSFCell cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getNombre( ) );

                    // FECHA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( c_ini.getTime( ) );
                    style.setDataFormat( createHelper.createDataFormat( ).getFormat( "yyy-mm-dd" ) );
                    cell.setCellStyle( style );

                    // HORA_ENTRADA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getSabado( ) );// **

                    // HORA_ENTRADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getIniHour( employee, c_ini.getTime( ) ) );

                    // HORA_SALIDA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getSabado_out( ) );// **

                    // HORA_SALIDA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );

                    // MINUTOS_TARDE TODO
                    // cell = row.createCell( numColumn++ );
                    // cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );
                }
                else if( c_ini.get( Calendar.DAY_OF_WEEK ) == 1 )// domingo
                {
                    if( !employee.getSchedule( ).isDomingo( ) )
                        continue;
                    // NOMBRE
                    row = sh.createRow( numRow++ );
                    HSSFCell cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getNombre( ) );

                    // FECHA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( c_ini.getTime( ) );
                    style.setDataFormat( createHelper.createDataFormat( ).getFormat( "yyy-mm-dd" ) );
                    cell.setCellStyle( style );

                    // HORA_ENTRADA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getDomingo( ) );// **

                    // HORA_ENTRADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getIniHour( employee, c_ini.getTime( ) ) );

                    // HORA_SALIDA_ESPERADA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( employee.getSchedule( ).getDomingo_out( ) );// **

                    // HORA_SALIDA
                    cell = row.createCell( numColumn++ );
                    cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );

                    // MINUTOS_TARDE TODO
                    // cell = row.createCell( numColumn++ );
                    // cell.setCellValue( getEndHour( employee, c_ini.getTime( ) ) );
                }
                else
                {
                    System.err.println( "No se reconoce el dia de la semana" );
                }
                numColumn = 0;
            }
            c_ini.add( Calendar.DAY_OF_MONTH, 1 );
        }
    }

    public String getIniHour( Employee e, Date date ) throws SQLException
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        Statement st = connection.createStatement( );
        String sql = "select min(time(x_time)) as x_time from event where date(x_time) = '" + sdf.format( date ) + "' and employee_id = " + e.getId( );
        ResultSet rs = st.executeQuery( sql );
        String result = "00:00:00";
        if( rs.next( ) )
        {
            result = rs.getString( 1 );
        }
        return result;
    }
    public String getEndHour( Employee e, Date date ) throws SQLException
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        Statement st = connection.createStatement( );
        String sql = "select max(time(x_time)) as x_time from event where date(x_time) = '" + sdf.format( date ) + "' and employee_id = " + e.getId( );
        ResultSet rs = st.executeQuery( sql );
        String result = "00:00:00";
        if( rs.next( ) )
        {
            result = rs.getString( 1 );
        }
        return result;
    }
    public void saveQuery( HSSFSheet sheet, HSSFWorkbook wb, ResultSet rs, int numRaw, short x_numColumn, boolean printHeader ) throws SQLException
    {
        ResultSetMetaData metadata = rs.getMetaData( );
        int numColums = metadata.getColumnCount( );
        HSSFRow raw;

        if( printHeader )
        {
            raw = sheet.getRow( numRaw );// con el raw.createRaw se borran las formulas
            raw = raw == null ? sheet.createRow( numRaw ) : raw;
            short numColumn = x_numColumn;
            for( int i = 1; i <= numColums && printHeader; i++ )
            {
                HSSFCell cell = raw.getCell( numColumn );
                cell = cell == null ? raw.createCell( numColumn ) : cell;
                numColumn++;
                cell.setCellValue( metadata.getColumnName( i ) );
            }
            numRaw++;
        }

        while( rs.next( ) )
        {
            raw = sheet.getRow( numRaw );// con el raw.createRaw se borran las formulas
            raw = raw == null ? sheet.createRow( numRaw ) : raw;
            numRaw++;

            short numColumn = x_numColumn;
            for( int i = 1; i <= numColums; i++ )
            {
                HSSFCell cell = raw.getCell( numColumn );
                int columnType = metadata.getColumnType( i );
                cell = cell == null ? raw.createCell( numColumn ) : cell;
                numColumn++;

                if( metadata.getColumnName( i ).equals( "VARCHAR" ) )// dado que para UNKNOWN tambien entraba aca, mejor se compara como un string
                {
                    cell.setCellValue( rs.getString( i ) );
                }
                else if( columnType == Types.DECIMAL || columnType == Types.DOUBLE )
                {
                    cell.setCellValue( rs.getDouble( i ) );
                }
                else if( columnType == Types.DATE || columnType == Types.TIMESTAMP )
                {
                    CreationHelper createHelper = wb.getCreationHelper( );
                    CellStyle style = wb.createCellStyle( );
                    style.setDataFormat( createHelper.createDataFormat( ).getFormat( "yyy-mm-dd hh:mm" ) );
                    cell.setCellStyle( style );
                    cell.setCellValue( rs.getTimestamp( i ) );
                }
                else if( columnType == Types.TIME )
                {
                    cell.setCellValue( rs.getString( i ) );
                }
                else if( columnType == Types.TINYINT || columnType == Types.INTEGER || columnType == Types.DECIMAL || columnType == Types.DOUBLE )
                {
                    cell.setCellValue( rs.getInt( i ) );
                }
                else
                // probablemente es UNKNOWN
                {
                    try
                    {
                        cell.setCellValue( rs.getDouble( i ) );
                    }
                    catch( Exception e )
                    {
                        try
                        {
                            cell.setCellValue( rs.getInt( i ) );
                        }
                        catch( Exception e2 )
                        {
                            try
                            {
                                cell.setCellValue( rs.getString( i ) );
                            }
                            catch( Exception e3 )
                            {
                                e3.printStackTrace( );
                                // TODO poner log de error aqui
                            }
                        }
                    }
                }
            }
        }

    }

}
