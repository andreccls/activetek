package co.com.activetek.genericmenu.ui.tables;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.ui.OsakiMenu;

import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import layout.TableLayout;

public class MapTablesPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private OsakiMenu window;
    private Table[][] tables;
    private int width;
    private int height;
    TableLayout layout;
    /**
     * This is the default constructor
     */
    public MapTablesPanel( OsakiMenu window, Table[][] tables )
    {
        super( );        
        tables = window.getMatrixTables( );
        width = tables[ 0 ].length;
        height = tables.length;
        this.tables = tables;
        this.setBorder( BorderFactory.createTitledBorder( null, "Mesas", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font( "Dialog", Font.BOLD, 12 ), new Color( 51, 51, 51 ) ) );
        refresh( tables );
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    public void refresh( Table[][] xtables )
    {        
        
        this.tables = xtables ;
        width = tables[ 0 ].length;
        height = tables.length;
        
        System.out.println("width: " +width + " height: " +height);
        
        double[] widthArray = new double[height];
        double[] heightArray = new double[width];
        
        java.util.Arrays.fill(widthArray, 100);
        java.util.Arrays.fill(heightArray, 100);
        layout = new TableLayout();
        layout.setColumn(widthArray);
        layout.setRow(widthArray);
        
        
        this.setLayout( layout );
        
        for( int j = 0; j < width; j++ )// 5
        {
            for( int i = 0; i < height; i++ )// 6
            {
            	GridBagConstraints bgc = new GridBagConstraints();
            	bgc.insets = new Insets( 0, 0, 0, 0 );
            	bgc.fill = GridBagConstraints.BOTH;
            	bgc.gridx = i;
            	bgc.gridy = j;
            	bgc.weightx = 1;
            	bgc.weighty = 1;
            	bgc.gridwidth = 1;
            	bgc.gridheight = 1;
            	
                this.add( new TablePanel( tables[ i ][ j ] ), " "+i+", "+j+"");
                System.out.println(" "+i+", "+j+"");
            }
        }
        this.setBorder( BorderFactory.createTitledBorder( null, "Mesas", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font( "Dialog", Font.BOLD, 12 ), new Color( 51, 51, 51 ) ) );
        this.doLayout();
        this.repaint();
        this.revalidate();
    }
    public void refresh2()
    {
    	layout.insertColumn(height -3 , 100);
    	
    	for(int i = 0 ; i < width; i++)
    	{
    		this.add(new TablePanel(null), (height-3)+","+i);
    	}
    	this.doLayout();
    	this.repaint();
    }
}
