package co.com.activetek.genericmenu.ui.menu;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

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

        GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.gridy=2;
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridy = 1;
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        labelNombre = new JLabel();
        labelNombre.setText("Ebi roll");
        labelNombre.setFont(new Font("Dialog", Font.BOLD, 18));
        this.setSize(388, 267);
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createTitledBorder(null, "Detalles del producto", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        this.add(labelNombre, gridBagConstraints);
        this.add(getProductInfoImagePanel(), gbc1);
        this.add(getProdcutInfoDetailPanel(), gridBagConstraints3);
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

}  //  @jve:decl-index=0:visual-constraint="10,10"
