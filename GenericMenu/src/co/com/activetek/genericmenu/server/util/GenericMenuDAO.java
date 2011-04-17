package co.com.activetek.genericmenu.server.util;

import java.awt.Menu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    public synchronized void showDatabses( ) throws SQLException
    {
        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "show databases" );
        while( rs.next( ) )
        {
            System.out.println( rs.getString( 1 ) );
        }

    }

    public synchronized Vector<MenuItem> getChildren( MenuItem parent ) throws SQLException
    {
        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "SELECT * FROM menuitem WHERE parentId " + ( parent == null ? "IS NULL" : " = " + parent.getId( ) ) );
        Vector<MenuItem> res = new Vector<MenuItem>( );
        while( rs.next( ) )
        {
            int menuitemid = rs.getInt( "menuItem_id" );
            res.add( new MenuItem( menuitemid, rs.getString( "nombre" ), rs.getString( "description" ), rs.getInt( "order" ), rs.getBoolean( "enable" ), rs.getString( "icon" ), getImages( menuitemid ), parent, getPrices( menuitemid ),false ) );
        }

        return res;
    }

    public synchronized Vector<PriceItem> getPrices( int menuitemid ) throws SQLException
    {
        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "SELECT * FROM priceitem WHERE menuitem_id = " + menuitemid + " ORDER BY price_order ASC" );
        Vector<PriceItem> res = new Vector<PriceItem>( );
        while( rs.next( ) )
        {
            res.add( new PriceItem( rs.getInt( "priceitem_id" ), rs.getInt( "cuantity" ), rs.getString( "description" ), rs.getBoolean( "enable" ), rs.getInt( "price_order" ), rs.getLong( "price" ), menuitemid ) );
        }

        return res;
    }

    public synchronized Vector<Image> getImages( int menuitemid ) throws SQLException
    {
        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "SELECT * FROM image WHERE menuitem_id = " + menuitemid + " order by image_order asc" );
        Vector<Image> res = new Vector<Image>( );
        while( rs.next( ) )
        {
            res.add( new Image( rs.getInt( "image_id" ), rs.getString( "url" ), rs.getBoolean( "enable" ), rs.getInt( "image_order" ), rs.getInt( "menuitem_id" ) ) );
        }

        return res;
    }
    public synchronized Vector<Waitress> getWaitress( ) throws SQLException
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
    public synchronized Vector<Table> getTables( ) throws SQLException
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
    public synchronized Map<String, Table> getMapTables( ) throws SQLException
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
    public synchronized Table[][] getMatrixTables( ) throws SQLException
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

    public void CRUD( Object object ) throws SQLException
    {
        String sql = "";
        if( object instanceof MenuItem )
        {
            MenuItem m = (MenuItem)object;
            Statement st = conn.createStatement( );            
            sql =  "insert into menuitem (menuitem_id,nombre,description,parentId,`order`,icon,enable) values " +
            "(" +( m.getId( ) < 0 ? "NULL" : m.getId( ) )+","+
            "" + ( m.getName( ) == null ? "NULL" :"'"+ m.getName( ) +"'" ) +","+
            "" + ( m.getDescription( ) == null ? "NULL" :"'"+ m.getDescription( ) +"'" ) +","+
            "" + m.getParent( ).getId( )+","+
            "" + m.getOrder( )+","+
            "" + ( m.getIcon( ) == null ? "NULL" :"'"+ m.getIcon( ) +"'" ) +","+            
            "" + (m.isEnable( ) ? "1" : "0")+                   
            ") on duplicate key update nombre = values(nombre),description = values(description) , parentId = values(parentId), `order` = values(`order`), icon = values(icon), enable = values(enable) ";            
            st.execute( sql );            
            
            if(m.getId( ) < 0)
            {
                st = conn.createStatement( );
                ResultSet rs = st.executeQuery( "select menuitem_id from menuitem where nombre = '" + m.getName( ) + "'" );
                if( rs.next( ) )
                {
                    m.setId( rs.getInt( 1 ) );
                }
                
            }
            System.out.println(sql);
        }
        else if( object instanceof Image )
        {
            Image i = (Image)object;
            Statement st = conn.createStatement( );
            sql = "insert into image (image_id,url,enable,menuitem_id,image_order) values "+
            " ("+(i.getId( ) < 0 ? "NULL" : i.getId( ))+","+
            "'"+i.getUrl( )+"',"+
            (i.isEnable( )?1:0)+","+
            i.getMenuItemId( )+","+
            1+""+//TODO colocar el orden que debe ir 
            ") on duplicate key update url = values(url), enable = values(enable), image_order = values(image_order)";            
            st.execute( sql );
            
            if(i.getId( ) < 0)
            {
                st = conn.createStatement( );
                ResultSet rs = st.executeQuery( "select image_id from image where url = '" + i.getUrl( ) + "'" );
                if( rs.next( ) )
                {
                    i.setId( rs.getInt( 1 ) );
                }
                
            }
            System.out.println(sql);
        }
        else if (object instanceof PriceItem )
        {
            PriceItem p = (PriceItem)object;
            Statement st = conn.createStatement( );
            sql = "insert into priceitem (priceitem_id,menuitem_id,cuantity,price,description,price_order,enable) values" +
            "("+p.getId( )+","+
            ""+p.getMenuitemId( )+","+
            ""+(p.getCuantity( )<0?"NULL":p.getCuantity( )+"")+","+
            ""+(p.getPrice( )<0?"NULL":p.getPrice( )+"")+","+
            "'"+(p.getDescripcion( ) == null ?"NULL":p.getDescripcion( )+"'")+","+
            ""+p.getOrder( )+","+
            ""+(p.isEnable( )?"1":"0")+""+            
            ") on duplicate key update menuitem_id = values(menuitem_id), cuantity = values (cuantity), price = values (price), description  = values (description), price_order = values (price_order), enable = values(enable) ";            
            System.out.println(sql);
            st.execute( sql );
            
            if(p.getId( )<0)
            {
                ResultSet rs = st.executeQuery( "select max(priceitem_id) from menuitem " );
                if( rs.next( ) )
                {
                    p.setId( rs.getInt( 1 ) );
                }
            }
            
        }
    }

    public void suprimir( Object object ) throws SQLException
    {
        String sql = "";
        if( object instanceof MenuItem )
        {
            MenuItem m = (MenuItem)object;
            Statement st = conn.createStatement( );            
            sql =  "delete from menuitem where menuitem_id = " + m.getId( );            
            st.executeUpdate( sql );          
        }
        else if(object instanceof Image)
        {
            Image i = (Image)object;
            Statement st = conn.createStatement( );            
            sql =  "delete from image where image_id = " + i.getId( );            
            st.executeUpdate( sql );
        }
            
    }

}
