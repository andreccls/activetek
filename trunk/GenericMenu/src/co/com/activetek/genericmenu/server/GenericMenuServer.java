package co.com.activetek.genericmenu.server;

import java.sql.SQLException;
import java.util.Vector;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.exception.GenericMenuException;
import co.com.activetek.genericmenu.server.util.GenericMenuDAO;

public class GenericMenuServer
{

    /**
     * MenuItem raiz del arbol del menu. Desde este elemento se tiene acceso a todo el arbol
     */
    private MenuItem root;
    
    public GenericMenuServer( ) throws SQLException, GenericMenuException
    {
        GenericMenuDAO.getInstance( );// para verificar que la base de datos esta arriba
        try
        {
            root = getChildren( -1 ).get( 0 );
            root.loadSons( );         
        }
        catch( ArrayIndexOutOfBoundsException e )
        {
            throw new GenericMenuException( "No se ha definido ningun menu en el sistema" );
        }
    }
    public Vector<MenuItem> getChildren( int idParent ) throws SQLException
    {
        return GenericMenuDAO.getInstance( ).getChildren( idParent );
    }

    // TODO eliminar esta cosa
    public final static void main( String[] a ) throws SQLException
    {
        GenericMenuDAO.getInstance( ).showDatabses( );
    }
    public MenuItem getMenuTree( ) throws SQLException, GenericMenuException
    {
        return root;
    }
    public MenuItem getMenuItemByPath( String path )
    {
        String[] items = path.split( "," );
        return root.findByName(items[items.length-1]);
    }
}
