package co.com.activetek.genericmenu.ui.menu;

import javax.swing.JPanel;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.ui.OsakiMenu;

import java.awt.BorderLayout;

/**
 * Panel encargado de unir el arbol del menu, junto con el panel de descripciones
 * @author daniel.rodriguez
 * 
 */
public class MenuPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private MenuTreePanel menuTreePanel = null;
    private ProductInfoPanel productInfo = null;
    private OsakiMenu window;

    /**
     * This is the default constructor
     */
    public MenuPanel( OsakiMenu window )
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
        this.setSize( 600, 600 );
        this.setLayout( new BorderLayout( ) );
        this.add( getMenuTreePanel( ), BorderLayout.WEST );
        this.add( getProductInfo( ), BorderLayout.CENTER );
    }

    /**
     * This method initializes menuTreePanel
     * 
     * @return co.com.activetek.genericmenu.ui.menu.MenuTreePanel
     */
    private MenuTreePanel getMenuTreePanel( )
    {
        if( menuTreePanel == null )
        {
            menuTreePanel = new MenuTreePanel( window );
        }
        return menuTreePanel;
    }

    /**
     * This method initializes productInfo
     * 
     * @return co.com.activetek.genericmenu.ui.menu.ProductInfo
     */
    private ProductInfoPanel getProductInfo( )
    {
        if( productInfo == null )
        {
            productInfo = new ProductInfoPanel( window );
        }
        return productInfo;
    }

    public void updateSelectedItem( MenuItem selected )
    {
        productInfo.updateSelectedItem( selected );
    }

}
