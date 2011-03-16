package co.com.activetek.genericmenu.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import co.com.activetek.genericmenu.server.beans.Image;
import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.beans.PriceItem;
import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.ui.tables.MapTablesPanel;

public class GenericMenuDAO
{

    private static GenericMenuDAO instance;
    private Connection conn;

    public GenericMenuDAO( ) throws SQLException
    {
        conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/genericMenu", "menu", "unem" );
        Log.getInstance( ).getLog( ).info( "Establecida conexion con la base de datos" );
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
            res.add( new MenuItem( menuitemid, rs.getString( "nombre" ), rs.getString( "description" ), rs.getBoolean( "enable" ), rs.getString( "icon" ), getImages( menuitemid ), parent, getPrices( menuitemid ) ) );
        }

        return res;
    }

    public Vector<PriceItem> getPrices( int menuitemid ) throws SQLException
    {
        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "SELECT * FROM priceitem WHERE menuitem_id = " + menuitemid + " ORDER BY price_order ASC" );
        Vector<PriceItem> res = new Vector<PriceItem>( );
        while( rs.next( ) )
        {
            res.add( new PriceItem( rs.getInt( "priceitem_id" ), rs.getInt( "cuantity" ), rs.getString( "description" ), rs.getBoolean( "enable" ), rs.getInt( "price_order" ), rs.getLong( "price" ) ) );
        }

        return res;
    }

    public Vector<Image> getImages( int menuitemid ) throws SQLException
    {
        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "SELECT * FROM image WHERE menuitem_id = " + menuitemid + " order by image_order asc" );
        Vector<Image> res = new Vector<Image>( );
        while( rs.next( ) )
        {
            res.add( new Image( rs.getInt( "image_id" ), rs.getString( "url" ), rs.getBoolean( "enable" ), rs.getInt( "image_order" ) ) );
        }

        return res;
    }
    public Vector<Waitress> getWaitress( ) throws SQLException
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
    public Vector<Table> getTables( ) throws SQLException
    {
        Vector<Table> result = new Vector<Table>( );
        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "SELECT * FROM x_table" );
        while( rs.next( ) )
        {
            result.add( new Table( rs.getInt( "table_id" ), rs.getInt( "number" ), rs.getInt( "capacity" ), rs.getInt( "x" ), rs.getInt( "y" ), rs.getString( "state" ), rs.getBoolean( "enable" ) ) );
        }
        return result;
    }
    /**
     * Retorna un map con las mesas en donde la llave es de la forma <x>:<y>
     * @return
     * @throws SQLException
     */
    public Map<String, Table> getMapTables( ) throws SQLException
    {
        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "SELECT * from x_table" );
        Map<String, Table> result = new HashMap<String, Table>( );
        while( rs.next( ) )
        {
            result.put( rs.getInt( "x" ) + ":" + rs.getInt( "y" ), new Table( rs.getInt( "table_id" ), rs.getInt( "number" ), rs.getInt( "capacity" ), rs.getInt( "x" ), rs.getInt( "y" ), rs.getString( "state" ), rs.getBoolean( "enable" ) ) );
        }
        return result;
    }
    /**
     * Reportana una matriz con las mesas, las coordenadas en la que no se encuentre la mesa es null
     * @return
     * @throws SQLException
     */
    public Table[][] getMatrixTables( ) throws SQLException
    {
        Map<String, Table> map = getMapTables( );

        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "SELECT max(x) as x,max(y) as y from x_table" );
        if( rs.next( ) )
        {
            int x = rs.getInt( "x" );
            int y = rs.getInt( "y" );
            Table[][] tables = new Table[x][y];
            for( int i = 0; i < x; i++ )
            {
                for( int j = 0; j < y; j++ )
                {
                    tables[ i ][ j ] = map.get( ( i + 1 ) + ":" + ( j + 1 ) );
                }
            }
            return tables;
        }
        else
            return new Table[0][0];

    }

}
