package co.com.activetek.genericmenu.ui.menu;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.beans.PriceItem;
import co.com.activetek.genericmenu.ui.ActiveMenu;

public class PricesPanel extends JPanel implements ActionListener
{
    private static final String DELETE = "DELETE:";
    private static final String EDIT = "EDIT:";
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField txtItems;
    private JTextField txtDetalles;
    private JTextField txtPrecio;
    private ActiveMenu window;
    private HashMap<Integer, PriceItem> prices;
    private MenuItem selected;

    public PricesPanel( ActiveMenu window )
    {
        this.window = window;
        this.prices = new HashMap<Integer, PriceItem>( );
        setLayout( new MigLayout( "", "[:15px:15px][grow][][grow][][grow][] ", "" ) );

        JLabel lblitems = new JLabel( "#" );
        lblitems.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblitems, "cell 0 0 1 1" );

        JLabel lblDetalles = new JLabel( "Detalles" );
        lblDetalles.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblDetalles, "cell 1 0 1 1" );

        JLabel lblPrecio = new JLabel( "Precio" );
        lblPrecio.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblPrecio, "cell 2 0 1 1" );

        JLabel lblMostar = new JLabel( "Mostar" );
        lblMostar.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblMostar, "cell 3 0 1 1" );

        JLabel lblEliminar = new JLabel( "Eliminar" );
        lblEliminar.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblEliminar, "cell 4 0 1 1" );

        JLabel lblEditar = new JLabel( "Editar" );
        add( lblEditar, "cell 5 0" );

    }
    private void printHeader( )
    {
        JLabel lblitems = new JLabel( "#" );
        lblitems.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblitems, "cell 0 0 1 1" );

        JLabel lblDetalles = new JLabel( "Detalles" );
        lblDetalles.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblDetalles, "cell 1 0 1 1" );

        JLabel lblPrecio = new JLabel( "Precio" );
        lblPrecio.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblPrecio, "cell 2 0 1 1" );

        JLabel lblMostar = new JLabel( "Mostar" );
        lblMostar.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblMostar, "cell 3 0 1 1" );

        JLabel lblEliminar = new JLabel( "Eliminar" );
        // lblEliminar.setIcon( new ImageIcon("./images/trash-icon.gif" ) );
        lblEliminar.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblEliminar, "cell 4 0 1 1" );

        JLabel lblEditar = new JLabel( "Editar" );
        lblEditar.setFont( new Font( "Tahoma", Font.BOLD, 11 ) );
        add( lblEditar, "cell 5 0 1 1" );

    }
    public void setSelectedItem( MenuItem selected )
    {
        this.selected = selected;
        this.removeAll( );
        printHeader( );
        int i = 1;
        for( PriceItem priceItem : selected.getPrices( ) )
        {
            txtItems = new JTextField( );
            txtItems.setText( priceItem.getCuantity( ) + "" );
            add( txtItems, "cell 0 " + i + ",growx" );
            txtItems.setColumns( 10 );
            txtItems.setEditable( false );

            txtDetalles = new JTextField( );
            txtDetalles.setText( priceItem.getDescripcion( ) );
            add( txtDetalles, "cell 1 " + i + ",growx" );
            txtDetalles.setColumns( 10 );
            txtDetalles.setEditable( false );

            txtPrecio = new JTextField( );
            txtPrecio.setText( priceItem.getPrice( ) + "" );
            add( txtPrecio, "cell 2 " + i + ",growx" );
            txtPrecio.setColumns( 10 );
            txtPrecio.setEditable( false );

            JCheckBox chckbxMostar = new JCheckBox( "" );
            chckbxMostar.setSelected( priceItem.isEnable( ) );
            chckbxMostar.setEnabled( false );
            add( chckbxMostar, "cell 3 " + i + "" );

            JButton btnEliminar = new JButton( );
            btnEliminar.setIcon( new ImageIcon( "./images/trash-icon.gif" ) );
            btnEliminar.setActionCommand( DELETE + Integer.toString( priceItem.getId( ) ) );
            btnEliminar.addActionListener( this );
            add( btnEliminar, "cell 4 " + i + "" );

            JButton btnEditar = new JButton( );
            btnEditar.setIcon( new ImageIcon( "./images/edit.gif" ) );
            btnEditar.setActionCommand( EDIT + Integer.toString( priceItem.getId( ) ) );
            btnEditar.addActionListener( this );
            add( btnEditar, "cell 5 " + i + "" );

            prices.put( priceItem.getId( ), priceItem );
            i++;
        }
        JButton buttAdd = new JButton( "+" );
        buttAdd.addActionListener( this );
        buttAdd.setActionCommand( "ADD_PRICE" );
        add( buttAdd, "cell 0 " + i );
    }
    @Override
    public void actionPerformed( ActionEvent arg0 )
    {
        String command = arg0.getActionCommand( );
        if( command.equals( "ADD_PRICE" ) )
        {
            new EditAddPriceItemDialog( null, this, selected ).setVisible( true );
        }
        else
        {
            PriceItem targeted = prices.get( Integer.parseInt( command.split( ":" )[ 1 ] ) );
            if( command.startsWith( EDIT ) )
            {
                new EditAddPriceItemDialog( targeted, this, selected ).setVisible( true );
            }
            if( command.startsWith( DELETE ) )
            {
                window.deletePriceItem( targeted );
                this.refresh( );
            }

        }
    }
    public void persistPriceItem( PriceItem priceItem )
    {
        window.persistPriceItem( priceItem );
    }
    public void refresh( )
    {
        this.setSelectedItem( selected );
    }
}
