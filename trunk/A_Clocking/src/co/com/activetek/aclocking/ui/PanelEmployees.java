package co.com.activetek.aclocking.ui;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;

public class PanelEmployees extends JPanel
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
        panelButtons.add( btnAgregar );

        JButton btnEditar = new JButton( "Editar" );
        panelButtons.add( btnEditar );

        JButton btnEliminar = new JButton( "Eliminar" );
        panelButtons.add( btnEliminar );
    }

}
