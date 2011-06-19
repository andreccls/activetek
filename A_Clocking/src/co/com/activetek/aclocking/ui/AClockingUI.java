package co.com.activetek.aclocking.ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;
import co.com.activetek.aclocking.entitybeans.Employee;
import co.com.activetek.aclocking.entitybeans.Schedule;
import co.com.activetek.aclocking.ui.employee.DialogAddEditEmployee;
import co.com.activetek.aclocking.ui.employee.PanelEmployees;
import co.com.activetek.aclocking.ui.schedule.DialogAddEditSchedule;
import co.com.activetek.aclocking.ui.schedule.PanelShedules;
import co.com.activetek.aclocking.world.AClock;
import co.com.activetek.aclocking.world.VerificationThread;

public class AClockingUI extends JFrame implements ActionListener
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private AClock aclock;
    private PanelEmployees panelEmployees;
    private PanelShedules panelShedules;
    private VerificationThread v;
    private TrayIcon trayIcon;
    private static LogInDialog dialog;

    public AClockingUI( )
    {
        setTitle( "Control de empleados" );
        try
        {
            this.aclock = new AClock( );
        }
        catch( Exception e1 )
        {
            JOptionPane.showMessageDialog( this, "Contacte al administrador de la aplicacion \n" + e1.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e1.printStackTrace( );
            System.exit( 0 );
        }
        this.setSize( 548, 330 );
        getContentPane( ).setLayout( new MigLayout( "", "[grow][grow]", "[grow][grow]" ) );

        panelEmployees = new PanelEmployees( this );
        getContentPane( ).add( panelEmployees, "cell 0 0,grow" );

        panelShedules = new PanelShedules( this );
        getContentPane( ).add( panelShedules, "cell 1 0,grow" );

        JPanel panel = new JPanel( );
        getContentPane( ).add( panel, "cell 0 1 2 1,grow" );
        panel.setLayout( new MigLayout( "", "[174px][53px][113px][][][]", "[61px]" ) );

        JLabel labelLogo = new JLabel( "" );
        labelLogo.setIcon( new ImageIcon( "images/logo4 copia.JPG" ) );
        panel.add( labelLogo, "cell 0 0,alignx left,aligny top" );

        JButton btnGenerarReporte = new JButton( "Generar Reporte" );
        btnGenerarReporte.setActionCommand( "TEST" );
        btnGenerarReporte.addActionListener( this );
        panel.add( btnGenerarReporte, "cell 5 0,alignx left,aligny center" );

        JButton btnSalir = new JButton( "Salir" );
        btnSalir.setActionCommand( "EXIT" );
        btnSalir.addActionListener( this );
        panel.add( btnSalir );

        if( SystemTray.isSupported( ) )
        {
            SystemTray tray = SystemTray.getSystemTray( );
            Image image = Toolkit.getDefaultToolkit( ).getImage( "images/tray.jpg" );

            ActionListener exitListener = new ActionListener( )
            {
                public void actionPerformed( ActionEvent e )
                {
                    System.out.println( "Exiting..." );
                    System.exit( 0 );
                }
            };

            PopupMenu popup = new PopupMenu( );
            MenuItem defaultItem = new MenuItem( "Exit" );
            defaultItem.addActionListener( exitListener );
            popup.add( defaultItem );

            trayIcon = new TrayIcon( image, "Tray Demo", popup );

            ActionListener actionListener = new ActionListener( )
            {
                public void actionPerformed( ActionEvent e )
                {
                    /**
                     * trayIcon.displayMessage("Action Event", "An Action Event Has Been Performed!", TrayIcon.MessageType.INFO);
                     */
                    // AClockingUI.this.setVisible( true );
                    dialog.setVisible( true );
                }
            };

            trayIcon.setImageAutoSize( true );
            trayIcon.addActionListener( actionListener );
            try
            {
                tray.add( trayIcon );
            }
            catch( AWTException e )
            {
                System.err.println( "TrayIcon could not be added." );
            }
        }

        v = new VerificationThread( aclock.getEmployees( ), trayIcon );
        v.start( );

    }
    public ArrayList<Employee> getEmployees( )
    {
        return aclock.getEmployees( );
    }
    public ArrayList<Schedule> getSchedules( )
    {
        return aclock.getSchedules( );
    }
    public final static void main( String[] arg )
    {
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
            e.printStackTrace( );
        }
        AClockingUI window = new AClockingUI( );
        dialog = new LogInDialog( window );
        dialog.setVisible( true );
    }
    public void showAddEmployeeDialog( )
    {
        DialogAddEditEmployee dialog = new DialogAddEditEmployee( this, null );
        dialog.setVisible( true );
    }
    public void showEditEmployeeDialog( Employee employee )
    {
        DialogAddEditEmployee dialog = new DialogAddEditEmployee( this, employee );
        dialog.setVisible( true );
    }
    public void deleteEmployee( Employee employee )
    {
        try
        {
            aclock.deleteEmployee( employee );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Error inesperado eliminando al empleado\n" + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace();
        }
        panelEmployees.refresh( );
    }
    public void editCreateEmployee( Employee employee )
    {
        try
        {
            aclock.editCreateEmployee( employee );
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( this, "Error inesperado agregando al nuevo empleado\n" + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
        }
        panelEmployees.refresh( );
    }
    public void showAddScheduleDialog( )
    {
        DialogAddEditSchedule dialog = new DialogAddEditSchedule( this, null );
        dialog.setVisible( true );
    }
    public void showEditScheduleDialog( Schedule schedule )
    {
        DialogAddEditSchedule dialog = new DialogAddEditSchedule( this, schedule );
        dialog.setVisible( true );
    }
    public void deleteSchedule( Schedule schedule )
    {
        aclock.deleteSchedule( schedule );
        panelShedules.refresh( );
    }
    public void editCreateSchedule( Schedule schedule )
    {
        // TODO Auto-generated method stub

    }
    @Override
    public void actionPerformed( ActionEvent arg0 )
    {
        if( arg0.getActionCommand( ).equals( "TEST" ) )
        {

        }
        else if( arg0.getActionCommand( ).equals( "EXIT" ) )
        {
            this.setVisible( false );
            trayIcon.displayMessage( "Active Clocking", "Active Clocking sigue ejecutándose", TrayIcon.MessageType.INFO );
        }
    }
}
