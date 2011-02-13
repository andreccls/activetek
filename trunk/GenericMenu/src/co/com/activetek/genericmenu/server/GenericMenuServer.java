package co.com.activetek.genericmenu.server;

import java.sql.SQLException;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.server.exception.GenericMenuException;
import co.com.activetek.genericmenu.server.util.GenericMenuDAO;

public class GenericMenuServer
{

    /**
     * MenuItem raiz del arbol del menu. Desde este elemento se tiene acceso a todo el arbol
     */
    private MenuItem root;
    private Vector<Waitress> waitresses;
    private Vector<Table> tables;
    
    public GenericMenuServer( ) throws SQLException, GenericMenuException
    {
        GenericMenuDAO.getInstance( );// para verificar que la base de datos esta arriba
        try
        {
            root = getChildren( null ).get( 0 );
            root.loadSons( );
            waitresses = GenericMenuDAO.getInstance( ).getWaitress( );
            tables = GenericMenuDAO.getInstance( ).getTables( );            
        }
        catch( ArrayIndexOutOfBoundsException e )
        {
            throw new GenericMenuException( "No se ha definido ningun menu en el sistema" );
        }
    }
    public Vector<MenuItem> getChildren( MenuItem parent ) throws SQLException
    {
        return GenericMenuDAO.getInstance( ).getChildren( parent );
    }

    // TODO eliminar esta cosa
    public final static void main( String[] a ) throws SQLException, GenericMenuException
    {
        new GenericMenuServer( ).getJSon( );
    }
    public MenuItem getMenuTree( ) 
    {
        return root;
    }
    public MenuItem getMenuItemByPath( String path )
    {
        String[] items = path.split( "," );
        return root.findByName(items[items.length-1]);
    }
    public Vector<Waitress> getWaitresses( ) throws SQLException
    {
        return waitresses;
    }
    public JSONArray getWaitressesJSON()
    {
        JSONArray array = new JSONArray( );
        for( Waitress waitress : waitresses )
        {
            array.add( waitress.getJSON( ) );
        }
        return array;
    }
    public JSONArray getTablesJSON()
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
        System.out.println(object);
        return object.toString( );
    }
}
