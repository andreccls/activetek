package co.com.activetek.genericmenu.ui.menu;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import javax.swing.JButton;

public class ProdcutInfoDetailPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private JCheckBox checkBoxEnableProduct = null;
    private JButton buttonDeleteProduct = null;

    /**
     * This is the default constructor
     */
    public ProdcutInfoDetailPanel( )
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
        GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.gridy =1;
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        this.setSize( 300, 200 );
        this.setLayout( new GridBagLayout( ) );
        this.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        this.add(getCheckBoxEnableProduct(), gridBagConstraints);
        this.add(getButtonDeleteProduct(), gridBagConstraints2);
    }

    /**
     * This method initializes checkBoxEnableProduct	
     * 	
     * @return javax.swing.JCheckBox	
     */
    private JCheckBox getCheckBoxEnableProduct( )
    {
        if( checkBoxEnableProduct == null )
        {
            checkBoxEnableProduct = new JCheckBox( "enable" );
        }
        return checkBoxEnableProduct;
    }

    /**
     * This method initializes buttonDeleteProduct	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getButtonDeleteProduct( )
    {
        if( buttonDeleteProduct == null )
        {
            buttonDeleteProduct = new JButton( "Borrar producto" );
        }
        return buttonDeleteProduct;
    }

}
