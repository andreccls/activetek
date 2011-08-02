package co.com.activetek.genericmenu.ui;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.UIManager;

import co.com.activetek.genericmenu.server.ActiveMenuServer;
import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.beans.Order;
import co.com.activetek.genericmenu.server.beans.PriceItem;
import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.server.exception.AnotherInstanceException;
import co.com.activetek.genericmenu.server.exception.GenericMenuException;
import co.com.activetek.genericmenu.server.util.Log;
import co.com.activetek.genericmenu.ui.orders.OrdersPanel;
import co.com.activetek.genericmenu.ui.menu.MenuPanel;
import co.com.activetek.genericmenu.ui.tables.TablesPanel;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import co.com.activetek.genericmenu.ui.waitress.WaitressesPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import co.com.activetek.genericmenu.ui.statistics.StatisticsPanel;

public class ActiveMenu extends JFrame
{

    private static final long serialVersionUID = 1L;
    private OrdersPanel ordersPanel = null;
    private MenuPanel menuPanel = null;
    private TablesPanel tablesPanel = null;
    private ActiveMenuServer server;

    /**
     * Atributo que representa el item del menu que esta seleccionado actualmente en la interfaz del usuario
     */
    private MenuItem selected; // @jve:decl-index=0:
    private WaitressesPanel waitressesPanel = null;
    private JSplitPane splitPane = null;
    private JTabbedPane tabbedPane = null;
    private ActiveMenuBar osakiMenuBar = null; // @jve:decl-index=0:visual-constraint=""
    private JSplitPane splitPaneRight = null;
    private JPanel logo = null;
    private JPanel mainPanel = null;
    private StatisticsPanel statisticsPanel;
    /**
     * This method initializes ordersPanel
     * 
     * @return co.com.activetek.genericmenu.ui.orders.OrdersPanel
     */
    private OrdersPanel getOrdersPanel( )
    {
        if( ordersPanel == null )
        {
            ordersPanel = new OrdersPanel( this );
        }
        return ordersPanel;
    }
    private JPanel getMainPanel( )
    {
        if( mainPanel == null )
        {
            mainPanel = new JPanel( );
            mainPanel.setLayout( new BorderLayout( ) );
            mainPanel.add( getLogoPanel( ), BorderLayout.NORTH );
            // mainPanel.add( getSplitPane( ), BorderLayout.CENTER );
            mainPanel.add( getSplitPane( ), BorderLayout.CENTER );
        }
        return mainPanel;
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
    private TablesPanel getMapTablesPanel( )
    {
        if( tablesPanel == null )
        {
            tablesPanel = new TablesPanel( this );
        }
        return tablesPanel;
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
    private JPanel getLogoPanel( )
    {
        if( logo == null )
        {
            logo = new JPanel( );
            logo.setLayout( new BorderLayout( ) );
            JLabel logoLab = new JLabel( );
            ImageIcon ic = new ImageIcon( "./images/GenericMenu/ui/logo.png" );
            logoLab.setIcon( ic );
            logo.add( logoLab );
        }
        return logo;
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
            // --------- ini: modificado el 20110522 para agregar las ordenes al tabbed pane
            // splitPane.setLeftComponent( getOrdersPanel( ) );
            // splitPane.setRightComponent( getSplitPaneRight( ) );
            // ---------end:
            splitPane.setLeftComponent( getTabbedPane( ) );
            splitPane.setRightComponent(getStatisticsPanel());
            // splitPane.setRightComponent( comp );TODO
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
            tabbedPane.addTab( "Ordenes", null, getOrdersPanel( ), null );
            tabbedPane.addTab( "Mesas", null, getMapTablesPanel( ), null );
            tabbedPane.addTab( "Menu", null, getMenuPanel( ), null );
            tabbedPane.addTab( "Meseros", null, getWaitressesPanel( ), null );
        }
        return tabbedPane;
    }

    /**
     * This method initializes osakiMenuBar
     * 
     * @return co.com.activetek.genericmenu.ui.OsakiMenuBar
     */
    private ActiveMenuBar getOsakiMenuBar( )
    {
        if( osakiMenuBar == null )
        {
            osakiMenuBar = new ActiveMenuBar( );
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
            splitPaneRight.setLeftComponent( getTabbedPane( ) );
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
            // UIManager.setLookAndFeel( "ch.randelshofer.quaqua.QuaquaLookAndFeel" );
            // UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" );
            // UIManager.setLookAndFeel( "co.com.activetek.genericmenu.ui.utils.MyLookAndFeel" );
            // MetalLookAndFeel.setCurrentTheme( new MyLookAndFeel( ) );
            // UIManager.setLookAndFeel( "javax.swing.plaf.metal.MetalLookAndFeel" );
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
                ActiveMenu thisClass = new ActiveMenu( );
                thisClass.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                thisClass.setVisible( true );
            }
        } );
    }

    /**
     * This is the default constructor
     */
    public ActiveMenu( )
    {
        super( );
        try
        {
            server = new ActiveMenuServer( this );
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
            System.exit( 0 );
            e.printStackTrace( );
        }
        catch( AnotherInstanceException e )
        {
            JOptionPane.showMessageDialog( this, e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
            System.exit( 0 );
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
        // this.setContentPane( getSplitPane( ) );
        this.setContentPane( getMainPanel( ) );
        this.setExtendedState( getExtendedState( ) | MAXIMIZED_BOTH );
        this.setTitle( "Active Menu" );
        // this.setMenuBar( getOsakiMenuBar( ) );
    }

    public MenuItem getMenuTree( )
    {
        return server.getMenuTree( );
    }

    public Vector<MenuItem> getMenuTreeAsVector( )
    {
        return server.getMenuTreeAsVector( );
    }

    /**
     * Actualiza el panel el atributo selected por el objeto MenuItem que tiene el path dado como parametro Acutualiza el panel que contiene la informacion del producto
     * (imagenes, precios, etc..)
     * @param path
     */
    public void setSelectedItem( String path )
    {
        selected = server.getMenuItemByPath( path );
        menuPanel.updateSelectedItem( selected );
    }
    public void setSelectedItem( MenuItem selected )
    {
        this.selected = selected;
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
            e.printStackTrace( );
        }
        return null;
    }

    public void addMenuImtemImage( File file )
    {
        try
        {
            server.addMenuImtemImage( file, selected );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Error inesperado agregando la nueva imagen, contacte al administrador del sistema \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
        }
    }

    public void deleteMenuItemImage( int image )
    {
        try
        {
            server.deleteMenuItemImage( image, selected );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Error inesperado tratando de eliminar la imagen, contacte al administrador del sistema \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
        }
    }

    public void changeImageItemEnable( int image, boolean isselected )
    {
        try
        {
            server.changeImageItemEnable( image, selected, isselected );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Error inesperado tratando de modificar la imagen, contacte al administrador del sistema \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
        }
    }

    public void notifyDimensionChanged( int width, int height )
    {
        try
        {
            server.notifyDimensionChanged( width, height );
            tablesPanel.refresh( );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Error inesperado tratando de almacenar las nuevas dimensiones \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
        }

    }
    public void notifyOrderReady( )
    {
        ordersPanel.refresh( server.getOrders( ) );
    }
    public void persistPriceItem( PriceItem priceItem )
    {
        try
        {
            server.persistPriceItem( priceItem );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Error inesperado tratando de almacenar el precio \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
        }

    }
    public void deletePriceItem( PriceItem targeted )
    {
        try
        {
            server.deletePriceItem( targeted );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Error inesperado tratando de eliminar el precio \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
        }
    }
    public void closeOrder( Order order )
    {
        try
        {
            server.closeOrder( order );
            statisticsPanel.refresh( );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Ha ocurrido un error inesperado tratando de liberar esta mesa \npor favor contacte al administrador de la aplicacion en http://helpdesk.activetek.co \n" + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
        }
        ordersPanel.refresh( server.getOrders( ) );
    }
    public void cancelOrder( Order order )
    {
        server.cancelOrder( order );
        ordersPanel.refresh( server.getOrders( ) );
    }
    private StatisticsPanel getStatisticsPanel() {
        if (statisticsPanel == null) {
        	statisticsPanel = new StatisticsPanel(this);
        }
        return statisticsPanel;
    }
    public HashMap<Waitress, Integer> getTopWaitress( ) throws SQLException
    {
        return server.getTopWaitress( );
    }
}
