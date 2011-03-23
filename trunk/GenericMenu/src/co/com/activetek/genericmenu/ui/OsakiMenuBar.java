package co.com.activetek.genericmenu.ui;

import java.awt.Menu;
import java.awt.MenuBar;

public class OsakiMenuBar extends MenuBar
{
    private Menu file = new Menu( "File" );
    public OsakiMenuBar( )
    {
        this.add( file );
    }
}
