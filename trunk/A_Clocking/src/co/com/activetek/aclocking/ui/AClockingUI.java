package co.com.activetek.aclocking.ui;

import javax.swing.JFrame;

import co.com.activetek.aclocking.world.AClock;
import co.com.activetek.aclocking.world.Employee;
import co.com.activetek.aclocking.world.Schedule;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.Panel;
import java.awt.Window;

import javax.swing.JButton;
import java.awt.Label;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import java.util.ArrayList;

public class AClockingUI extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private AClock aclock;

    public AClockingUI( )
    {
        setTitle( "Control de empleados" );
        this.aclock = new AClock( );
        this.setSize( 548, 330 );
        getContentPane( ).setLayout( new MigLayout( "", "[grow][grow]", "[grow][grow]" ) );

        PanelEmployees panelEmployees = new PanelEmployees( this );
        getContentPane( ).add( panelEmployees, "cell 0 0,grow" );

        PanelShedules panelShedules = new PanelShedules( this );
        getContentPane( ).add( panelShedules, "cell 1 0,grow" );

        JPanel panel = new JPanel( );
        getContentPane( ).add( panel, "cell 0 1 2 1,grow" );

        JButton btnSalir = new JButton( "Salir" );
        panel.add( btnSalir );

        JButton btnGenerarReporte = new JButton( "Generar Reporte" );
        panel.add( btnGenerarReporte );

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
        AClockingUI window = new AClockingUI( );
        LogInDialog dialog = new LogInDialog( window );
        dialog.setVisible( true );
    }
}
