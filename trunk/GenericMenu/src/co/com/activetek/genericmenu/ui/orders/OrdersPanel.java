package co.com.activetek.genericmenu.ui.orders;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JScrollPane;

public class OrdersPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private JScrollPane jScrollPane = null;
    private JPanel jPanel = null;

    /**
     * This is the default constructor
     */
    public OrdersPanel( )
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
        GridLayout gridLayout = new GridLayout( );
        this.setLayout( gridLayout );
        this.add( getJScrollPane( ), null );
    }

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane( )
    {
        if( jScrollPane == null )
        {
            jScrollPane = new JScrollPane( );
            jScrollPane.setViewportView( getJPanel( ) );
        }
        return jScrollPane;
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel( )
    {
        if( jPanel == null )
        {
            jPanel = new JPanel( );
            jPanel.setLayout( new GridLayout( 5, 1 ) );
            for( int i = 0; i < 2; i++ )
            {
                jPanel.add( new OrderPanel( ) );
            }

        }
        return jPanel;
    }

}
