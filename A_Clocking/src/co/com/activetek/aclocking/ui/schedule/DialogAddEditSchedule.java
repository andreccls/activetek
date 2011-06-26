package co.com.activetek.aclocking.ui.schedule;

import javax.swing.JDialog;
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

public class DialogAddEditSchedule extends JDialog implements ActionListener
{
    private String[] daysOfWeek = { "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo", "Festivos" };
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
        setTitle( "Editar horario" );
        this.setSize( 608, 218 );
        getContentPane( ).setLayout( new MigLayout( "", "[591px,grow]", "[grow][1px][33px]" ) );

        JPanel panel_2 = new JPanel( );
        getContentPane( ).add( panel_2, "cell 0 0,grow" );
        panel_2.setLayout( new MigLayout( "", "[36px][86px]", "[20px]" ) );

        JLabel lblNombre = new JLabel( "Nombre" );
        panel_2.add( lblNombre, "cell 0 0,alignx left,aligny center" );

        txtNombre = new JTextField( );
        panel_2.add( txtNombre, "cell 1 0,alignx left,aligny top" );
        txtNombre.setColumns( 10 );
        
        
        JPanel panel = new JPanel( );
        panel.setBorder( new LineBorder( new Color( 0, 0, 0 ) ) );
        getContentPane( ).add( panel, "cell 0 1,growx,aligny top" );
        panel.setLayout( new GridLayout( 0, 8, 0, 0 ) );

        for( String day : daysOfWeek )
        {
            panel.add( new PanelDaySchedule( day, this ) );
        }
        
        JPanel panel_1 = new JPanel( );
        getContentPane( ).add( panel_1, "cell 0 2,growx,aligny top" );

        btnAceptar = new JButton( "Aceptar" );
        panel_1.add( btnAceptar );

        btnCancelar = new JButton( "Cancelar" );
        panel_1.add( btnCancelar );


    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );
        if( command.equals( "ACEPT" ) )
        {
            if( dataOk( ) )
            {
                if( isNewSchedule )
                {
                    //schedule = new TODO 
                }
                window.editCreateSchedule( schedule );
            }

        }
        else if( command.equals( "CANCEL" ) )
        {
            this.dispose( );
        }
    }
    public boolean dataOk( )
    {
        return true;
    }
}
