package co.com.activetek.genericmenu.ui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JButton;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.beans.PriceItem;

public class EditAddPriceItemDialog extends JDialog implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField txtItems;
    private JTextField txtDetalles;
    private JTextField txtPrecio;
    private PriceItem priceItem;
    private MenuItem selected;
    private JCheckBox chckbxMostar;
    private boolean isNew;
    private PricesPanel father;
    
    public EditAddPriceItemDialog( PriceItem targeted, PricesPanel pricesPanel, MenuItem selected )
    {
        setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        this.father = pricesPanel;
        this.selected = selected;
        this.priceItem = targeted;
        isNew = priceItem == null;
        if( isNew )
            this.setTitle( "Agregar Precio" );
        else
            this.setTitle( "Editar Precio" );

        setSize( 302, 188 );
        getContentPane( ).setLayout( new MigLayout( "", "[grow][grow]", "[][][][][grow]" ) );

        JLabel lblNumeroDeItems = new JLabel( "Numero de Items" );
        getContentPane( ).add( lblNumeroDeItems, "cell 0 0,alignx trailing" );

        txtItems = new JTextField( );
        // txtItems.setText( Integer.toString( priceItem.getCuantity( ) ) );
        getContentPane( ).add( txtItems, "cell 1 0,growx" );
        txtItems.setColumns( 10 );

        JLabel lblDetalles = new JLabel( "Detalles" );
        getContentPane( ).add( lblDetalles, "cell 0 1,alignx trailing" );

        txtDetalles = new JTextField( );
        // txtDetalles.setText( priceItem.getDescripcion( ) );
        getContentPane( ).add( txtDetalles, "cell 1 1,growx" );
        txtDetalles.setColumns( 10 );

        JLabel lblPrecio = new JLabel( "Precio" );
        getContentPane( ).add( lblPrecio, "cell 0 2,alignx trailing" );

        txtPrecio = new JTextField( );
        // txtPrecio.setText( Long.toString( priceItem.getPrice( ) ) );
        getContentPane( ).add( txtPrecio, "cell 1 2,growx" );
        txtPrecio.setColumns( 10 );

        JLabel lblMostrar = new JLabel( "Mostrar" );
        getContentPane( ).add( lblMostrar, "cell 0 3,alignx right" );

        chckbxMostar = new JCheckBox( );
        // chckbxMostar.setSelected( priceItem.isEnable( ) );
        getContentPane( ).add( chckbxMostar, "cell 1 3" );

        JPanel panel = new JPanel( );
        getContentPane( ).add( panel, "cell 0 4 2 1,grow" );

        if( !isNew )
        {
            txtItems.setText( Integer.toString( priceItem.getCuantity( ) ) );
            txtDetalles.setText( priceItem.getDescripcion( ) );
            txtPrecio.setText( Long.toString( priceItem.getPrice( ) ) );
            chckbxMostar.setSelected( priceItem.isEnable( ) );
        }

        JButton btnAceptar = new JButton( "Aceptar" );
        panel.add( btnAceptar );
        btnAceptar.addActionListener( this );
        btnAceptar.setActionCommand( "ACEPT" );

        JButton btnCancelar = new JButton( "Cancelar" );
        btnCancelar.addActionListener( this );
        btnCancelar.setActionCommand( "CANCEL" );
        panel.add( btnCancelar );
    }
    public void dispose( )
    {
        super.dispose( );
    }
    @Override
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );
        if( command.equals( "ACEPT" ) )
        {
            if( dataOk( ) )
            {
                if( isNew )
                {
                    try
                    {
                        priceItem = new PriceItem( -1, Integer.parseInt( txtItems.getText( ) ), txtDetalles.getText( ), chckbxMostar.isSelected( ), 1, Long.parseLong( txtPrecio.getText( ) ), selected );
                        //selected.addPriceItem( priceItem );
                    }
                    catch( NumberFormatException e1 )
                    {
                        JOptionPane
                                .showMessageDialog( this, "Error inesperado tratando de persistir el precio\n Concatacte al administrador de la aplicacion\n Para mayor ayuda visitenos en helpdesk.activetek.co ", "Error", JOptionPane.ERROR_MESSAGE );
                        e1.printStackTrace( );
                    }
                    catch( SQLException e1 )
                    {
                        JOptionPane
                                .showMessageDialog( this, "Error inesperado tratando de persistir el precio\n Concatacte al administrador de la aplicacion\n para mayor ayuda visitenos en helpdesk.activetek.co ", "Error", JOptionPane.ERROR_MESSAGE );
                        e1.printStackTrace( );
                    }
                }
                else
                {
                    priceItem.setCuantity( Integer.parseInt( txtItems.getText( ) ) );
                    priceItem.setDescripcion( txtDetalles.getText( ) );
                    priceItem.setEnable( chckbxMostar.isSelected( ) );
                    priceItem.setPrice( Long.parseLong( txtPrecio.getText( ) ) );
                }
                father.persistPriceItem(priceItem);
                father.refresh();
                this.dispose( );
            }
        }
        else if( command.equals( "CANCEL" ) )
        {
            this.dispose( );
        }

    }
    public boolean dataOk( )
    {
        try
        {
            Long.parseLong( txtPrecio.getText( ) );
        }
        catch( NumberFormatException e )
        {
            JOptionPane.showMessageDialog( this, "Ingrese un precio valido", "Error", JOptionPane.ERROR_MESSAGE );
            return false;
        }
        try
        {
            Integer.parseInt( txtItems.getText( ) );
        }
        catch( NumberFormatException e )
        {
            JOptionPane.showMessageDialog( this, "Ingrese un numero de items valido", "Error", JOptionPane.ERROR_MESSAGE );
            return false;
        }

        return true;
    }
}
