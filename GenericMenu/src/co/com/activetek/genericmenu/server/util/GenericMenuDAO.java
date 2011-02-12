package co.com.activetek.genericmenu.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import co.com.activetek.genericmenu.server.beans.Image;
import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.beans.Waitress;

public class GenericMenuDAO
{

    private static GenericMenuDAO instance;
    private Connection conn;

    public GenericMenuDAO( ) throws SQLException
    {
        conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/genericMenu", "menu", "unem" );
    }

    public static GenericMenuDAO getInstance( ) throws SQLException
    {
        if( instance == null )
        {
            instance = new GenericMenuDAO( );
        }
        return instance;
    }

    public void showDatabses( ) throws SQLException
    {
        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "show databases" );
        while( rs.next( ) )
        {
            System.out.println( rs.getString( 1 ) );
        }

    }

    public Vector<MenuItem> getChildren( MenuItem parent ) throws SQLException
    {
        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "SELECT * FROM menuitem WHERE parentId " + ( parent == null ? "IS NULL" : " = " + parent.getId( ) ) );
        Vector<MenuItem> res = new Vector<MenuItem>( );
        while( rs.next( ) )
        {
            int menuitemid = rs.getInt( "menuItem_id" );
            res.add( new MenuItem( menuitemid, rs.getString( "nombre" ), rs.getString( "description" ), rs.getBoolean( "enable" ), rs.getString( "icon" ), getImages( menuitemid ) , parent) );
        }

        return res;
    }

    public Vector<Image> getImages( int menuitemid ) throws SQLException
    {
        Statement st = conn.createStatement( );        
        ResultSet rs = st.executeQuery( "SELECT * FROM menuitem_image JOIN image USING(image_id) WHERE menuitem_id = " + menuitemid + " order by image_order asc" );     
        Vector<Image> res = new Vector<Image>( );
        while( rs.next( ) )
        {
            res.add( new Image( rs.getInt( "image_id" ), rs.getString( "url" ), rs.getBoolean( "enable" ),rs.getInt( "image_order" ) ) );
        }

        return res;
    }
    public Vector<Waitress> getWaitress() throws SQLException
    {
        Vector<Waitress> result = new Vector<Waitress>( );
        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "SELECT * FROM mesero" );
        while( rs.next( ) )
        {
            result.add( new Waitress( rs.getInt( "id_mesero" ), rs.getString( "foto" ), rs.getString( "nombre" ), rs.getString( "apellido" ), rs.getString( "nick" ), rs.getBoolean( "enable" ) ) );
        }
        return result;
    }
}
