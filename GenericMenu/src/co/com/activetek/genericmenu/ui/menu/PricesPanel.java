package co.com.activetek.genericmenu.ui.menu;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class PricesPanel extends JPanel
{
    private JTextField txtItems;
    private JTextField txtDetalles;
    private JTextField txtPrecio;
    public PricesPanel( )
    {
        setLayout( new MigLayout( "", "[][grow][][grow][][grow][][][][]", "[][]" ) );

        JLabel lblitems = new JLabel( "#Items" );
        lblitems.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblitems, "cell 0 0 2 1" );

        JLabel lblDetalles = new JLabel( "Detalles" );
        lblDetalles.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblDetalles, "cell 2 0 2 1" );

        JLabel lblPrecio = new JLabel( "Precio" );
        lblPrecio.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblPrecio, "cell 4 0 2 1" );

        JLabel lblMostar = new JLabel( "Mostar" );
        lblMostar.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblMostar, "cell 6 0 2 1" );

        JLabel lblEliminar = new JLabel( "Eliminar" );
        lblEliminar.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblEliminar, "cell 8 0 2 1" );

        txtItems = new JTextField( );
        txtItems.setText( "items" );
        add( txtItems, "cell 1 1,growx" );
        txtItems.setColumns( 10 );

        txtDetalles = new JTextField( );
        txtDetalles.setText( "detalles" );
        add( txtDetalles, "cell 3 1,growx" );
        txtDetalles.setColumns( 10 );

        txtPrecio = new JTextField( );
        txtPrecio.setText( "precio" );
        add( txtPrecio, "cell 5 1,growx" );
        txtPrecio.setColumns( 10 );

        JCheckBox chckbxMostar = new JCheckBox( "" );
        add( chckbxMostar, "cell 7 1" );

        JButton btnEliminar = new JButton( "eliminar" );
        add( btnEliminar, "cell 9 1" );
    }

}
