package co.com.activetek.genericmenu.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import co.com.activetek.genericmenu.server.beans.Image;
import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.beans.PriceItem;
import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.server.exception.AnotherInstanceException;
import co.com.activetek.genericmenu.server.exception.GenericMenuException;
import co.com.activetek.genericmenu.server.util.FileUtil;
import co.com.activetek.genericmenu.server.util.GenericMenuDAO;

public class GenericMenuServer
{
    public final static String IMAGES_PATH = "./images/menu/";

    public final static String PROPERTIES = "./OsakiMenu.properties";

    /**
     * MenuItem raiz del arbol del menu. Desde este elemento se tiene acceso a todo el arbol
     */
    private MenuItem root;
    private Vector<Waitress> waitresses;
    private Vector<Table> tables;
    private ListenerThread listener;

    public GenericMenuServer( ) throws SQLException, GenericMenuException, AnotherInstanceException
    {
        GenericMenuDAO.getInstance( );// para verificar que la base de datos esta arriba
        try
        {
            root = getChildren( null ).get( 0 );
            root.loadSons( );
            waitresses = GenericMenuDAO.getInstance( ).getWaitress( );
            tables = GenericMenuDAO.getInstance( ).getTables( );

            Properties prop = new Properties( );
            FileInputStream in = new FileInputStream( PROPERTIES );
            prop.load( in );
            in.close( );

            writeJson( );

            try
            {
                listener = new ListenerThread( this, Integer.parseInt( prop.getProperty( "osaki.server.port" ) ) );
            }
            catch( IOException e )
            {
                throw new AnotherInstanceException( "Error iniciando el servidor, revise que no halla otra instancia corriendo \n" + e.getMessage( ) );
            }
            listener.start( );
        }
        catch( ArrayIndexOutOfBoundsException e )
        {
            throw new GenericMenuException( "No se ha definido ningun menu en el sistema \n" + e.getMessage( ) );
        }
        catch( FileNotFoundException e )
        {
            throw new GenericMenuException( "No se encuentra el archivo de propiedades \n contacte al administrador del sistema \n" + e.getMessage( ) );
        }
        catch( IOException e )
        {
            e.printStackTrace( );
            throw new GenericMenuException( "Error inesperado cargando el archivo de propiedades \n" + e.getMessage( ) );
        }
    }
    public Vector<MenuItem> getChildren( MenuItem parent ) throws SQLException
    {
        return GenericMenuDAO.getInstance( ).getChildren( parent );
    }

    public MenuItem getMenuTree( )
    {
        return root;
    }
    public MenuItem getMenuItemByPath( String path )
    {
        String[] items = path.split( "," );
        return root.findByName( items[ items.length - 1 ] );
    }
    public MenuItem findMenuItemById( int id )
    {
        return root.findById( id );
    }
    public Vector<Waitress> getWaitresses( ) throws SQLException
    {
        return waitresses;
    }
    public JSONArray getWaitressesJSON( )
    {
        JSONArray array = new JSONArray( );
        for( Waitress waitress : waitresses )
        {
            array.add( waitress.getJSON( ) );
        }
        return array;
    }
    public JSONArray getTablesJSON( )
    {
        JSONArray array = new JSONArray( );
        for( Table table : tables )
        {
            array.add( table.getJSON( ) );
        }
        return array;
    }
    public String getJSon( )
    {
        JSONObject object = root.getJSON( );
        object.put( "meseros", getWaitressesJSON( ) );
        object.put( "mesas", getTablesJSON( ) );
        System.out.println( object );
        System.out.println( getTablesJSON( ) );
        return object.toString( );
    }
    public Table[][] getMatrixTables( ) throws SQLException
    {
        return GenericMenuDAO.getInstance( ).getMatrixTables( );
    }
    public Vector<MenuItem> getMenuTreeAsVector( )
    {
        return null;
    }
    public void addMenuImtemImage( File file, MenuItem selected ) throws SQLException
    {
        FileUtil.copyfile( file.getAbsolutePath( ), IMAGES_PATH + file.getName( ) );
        Image image = new Image( -1, IMAGES_PATH + file.getName( ), true, -1, selected.getId( ) );
        selected.getImages( ).add( image );
        GenericMenuDAO.getInstance( ).CRUD( image );
    }
    public void deleteMenuItemImage( int image, MenuItem selected ) throws SQLException
    {
        selected.deleteMenuItemImage( image );
    }

    public void changeImageItemEnable( int image, MenuItem selected, boolean enable ) throws SQLException
    {
        selected.changeImageItemEnable( image, enable );
    }
    public void notifyDimensionChanged( int width, int height ) throws SQLException
    {
        GenericMenuDAO.getInstance( ).notifyDimensionChanged( width, height );
    }
    public void writeJson( ) throws FileNotFoundException
    {
        PrintWriter p = new PrintWriter( new File( "./images/json.txt" ) );
        p.println( getJSon( ) );
        p.close( );
    }
    public Table getTablebyNumber( int number )
    {
        for( Table table : tables )
        {
            if( table.getNumber( ) == number )
                return table;
        }
        return null;
    }
    public Waitress getWaitressById( int id )
    {
        for( Waitress waitress : waitresses )
        {
            if( waitress.getId( ) == id )
                return waitress;
        }
        return null;
    }
    public PriceItem getPriceItemById( int id )
    {
        return root.findPriceItemById( id );
    }
}
