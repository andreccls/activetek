package co.com.activetek.genericmenu.ui.orders;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JScrollPane;

import co.com.activetek.genericmenu.server.beans.Order;

public class OrdersPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private JScrollPane jScrollPane = null;
    private JPanel jPanel = null;
    private JLabel noOrdersLabel;

    /**
     * This is the default constructor
     * @param
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

    private JLabel getNoOrdersLabel( )
    {
        if( noOrdersLabel == null )
        {
            noOrdersLabel = new JLabel( "No hay ordenes por porcesar" );
            noOrdersLabel.setFont( new Font( "Tahoma", Font.ITALIC, 30 ) );
        }
        return noOrdersLabel;
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
            jPanel.setLayout( new BoxLayout( jPanel, BoxLayout.Y_AXIS ) );
            jPanel.add( getNoOrdersLabel( ) );
            // jPanel.setLayout( new GridLayout( 5, 1 ) );
            // for( int i = 0; i < 2; i++ )
            // {
            // jPanel.add( new OrderPanel( ) );
            // }

        }
        return jPanel;
    }

    public void refresh( Vector<Order> orders )
    {
        jPanel.removeAll( );
        if( orders.size( ) == 0 )
        {
            jPanel.add( noOrdersLabel );
        }
        for( Order order : orders )
        {
            jPanel.add( new OrderPanel( order ) );
        }
        this.revalidate( );
        this.repaint( );
    }

}
