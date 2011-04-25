package co.com.activetek.genericmenu.ui;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OsakiMenuBar extends MenuBar implements ActionListener
{
    private final static String ADD_WAITER = "Agregar Mesero";

    private Menu file = new Menu( "File" );
    private MenuItem addWaiter = new MenuItem( "Agregar Mesero" );

    public OsakiMenuBar( )
    {
        file.add( addWaiter );
        this.add( file );
    }

    public void actionPerformed( ActionEvent arg0 )
    {
        String command = arg0.getActionCommand( );
        if( command.equals( ADD_WAITER ) )
        {

        }

    }
}
