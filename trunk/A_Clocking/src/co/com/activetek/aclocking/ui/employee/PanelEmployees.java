package co.com.activetek.aclocking.ui.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;

import co.com.activetek.aclocking.entitybeans.Employee;
import co.com.activetek.aclocking.ui.AClockingUI;

public class PanelEmployees extends JPanel implements ActionListener
{
    private AClockingUI window;
    private JList list;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    public PanelEmployees( AClockingUI aClockingUI )
    {
        this.window = aClockingUI;
        setLayout( new MigLayout( "", "[grow]", "[][grow][grow]" ) );

        JLabel lblEmpleados = new JLabel( "Empleados" );
        add( lblEmpleados, "cell 0 0" );

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
            window.showAddEmployeeDialog( );
        }
        else if( command.equals( "EDIT" ) )
        {
            window.showEditEmployeeDialog( ( Employee )list.getSelectedValue( ) );
        }
        else if( command.equals( "DELETE" ) )
        {
            window.deleteEmployee( ( Employee )list.getSelectedValue( ) );
        }
    }
    public void refresh( )
    {
        list.setListData( window.getEmployees( ).toArray( ) );
        list.setSelectedIndex( 0 );
        if( window.getEmployees( ).size( ) == 0 )
        {
            btnEditar.setEnabled( false );
        }
        else
        {
            btnEditar.setEnabled( true );
        }
    }
}
