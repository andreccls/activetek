package co.com.activetek.genericmenu.ui;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.awt.Rectangle;
import co.com.activetek.genericmenu.ui.orders.OrdersPanel;
import co.com.activetek.genericmenu.ui.menu.MenuTreePanel;
import co.com.activetek.genericmenu.ui.menu.MenuPanel;

public class GenericMenu extends JFrame
{

    private static final long serialVersionUID = 1L;
    private JDesktopPane jDesktopPane = null;
    private JInternalFrame internalFrameOrders = null;
    private JPanel contentPaneOrders = null;
    private OrdersPanel ordersPanel = null;
    private JInternalFrame internalFrameMenu = null;
    private JPanel contentPaneMenu = null;
    private MenuPanel menuPanel = null;
    /**
     * This method initializes jDesktopPane	
     * 	
     * @return javax.swing.JDesktopPane	
     */
    private JDesktopPane getJDesktopPane( )
    {
        if( jDesktopPane == null )
        {
            jDesktopPane = new JDesktopPane( );
            jDesktopPane.add(getInternalFrameOrders(), null);
            jDesktopPane.add(getInternalFrameMenu(), null);
        }
        return jDesktopPane;
    }

    /**
     * This method initializes internalFrameOrders	
     * 	
     * @return javax.swing.JInternalFrame	
     */
    private JInternalFrame getInternalFrameOrders( )
    {
        if( internalFrameOrders == null )
        {
            internalFrameOrders = new JInternalFrame( );
            internalFrameOrders.setBounds(new Rectangle(3, 0, 209, 563));
            internalFrameOrders.setMaximizable(true);
            internalFrameOrders.setClosable(true);
            internalFrameOrders.setResizable(true);
            internalFrameOrders.setVisible(true);
            internalFrameOrders.setContentPane(getContentPaneOrders());
        }
        return internalFrameOrders;
    }

    /**
     * This method initializes contentPaneOrders	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getContentPaneOrders( )
    {
        if( contentPaneOrders == null )
        {
            contentPaneOrders = new JPanel( );
            contentPaneOrders.setLayout(new BorderLayout());
            contentPaneOrders.add(getOrdersPanel(), BorderLayout.CENTER);
        }
        return contentPaneOrders;
    }

    /**
     * This method initializes ordersPanel	
     * 	
     * @return co.com.activetek.genericmenu.ui.orders.OrdersPanel	
     */
    private OrdersPanel getOrdersPanel( )
    {
        if( ordersPanel == null )
        {
            ordersPanel = new OrdersPanel( );
        }
        return ordersPanel;
    }

    /**
     * This method initializes internalFrameMenu	
     * 	
     * @return javax.swing.JInternalFrame	
     */
    private JInternalFrame getInternalFrameMenu( )
    {
        if( internalFrameMenu == null )
        {
            internalFrameMenu = new JInternalFrame( );
            internalFrameMenu.setBounds(new Rectangle(213, 0, 677, 404));
            internalFrameMenu.setResizable(true);
            internalFrameMenu.setMaximizable(true);
            internalFrameMenu.setClosable(true);
            internalFrameMenu.setVisible(true);
            internalFrameMenu.setContentPane(getContentPaneMenu());
        }
        return internalFrameMenu;
    }

    /**
     * This method initializes contentPaneMenu	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getContentPaneMenu( )
    {
        if( contentPaneMenu == null )
        {
            contentPaneMenu = new JPanel( );
            contentPaneMenu.setLayout(new BorderLayout());
            contentPaneMenu.add(getMenuPanel(), BorderLayout.CENTER);
        }
        return contentPaneMenu;
    }

    /**
     * This method initializes menuPanel	
     * 	
     * @return co.com.activetek.genericmenu.ui.menu.MenuPanel	
     */
    private MenuPanel getMenuPanel( )
    {
        if( menuPanel == null )
        {
            menuPanel = new MenuPanel( );
        }
        return menuPanel;
    }

    /**
     * @param args
     */
    public static void main( String[] args )
    {
        // TODO Auto-generated method stub

        SwingUtilities.invokeLater( new Runnable( )
        {
            public void run( )
            {
                GenericMenu thisClass = new GenericMenu( );
                thisClass.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                thisClass.setVisible( true );
            }
        } );
    }

    /**
     * This is the default constructor
     */
    public GenericMenu( )
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
        this.setSize( 1000, 1000 );
        this.setContentPane(getJDesktopPane());
        this.setTitle( "JFrame" );
    }

}
