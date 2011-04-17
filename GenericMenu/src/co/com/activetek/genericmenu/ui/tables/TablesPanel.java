package co.com.activetek.genericmenu.ui.tables;

import javax.swing.JPanel;

import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.ui.OsakiMenu;
import java.awt.BorderLayout;

public class TablesPanel extends JPanel
{
    private OsakiMenu window;
    MapTablesPanel mapTablesPanel;
    TablesPanelOptions tablesPanelOptions;
    
    public TablesPanel( OsakiMenu window )
    {
        this.window = window;
        setLayout( new BorderLayout( 0, 0 ) );

        
        Table[][] tables = window.getMatrixTables( );
        mapTablesPanel = new MapTablesPanel( window, tables );
        add( mapTablesPanel );

        tablesPanelOptions = new TablesPanelOptions( window, tables.length, tables[0].length );
        add( tablesPanelOptions, BorderLayout.SOUTH );       
     
    }
    
    public void refresh( )
    {
        Table[][] tables = window.getMatrixTables( );
        mapTablesPanel.refresh( tables );
        this.revalidate( );        
    }
    
    
}
