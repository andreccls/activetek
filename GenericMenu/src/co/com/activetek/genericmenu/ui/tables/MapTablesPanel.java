package co.com.activetek.genericmenu.ui.tables;

import javax.swing.JPanel;

import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.ui.OsakiMenu;

import java.awt.Graphics;

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
        //this.setBorder( BorderFactory.createTitledBorder( null, "Mesas", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font( "Dialog", Font.BOLD, 12 ), new Color( 51, 51, 51 ) ) );
        init( );
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    public void init(  )
    {           
        
        height = tables[ 0 ].length;
        width = tables.length;
        
        double[] widthArray = new double[width];
        double[] heightArray = new double[height];
        
        java.util.Arrays.fill(widthArray, 0);
        java.util.Arrays.fill(heightArray, 0);
        layout = new TableLayout();
        layout.setColumn(widthArray);
        layout.setRow(widthArray);
        
        
        this.setLayout( layout );
        
        for( int i = 0; i < width; i++ )// 5
        {
            for( int j = 0; j < height; j++ )// 6
            {
                this.add( new TablePanel( tables[ i ][ j ],i ,j , window), " "+i+", "+j+"");             
            }
        }        
        this.doLayout();
        this.repaint();        
    }
    public void refresh(Table[][] xtables)
    {
    	tables = xtables;
    	height = tables[ 0 ].length;
    	width = tables.length;
        
        for( int i = 0; i < width; i++ )// 5
        {
            for( int j = 0; j < height; j++ )// 6
            {
            	
                this.add( new TablePanel( tables[ i ][ j ],i,j, window ), " "+i+", "+j+"");               
            }
        }     
        this.doLayout();
        this.repaint();        
        
    }
    /**
     * Se reimplementa este motodo con el fin de que el panel se ajuse al ancho de la ventana
     */
    public void paintComponent(Graphics g )//TODO hay un bug cuando se quiere cambiar la altura no actualiza correctamente
    {   	
        double[] widthArray = new double[width];
        double[] heightArray = new double[height];
        
        java.util.Arrays.fill(widthArray, this.getWidth()/width);
        java.util.Arrays.fill(heightArray, this.getHeight()/height);
        
        layout.setColumn(widthArray);
        layout.setRow(widthArray);
        
        this.doLayout();
        this.revalidate();
    	super.paintComponent(g);
    }
}
