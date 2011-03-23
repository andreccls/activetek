package co.com.activetek.genericmenu.ui.tables;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.ui.OsakiMenu;

import java.awt.Font;
import java.awt.Color;

public class MapTablesPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private OsakiMenu window;
    private Table[][] tables;
    /**
     * This is the default constructor
     */
    public MapTablesPanel( OsakiMenu window )
    {
        super( );
        this.window = window;
        tables = window.getMatrixTables( );
        initialize( );
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize( )
    {
        this.setBorder( BorderFactory.createTitledBorder( null, "Mesas", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font( "Dialog", Font.BOLD, 12 ), new Color( 51, 51, 51 ) ) );
        GridLayout gridLayout = new GridLayout( tables[ 0 ].length, tables.length );
        this.setLayout( gridLayout );
        for( int j = 0; j < tables[ 0 ].length; j++ )// 5
        {
            for( int i = 0; i < tables.length; i++ )// 6
            {
                // if(tables[i][j] != null)
                // System.out.println("i: " + (i+1) + " j: " + (j+1) + "  "+tables[i][j].getX( )+":"+tables[i][j].getY( ));
                this.add( new TablePanel( tables[ i ][ j ] ) );
            }
        }
    }

}
