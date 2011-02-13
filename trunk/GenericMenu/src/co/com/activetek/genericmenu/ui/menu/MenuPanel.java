package co.com.activetek.genericmenu.ui.menu;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.ui.GenericMenu;

import java.awt.BorderLayout;

public class MenuPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private MenuTreePanel menuTreePanel = null;
    private ProductInfoPanel productInfo = null;
    private GenericMenu window;

    /**
     * This is the default constructor
     */
    public MenuPanel( GenericMenu window )
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
        this.setSize( 300, 200 );
        this.setLayout(new BorderLayout());
        this.add(getMenuTreePanel(), BorderLayout.WEST);
        this.add(getProductInfo(), BorderLayout.CENTER);
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
            productInfo = new ProductInfoPanel( );
        }
        return productInfo;
    }

    public void updateSelectedItem( MenuItem selected )
    {
        productInfo.updateSelectedItem( selected );        
    }

}