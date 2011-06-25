package co.com.activetek.genericmenu.ui.menu;

import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JButton;

public class EditAddPriceItemDialog extends JDialog
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField txtItems;
    private JTextField txtDetalles;
    private JTextField txtPrecio;
    public EditAddPriceItemDialog( )
    {
        getContentPane( ).setLayout( new MigLayout( "", "[grow][grow]", "[][][][][grow]" ) );

        JLabel lblNumeroDeItems = new JLabel( "Numero de Items" );
        getContentPane( ).add( lblNumeroDeItems, "cell 0 0,alignx trailing" );

        txtItems = new JTextField( );
        txtItems.setText( "items" );
        getContentPane( ).add( txtItems, "cell 1 0,growx" );
        txtItems.setColumns( 10 );

        JLabel lblDetalles = new JLabel( "Detalles" );
        getContentPane( ).add( lblDetalles, "cell 0 1,alignx trailing" );

        txtDetalles = new JTextField( );
        txtDetalles.setText( "detalles" );
        getContentPane( ).add( txtDetalles, "cell 1 1,growx" );
        txtDetalles.setColumns( 10 );

        JLabel lblPrecio = new JLabel( "Precio" );
        getContentPane( ).add( lblPrecio, "cell 0 2,alignx trailing" );

        txtPrecio = new JTextField( );
        txtPrecio.setText( "precio" );
        getContentPane( ).add( txtPrecio, "cell 1 2,growx" );
        txtPrecio.setColumns( 10 );

        JLabel lblMostrar = new JLabel( "Mostrar" );
        getContentPane( ).add( lblMostrar, "cell 0 3" );

        JCheckBox chckbxMostar = new JCheckBox( "" );
        getContentPane( ).add( chckbxMostar, "cell 1 3" );

        JPanel panel = new JPanel( );
        getContentPane( ).add( panel, "cell 0 4 2 1,grow" );

        JButton btnAceptar = new JButton( "Aceptar" );
        panel.add( btnAceptar );

        JButton btnCancelar = new JButton( "Cancelar" );
        panel.add( btnCancelar );
    }

}
