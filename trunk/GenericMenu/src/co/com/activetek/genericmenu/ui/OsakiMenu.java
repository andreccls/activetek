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
import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.server.exception.GenericMenuException;
import co.com.activetek.genericmenu.server.util.Log;
import co.com.activetek.genericmenu.ui.orders.OrdersPanel;
import co.com.activetek.genericmenu.ui.menu.MenuTreePanel;
import co.com.activetek.genericmenu.ui.menu.MenuPanel;
import co.com.activetek.genericmenu.ui.tables.MapTablesPanel;
import java.awt.Point;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Logger;

import co.com.activetek.genericmenu.ui.waitress.WaitressesPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

public class OsakiMenu extends JFrame
{

    private static final long serialVersionUID = 1L;
    private OrdersPanel ordersPanel = null;
    private MenuPanel menuPanel = null;
    private MapTablesPanel mapTablesPanel = null;
    private GenericMenuServer server;

    /**
     * Atributo que representa el item del menu que esta seleccionado actualmente en la interfaz del usuario
     */
    private MenuItem selected;  //  @jve:decl-index=0:
    private WaitressesPanel waitressesPanel = null;
    private JSplitPane splitPane = null;
    private JTabbedPane tabbedPane = null;
    private OsakiMenuBar osakiMenuBar = null;   //  @jve:decl-index=0:visual-constraint=""
    private JSplitPane splitPaneRight = null;

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
     * This method initializes mapTablesPanel
     * 
     * @return co.com.activetek.genericmenu.ui.tables.MapTablesPanel
     */
    private MapTablesPanel getMapTablesPanel( )
    {
        if( mapTablesPanel == null )
        {
            mapTablesPanel = new MapTablesPanel( this );
        }
        return mapTablesPanel;
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
     * This method initializes splitPane	
     * 	
     * @return javax.swing.JSplitPane	
     */
    private JSplitPane getSplitPane( )
    {
        if( splitPane == null )
        {
            splitPane = new JSplitPane( );
            splitPane.setLeftComponent(getOrdersPanel());
            splitPane.setRightComponent(getSplitPaneRight());
        }
        return splitPane;
    }

    /**
     * This method initializes tabbedPane	
     * 	
     * @return javax.swing.JTabbedPane	
     */
    private JTabbedPane getTabbedPane( )
    {
        if( tabbedPane == null )
        {
            tabbedPane = new JTabbedPane( );
            tabbedPane.addTab("Mesas", null, getMapTablesPanel(), null);
            tabbedPane.addTab("Menu", null, getMenuPanel(), null);
            tabbedPane.addTab("Meseros", null, getWaitressesPanel(), null);
        }
        return tabbedPane;
    }

    /**
     * This method initializes osakiMenuBar	
     * 	
     * @return co.com.activetek.genericmenu.ui.OsakiMenuBar	
     */
    private OsakiMenuBar getOsakiMenuBar( )
    {
        if( osakiMenuBar == null )
        {
            osakiMenuBar = new OsakiMenuBar( );
        }
        return osakiMenuBar;
    }

    /**
     * This method initializes splitPaneRight	
     * 	
     * @return javax.swing.JSplitPane	
     */
    private JSplitPane getSplitPaneRight( )
    {
        if( splitPaneRight == null )
        {
            splitPaneRight = new JSplitPane( );
            splitPaneRight.setLeftComponent(getTabbedPane());
        }
        return splitPaneRight;
    }

    /**
     * @param args
     */
    public static void main( String[] args )
    {
        Log.getInstance( ).getLog( ).info( "Iniciando Menu" );
        try
        {
            //UIManager.setLookAndFeel( "ch.randelshofer.quaqua.QuaquaLookAndFeel" );
             //UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            // UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" );
        }
        catch( Exception e )
        {
            Log.getInstance( ).getLog( ).warning( "No fue posible establecer el look & feel " + e );
            e.printStackTrace( );
        }
        SwingUtilities.invokeLater( new Runnable( )
        {
            public void run( )
            {
                OsakiMenu thisClass = new OsakiMenu( );
                thisClass.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                thisClass.setVisible( true );
            }
        } );
    }

    /**
     * This is the default constructor
     */
    public OsakiMenu( )
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
        this.setContentPane(getSplitPane());
        this.setExtendedState( getExtendedState( ) | MAXIMIZED_BOTH );
        this.setTitle( "Osaki" );
        this.setMenuBar( getOsakiMenuBar( ));
    }

    public MenuItem getMenuTree( )
    {
        return server.getMenuTree( );
    }
    
    public Vector<MenuItem> getMenuTreeAsVector()
    {
        return server.getMenuTreeAsVector();
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
    public MenuItem getSelectedItem( )
    {
        return selected;
    }

    public Table[][] getMatrixTables( )
    {
        try
        {
            return server.getMatrixTables( );
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, "Error inesperado recolectando la informacion de las mesas desde la base de datos, contacte al administrador del sistema \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace();
        }
        return null;
    }

}  //  @jve:decl-index=0:visual-constraint="258,54"
