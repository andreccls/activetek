package co.com.activetek.genericmenu.ui;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.TreePath;

import java.awt.Rectangle;

import co.com.activetek.genericmenu.server.GenericMenuServer;
import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.server.exception.GenericMenuException;
import co.com.activetek.genericmenu.ui.orders.OrdersPanel;
import co.com.activetek.genericmenu.ui.menu.MenuTreePanel;
import co.com.activetek.genericmenu.ui.menu.MenuPanel;
import co.com.activetek.genericmenu.ui.tables.MapTablesPanel;
import java.awt.Point;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.Vector;
import co.com.activetek.genericmenu.ui.waitress.WaitressesPanel;

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
    private JInternalFrame internalFrameTables = null;
    private JPanel contentPaneTables = null;
    private MapTablesPanel mapTablesPanel = null;
    private GenericMenuServer server;

    /**
     * Atributo que representa el item del menu que esta seleccionado actualmente en la interfaz del usuario
     */
    private MenuItem selected;
    private JInternalFrame internalFrameWaitresses = null;
    private JPanel contentPaneWaitresses = null;
    private WaitressesPanel waitressesPanel = null;

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
            jDesktopPane.add( getInternalFrameOrders( ), null );
            jDesktopPane.add( getInternalFrameMenu( ), null );
            jDesktopPane.add( getInternalFrameTables( ), null );
            jDesktopPane.add( getInternalFrameWaitresses( ), null );
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
            internalFrameOrders.setBounds( new Rectangle( -1, 1, 209, 744 ) );
            internalFrameOrders.setMaximizable( true );
            internalFrameOrders.setClosable( true );
            internalFrameOrders.setResizable( true );
            internalFrameOrders.setVisible( true );
            internalFrameOrders.setContentPane( getContentPaneOrders( ) );
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
            contentPaneOrders.setLayout( new BorderLayout( ) );
            contentPaneOrders.add( getOrdersPanel( ), BorderLayout.CENTER );
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
            internalFrameMenu.setBounds(new Rectangle(652, 2, 677, 404));
            internalFrameMenu.setResizable( true );
            internalFrameMenu.setMaximizable( true );
            internalFrameMenu.setClosable( true );
            internalFrameMenu.setVisible( true );
            internalFrameMenu.setContentPane( getContentPaneMenu( ) );
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
            contentPaneMenu.setLayout( new BorderLayout( ) );
            contentPaneMenu.add( getMenuPanel( ), BorderLayout.CENTER );
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
            menuPanel = new MenuPanel( this );
        }
        return menuPanel;
    }

    /**
     * This method initializes internalFrameTables
     * 
     * @return javax.swing.JInternalFrame
     */
    private JInternalFrame getInternalFrameTables( )
    {
        if( internalFrameTables == null )
        {
            internalFrameTables = new JInternalFrame( );
            internalFrameTables.setVisible( true );
            internalFrameTables.setResizable( true );
            internalFrameTables.setLocation( new Point( 210, 2 ) );
            internalFrameTables.setSize(new Dimension(442, 460));
            internalFrameTables.setContentPane( getContentPaneTables( ) );
        }
        return internalFrameTables;
    }

    /**
     * This method initializes contentPaneTables
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getContentPaneTables( )
    {
        if( contentPaneTables == null )
        {
            contentPaneTables = new JPanel( );
            contentPaneTables.setLayout( new BorderLayout( ) );
            contentPaneTables.add( getMapTablesPanel( ), BorderLayout.NORTH );
        }
        return contentPaneTables;
    }

    /**
     * This method initializes mapTablesPanel
     * 
     * @return co.com.activetek.genericmenu.ui.tables.MapTablesPanel
     */
    private MapTablesPanel getMapTablesPanel( )
    {
        if( mapTablesPanel == null )
        {
            mapTablesPanel = new MapTablesPanel( );
        }
        return mapTablesPanel;
    }

    /**
     * This method initializes internalFrameWaitresses
     * 
     * @return javax.swing.JInternalFrame
     */
    private JInternalFrame getInternalFrameWaitresses( )
    {
        if( internalFrameWaitresses == null )
        {
            internalFrameWaitresses = new JInternalFrame( );
            internalFrameWaitresses.setContentPane( getContentPaneWaitresses( ) );
            internalFrameWaitresses.setVisible( true );
            internalFrameWaitresses.setResizable( true );
            internalFrameWaitresses.setLocation(new Point(208, 461));
            internalFrameWaitresses.setSize( new Dimension( 597, 257 ) );
        }
        return internalFrameWaitresses;
    }

    /**
     * This method initializes contentPaneWaitresses
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getContentPaneWaitresses( )
    {
        if( contentPaneWaitresses == null )
        {
            contentPaneWaitresses = new JPanel( );
            contentPaneWaitresses.setLayout( new BorderLayout( ) );
            contentPaneWaitresses.add( getWaitressesPanel( ), BorderLayout.NORTH );
        }
        return contentPaneWaitresses;
    }

    /**
     * This method initializes waitressesPanel
     * 
     * @return co.com.activetek.genericmenu.ui.waitress.WaitressesPanel
     */
    private WaitressesPanel getWaitressesPanel( )
    {
        if( waitressesPanel == null )
        {
            waitressesPanel = new WaitressesPanel( this );
        }
        return waitressesPanel;
    }

    /**
     * @param args
     */
    public static void main( String[] args )
    {
        try
        {
            UIManager.setLookAndFeel( "ch.randelshofer.quaqua.QuaquaLookAndFeel" );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
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
        try
        {
            server = new GenericMenuServer( );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Se ha producido un error inesperado tratando conectar a la base de datos, por favor contacte al administrador \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
            System.exit( 0 );
        }
        catch( GenericMenuException e )
        {
            JOptionPane.showMessageDialog( this, e.getMessage( ), "Warning", JOptionPane.WARNING_MESSAGE );
            e.printStackTrace( );
        }
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
        this.setExtendedState( getExtendedState( ) | MAXIMIZED_BOTH );
        this.setContentPane( getJDesktopPane( ) );
        this.setTitle( "Osaki" );
    }

    public Vector<MenuItem> getChildren( int parentId )
    {
        try
        {
            return server.getChildren( parentId );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Error inesperado, contacte al administrador del sistema \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
        }
        return null;
    }

    public MenuItem getMenuTree( )
    {
        return server.getMenuTree( );
    }

    public void setSelectedItem( String path )
    {
        selected = server.getMenuItemByPath( path );
        menuPanel.updateSelectedItem( selected );
    }

    public Vector<Waitress> getWaitresses( )
    {
        try
        {
            return server.getWaitresses( );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Error inesperado recolectando la informacion de meseros desde la base de datos, contacte al administrador del sistema \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
        }
        return null;
    }

}
