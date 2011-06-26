package co.com.activetek.aclocking.ui.schedule;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import co.com.activetek.aclocking.entitybeans.Schedule;
import co.com.activetek.aclocking.ui.AClockingUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class DialogAddEditSchedule extends JDialog implements ActionListener
{
    private String[] daysOfWeek = { "lunes", "martes", "miercoles", "jueves", "viernes", "sabado", "domingo", "festivo" };
    private PanelDaySchedule[] daysPanels;
    private HashMap<String, PanelDaySchedule> panels;
    private HashMap<String, String> ini_hour;
    private HashMap<String, String> end_hour;
    private HashMap<String, Boolean> enable;
    private JTextField txtNombre;
    private Schedule schedule;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private AClockingUI window;
    private boolean isNewSchedule;

    public DialogAddEditSchedule( AClockingUI aClockingUI, Schedule schedule )
    {
        this.schedule = schedule;
        isNewSchedule = schedule == null;
        this.window = aClockingUI;
        if( isNewSchedule )
            setTitle( "Agregar horario" );
        else
            setTitle( "Editar horario" );
        this.setSize( 608, 228 );
        getContentPane( ).setLayout( new MigLayout( "", "[591px,grow]", "[grow][1px][33px]" ) );

        JPanel panel_2 = new JPanel( );
        getContentPane( ).add( panel_2, "cell 0 0,grow" );
        panel_2.setLayout( new MigLayout( "", "[36px][86px]", "[20px]" ) );

        JLabel lblNombre = new JLabel( "Nombre" );
        panel_2.add( lblNombre, "cell 0 0,alignx left,aligny center" );

        txtNombre = new JTextField( );
        if( !isNewSchedule )
            txtNombre.setText( schedule.getScheduleName( ) );
        panel_2.add( txtNombre, "cell 1 0,alignx left,aligny top" );
        txtNombre.setColumns( 10 );

        JPanel panel = new JPanel( );
        panel.setBorder( new LineBorder( new Color( 0, 0, 0 ) ) );
        getContentPane( ).add( panel, "cell 0 1,growx,aligny top" );
        panel.setLayout( new GridLayout( 0, 8, 0, 0 ) );

        daysPanels = new PanelDaySchedule[daysOfWeek.length];
        panels = new HashMap<String, PanelDaySchedule>( );
        ini_hour = new HashMap<String, String>( );
        end_hour = new HashMap<String, String>( );
        enable = new HashMap<String, Boolean>( );
        int i = 0;
        for( String day : daysOfWeek )
        {
            PanelDaySchedule p = new PanelDaySchedule( day, this, schedule );
            panel.add( p );
            panels.put( day, p );
            daysPanels[ i ] = p;
            i++;
        }

        JPanel panel_1 = new JPanel( );
        getContentPane( ).add( panel_1, "cell 0 2,growx,aligny top" );

        btnAceptar = new JButton( "Aceptar" );
        btnAceptar.addActionListener( this );
        btnAceptar.setActionCommand( "ACEPT" );
        panel_1.add( btnAceptar );

        btnCancelar = new JButton( "Cancelar" );
        btnCancelar.addActionListener( this );
        btnCancelar.setActionCommand( "CANCEL" );
        panel_1.add( btnCancelar );

    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );
        if( command.equals( "ACEPT" ) )
        {
            if( txtNombre == null || txtNombre.getText( ).trim( ).equals( "" ) )
            {
                JOptionPane.showMessageDialog( window, "Ingrese un nombre para el horario", "Erorr", JOptionPane.ERROR_MESSAGE );
            }
            else
            {
                if( dataOk( ) )
                {
                    if( isNewSchedule )
                    {
                        schedule = new Schedule( -1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, false, false, false, false, false, false, false );
                    }
                    schedule.setScheduleName( txtNombre.getText( ).trim( ) );

                    schedule.setLunes( panels.get( "lunes" ).getIniHour( ) );
                    schedule.setLunes_out( panels.get( "lunes" ).getEndHour( ) );
                    schedule.setLunes( panels.get( "lunes" ).isSelected( ) );

                    schedule.setMartes( panels.get( "martes" ).getIniHour( ) );
                    schedule.setMartes_out( panels.get( "martes" ).getEndHour( ) );
                    schedule.setMartes( panels.get( "martes" ).isSelected( ) );

                    schedule.setMiercoles( panels.get( "miercoles" ).getIniHour( ) );
                    schedule.setMiercoles_out( panels.get( "miercoles" ).getEndHour( ) );
                    schedule.setMiercoles( panels.get( "miercoles" ).isSelected( ) );

                    schedule.setJueves( panels.get( "jueves" ).getIniHour( ) );
                    schedule.setJueves_out( panels.get( "jueves" ).getEndHour( ) );
                    schedule.setJueves( panels.get( "jueves" ).isSelected( ) );

                    schedule.setViernes( panels.get( "viernes" ).getIniHour( ) );
                    schedule.setViernes_out( panels.get( "viernes" ).getEndHour( ) );
                    schedule.setViernes( panels.get( "viernes" ).isSelected( ) );

                    schedule.setSabado( panels.get( "sabado" ).getIniHour( ) );
                    schedule.setSabado_out( panels.get( "sabado" ).getEndHour( ) );
                    schedule.setSabado( panels.get( "sabado" ).isSelected( ) );

                    schedule.setDomingo( panels.get( "domingo" ).getIniHour( ) );
                    schedule.setDomingo_out( panels.get( "domingo" ).getEndHour( ) );
                    schedule.setDomingo( panels.get( "domingo" ).isSelected( ) );

                    schedule.setFestivo( panels.get( "festivo" ).getIniHour( ) );
                    schedule.setFestivo_out( panels.get( "festivo" ).getEndHour( ) );
                    schedule.setFestivo( panels.get( "festivo" ).isSelected( ) );

                    window.editCreateSchedule( schedule );
                    super.dispose( );
                }
                else
                {
                    JOptionPane.showMessageDialog( window, "Ingrese una hora valida para cada uno de los dias", "Erorr", JOptionPane.ERROR_MESSAGE );
                }
            }            
        }
        else if( command.equals( "CANCEL" ) )
        {
            this.dispose( );
        }
    }
    public boolean dataOk( )
    {
        boolean ok = true;
        for( PanelDaySchedule panelDaySchedule : daysPanels )
        {
            if( !panelDaySchedule.dataOk( ) )
            {
                return false;
            }
        }
        return ok;
    }
}
