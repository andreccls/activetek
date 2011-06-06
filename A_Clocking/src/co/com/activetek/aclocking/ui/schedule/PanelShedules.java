package co.com.activetek.aclocking.ui.schedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;

import co.com.activetek.aclocking.entitybeans.Schedule;
import co.com.activetek.aclocking.ui.AClockingUI;

public class PanelShedules extends JPanel implements ActionListener
{
    private AClockingUI window;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JList list;

    public PanelShedules( AClockingUI aClockingUI )
    {
        this.window = aClockingUI;
        setLayout( new MigLayout( "", "[grow]", "[][grow][grow]" ) );

        JLabel lblHorarios = new JLabel( "Horarios" );
        add( lblHorarios, "cell 0 0" );

        JScrollPane scrollPane = new JScrollPane( );
        add( scrollPane, "cell 0 1,grow" );

        list = new JList( );
        scrollPane.setViewportView( list );

        JPanel panelButtons = new JPanel( );
        add( panelButtons, "cell 0 2,grow" );

        btnAgregar = new JButton( "Agregar" );
        btnAgregar.setActionCommand( "ADD" );
        btnAgregar.addActionListener( this );
        panelButtons.add( btnAgregar );

        btnEditar = new JButton( "Editar" );
        btnEditar.setActionCommand( "EDIT" );
        btnEditar.addActionListener( this );
        panelButtons.add( btnEditar );

        btnEliminar = new JButton( "Eliminar" );
        btnEliminar.setActionCommand( "DELETE" );
        btnEliminar.addActionListener( this );
        panelButtons.add( btnEliminar );

        refresh( );
    }
    @Override
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );
        if( command.equals( "ADD" ) )
        {
            window.showAddScheduleDialog( );
        }
        if( command.equals( "EDIT" ) )
        {
            window.showEditScheduleDialog( ( Schedule )list.getSelectedValue( ) );
        }
        if( command.equals( "DELETE" ) )
        {
            window.deleteSchedule( ( Schedule )list.getSelectedValue( ) );
        }
    }
    public void refresh( )
    {
        list.setListData( window.getSchedules( ).toArray( ) );
        list.setSelectedIndex( 0 );
        if( window.getSchedules( ).size( ) == 0 )
        {
            btnEditar.setEnabled( false );
            btnEliminar.setEnabled( false );
        }
        else
        {
            btnEditar.setEnabled( true );
            btnEliminar.setEnabled( true );
        }
    }
}
