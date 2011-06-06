package co.com.activetek.aclocking.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;

public class PanelEmployees extends JPanel implements ActionListener
{
    public AClockingUI window;
    public PanelEmployees( AClockingUI aClockingUI )
    {
        this.window = aClockingUI;
        setLayout( new MigLayout( "", "[grow]", "[][grow][grow]" ) );

        JLabel lblEmpleados = new JLabel( "Empleados" );
        add( lblEmpleados, "cell 0 0" );

        JScrollPane scrollPane = new JScrollPane( );
        add( scrollPane, "cell 0 1,grow" );

        JList list = new JList( );
        list.setListData( window.getEmployees( ).toArray( ) );
        scrollPane.setViewportView( list );

        JPanel panelButtons = new JPanel( );
        add( panelButtons, "cell 0 2,grow" );

        JButton btnAgregar = new JButton( "Agregar" );
        btnAgregar.setActionCommand( "ADD" );
        btnAgregar.addActionListener( this );
        panelButtons.add( btnAgregar );

        JButton btnEditar = new JButton( "Editar" );
        btnEditar.setActionCommand( "EDIT" );
        btnEditar.addActionListener( this );
        panelButtons.add( btnEditar );

        JButton btnEliminar = new JButton( "Eliminar" );
        btnEliminar.setActionCommand( "DELETE" );
        btnEliminar.addActionListener( this );
        panelButtons.add( btnEliminar );
    }
    @Override
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );
        if( command.equals( "ADD" ) )
        {
            window.showAddEmployeeDialog();
        }
        else if( command.equals( "EDIT" ) )
        {

        }
        else if( command.equals( "DELETE" ) )
        {

        }
    }

}
