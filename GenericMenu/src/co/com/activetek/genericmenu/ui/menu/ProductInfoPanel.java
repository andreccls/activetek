package co.com.activetek.genericmenu.ui.menu;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import co.com.activetek.genericmenu.server.beans.Image;
import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.ui.ActiveMenu;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

public class ProductInfoPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JLabel labelNombre = null;
    private ProductInfoImagePanel productInfoImagePanel = null;
    private ProdcutInfoDetailPanel prodcutInfoDetailPanel = null;
    private ActiveMenu window;
    /**
     * This is the default constructor
     */
    public ProductInfoPanel( ActiveMenu window )
    {
        super( );
        this.window = window;
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
        labelNombre.setHorizontalAlignment(SwingConstants.LEFT);
        labelNombre.setText( "Ebi roll" );// TODO esta vaina tiene que morir
        labelNombre.setFont( new Font( "Dialog", Font.BOLD, 18 ) );
        //this.setSize( 388, 600 );
        this.setBorder( BorderFactory.createTitledBorder( null, "Detalles del producto", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null ) );
        setLayout(new MigLayout("fill", "[376px]", "[:24px:24px][:264px:264px][310px:310px]"));
        this.add( labelNombre, "cell 0 0,alignx center,aligny top" );
        this.add( getProductInfoImagePanel( ), "cell 0 1,alignx center,aligny top" );
        this.add( getProdcutInfoDetailPanel( ), "cell 0 2,alignx center,aligny center" );
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
            productInfoImagePanel = new ProductInfoImagePanel( window );
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
            prodcutInfoDetailPanel = new ProdcutInfoDetailPanel( window );
        }
        return prodcutInfoDetailPanel;
    }

    public void updateSelectedItem( MenuItem selected )
    {
        if( selected != null && ( selected.getLevel( ) == MenuItem.LEVEl_CATEGORY || selected.getLevel( ) == MenuItem.LEVEL_MENU ) )
        {
            labelNombre.setText( selected.getName( ) );
            productInfoImagePanel.setImages( selected.getImages( ) );
            getProdcutInfoDetailPanel().setVisible( false );
            this.repaint( );
        }
        else if( selected != null )
        {
            labelNombre.setText( selected.getName( ) );
            productInfoImagePanel.setImages( selected.getImages( ) );
            getProdcutInfoDetailPanel().setVisible( true );
            prodcutInfoDetailPanel.setSelectedItem( selected );
        }
    }

} // @jve:decl-index=0:visual-constraint="10,10"
