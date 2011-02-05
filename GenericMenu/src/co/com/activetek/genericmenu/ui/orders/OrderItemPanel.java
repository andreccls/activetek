package co.com.activetek.genericmenu.ui.orders;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

public class OrderItemPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private JLabel labelCuantity = null;
    private JLabel labelItemName = null;
    private JCheckBox jCheckBox = null;

    /**
     * This is the default constructor
     */
    public OrderItemPanel( )
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
        labelItemName = new JLabel();
        labelItemName.setText("labelItemName");
        labelCuantity = new JLabel();
        labelCuantity.setText("  10  ");
        this.setLayout(new BorderLayout());
        this.add(labelCuantity, BorderLayout.WEST);
        this.add(labelItemName, BorderLayout.CENTER);
        this.add(getJCheckBox(), BorderLayout.EAST);
    }

    /**
     * This method initializes jCheckBox	
     * 	
     * @return javax.swing.JCheckBox	
     */
    private JCheckBox getJCheckBox( )
    {
        if( jCheckBox == null )
        {
            jCheckBox = new JCheckBox( );
        }
        return jCheckBox;
    }

}
