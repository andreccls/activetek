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
    public final static String MAPTABLES_HEIGHT = "mapTables.height";
    public final static String MAPTABLES_WIDTH = "mapTables.width";

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
             MenuItem m  = new MenuItem( menuitemid, rs.getString( "nombre" ), rs.getString( "description" ), rs.getInt( "order" ), rs.getBoolean( "enable" ), rs.getString( "icon" ), getImages( menuitemid ), parent, false ) ;
             m.setPrices( getPrices( m ) );
             res.add(m);
        }

        return res;
    }

    public synchronized Vector<PriceItem> getPrices( MenuItem menuitem ) throws SQLException
    {
        Statement st = conn.createStatement( );
        ResultSet rs = st.executeQuery( "SELECT * FROM priceitem WHERE menuitem_id = " + menuitem.getId( ) + " ORDER BY price_order ASC" );
        Vector<PriceItem> res = new Vector<PriceItem>( );
        while( rs.next( ) )
        {
            res.add( new PriceItem( rs.getInt( "priceitem_id" ), rs.getInt( "cuantity" ), rs.getString( "description" ), rs.getBoolean( "enable" ), rs.getInt( "price_order" ), rs.getLong( "price" ), menuitem ) );
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
        ResultSet rs = st.executeQuery( "SELECT x_key,x_value FROM cfg_menu WHERE x_key IN ('"+MAPTABLES_HEIGHT+"','"+MAPTABLES_WIDTH+"')" );
        
        //default values used if the propertie does not exist
        int x = 5;
        int y = 6;
        
        while(rs.next( ))
        {
            String key = rs.getString( "x_key" );
            if(key.equals( "mapTables.height" ))
            {
                y = rs.getInt( "x_value" );
            }
            else if(key.equals( "mapTables.width" ))
            {
                x = rs.getInt( "x_value" );
            }
        }
        
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
            "("+(p.getId( )<0?"NULL":p.getId( )+"")+","+
            ""+p.getMenuitem( ).getId( )+","+
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
                ResultSet rs = st.executeQuery( "select max(priceitem_id) from priceitem " );
                if( rs.next( ) )
                {
                    p.setId( rs.getInt( 1 ) );
                }
            }            
        }
        else if ( object instanceof Table )
        {
            Table t = (Table)object;
            Statement st = conn.createStatement( );
            sql = "insert into x_table (table_id,number,capacity,x,y,state,enable) values" +
            "( "+(t.getId( )<0?"NULL":t.getId( ))+","+
            ""+t.getNumber( )+","+
            ""+t.getCapacity( )+","+
            ""+(t.getX( )+1)+","+
            ""+(t.getY( )+1)+","+
            "'"+(t.getState( ))+"',"+
            ""+(t.isEnable( )?"1":"0")+""+
            ") on duplicate key update number = values(number),capacity = values(capacity), x = values(x), y = values(y), state = values(state), enable = values(enable)";
            System.out.println(sql);
            st.execute( sql );
            
            if(t.getId( ) < 0)
            {
                ResultSet rs = st.executeQuery( "select max(table_id) from x_table " );
                if( rs.next( ) )
                {
                    t.setId( rs.getInt( 1 ) );
                }
            }
        }
    }

    public void delete( Object object ) throws SQLException
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
        else if(object instanceof PriceItem)
        {
            PriceItem p = (PriceItem)object;
            Statement st = conn.createStatement( );
            sql = "delete from priceitem where priceitem_id = " + p.getId( );
            st.executeUpdate( sql );
        }
        else if(object instanceof Table)
        {
            Table t = (Table)object;
            Statement st = conn.createStatement( );
            sql = "delete from x_table where table_id = " + t.getId( );
            st.executeUpdate( sql );
        }
    }

    public void notifyDimensionChanged( int width, int height ) throws SQLException
    {
        Statement st = conn.createStatement( );
        String sql = "update cfg_menu set x_value = " + width + " where x_key = '"+MAPTABLES_WIDTH + "'";
        st.executeUpdate( sql );
        
        sql = "update cfg_menu set x_value = " + height + " where x_key = '"+MAPTABLES_HEIGHT + "'";
        st.executeUpdate( sql );
    }
    
    

}
