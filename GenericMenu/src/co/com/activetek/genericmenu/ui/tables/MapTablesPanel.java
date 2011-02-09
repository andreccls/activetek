package co.com.activetek.genericmenu.ui.tables;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;

public class MapTablesPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    public final static int WIDTH = 5;
    public final static int HEIGHT = 5;

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
        this.setBorder(BorderFactory.createTitledBorder(null, "Tables", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
        GridLayout gridLayout = new GridLayout(HEIGHT,WIDTH);
        this.setLayout(gridLayout);
        for (int i = 0; i < WIDTH; i++)
        {
        	for (int j = 0; j < HEIGHT; j++)
        	{
        		this.add(new TablePanel(j, i));
        	}
        }
    }

}
