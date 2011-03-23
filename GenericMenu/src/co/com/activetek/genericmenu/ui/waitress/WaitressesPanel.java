package co.com.activetek.genericmenu.ui.waitress;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.ui.OsakiMenu;

import java.awt.GridLayout;

public class WaitressesPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private final static int width = 5;
    private final static int height = 5;
    private OsakiMenu window;
    /**
     * This is the default constructor
     */
    public WaitressesPanel( OsakiMenu widow )
    {
        super( );
        this.window = widow;
        initialize( );
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize( )
    {
        GridLayout gridLayout = new GridLayout( HEIGHT, WIDTH );

        for( Waitress waitress : window.getWaitresses( ) )
        {
            this.add( new WaitressPanel( waitress ) );
        }

        this.setLayout( gridLayout );
        this.setSize( 300, 200 );
    }

}
