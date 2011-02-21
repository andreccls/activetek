package co.com.activetek.genericmenu.ui.menu;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import co.com.activetek.genericmenu.server.beans.Image;
import co.com.activetek.genericmenu.server.beans.MenuItem;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.Vector;
import javax.swing.BoxLayout;

public class ProductInfoPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JLabel labelNombre = null;
    private ProductInfoImagePanel productInfoImagePanel = null;
    private ProdcutInfoDetailPanel prodcutInfoDetailPanel = null;

    /**
     * This is the default constructor
     */
    public ProductInfoPanel( )
    {
        super( );
        initialize( );
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize( )
    {

        labelNombre = new JLabel( );
        labelNombre.setText( "Ebi roll" );//TODO esta vaina tiene que morir
        labelNombre.setFont( new Font( "Dialog", Font.BOLD, 18 ) );
        this.setSize( 388, 600 );
        this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        this.setBorder( BorderFactory.createTitledBorder( null, "Detalles del producto", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null ) );
        this.add( labelNombre );
        this.add( getProductInfoImagePanel( ) );
        this.add( getProdcutInfoDetailPanel( ) );
    }

    /**
     * This method initializes productInfoImagePanel
     * 
     * @return co.com.activetek.genericmenu.ui.menu.ProductInfoImagePanel
     */
    private ProductInfoImagePanel getProductInfoImagePanel( )
    {
        if( productInfoImagePanel == null )
        {
            productInfoImagePanel = new ProductInfoImagePanel( );
        }
        return productInfoImagePanel;
    }

    /**
     * This method initializes prodcutInfoDetailPanel
     * 
     * @return co.com.activetek.genericmenu.ui.menu.ProdcutInfoDetailPanel
     */
    private ProdcutInfoDetailPanel getProdcutInfoDetailPanel( )
    {
        if( prodcutInfoDetailPanel == null )
        {
            prodcutInfoDetailPanel = new ProdcutInfoDetailPanel( );
        }
        return prodcutInfoDetailPanel;
    }

    public void updateSelectedItem( MenuItem selected )
    {
        labelNombre.setText( selected.getName( ) );
        productInfoImagePanel.setImages( selected.getImages( ) );
        prodcutInfoDetailPanel.setSelectedItem(selected);
    }

} // @jve:decl-index=0:visual-constraint="10,10"
