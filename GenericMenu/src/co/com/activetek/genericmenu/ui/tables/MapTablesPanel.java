package co.com.activetek.genericmenu.ui.tables;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

public class MapTablesPanel extends JPanel
{

    private static final long serialVersionUID = 1L;

    /**
     * This is the default constructor
     */
    public MapTablesPanel( )
    {
        super( );
        initialize( );
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize( )
    {
        this.setSize( 300, 200 );
        this.setLayout( new GridBagLayout( ) );
    }

}
