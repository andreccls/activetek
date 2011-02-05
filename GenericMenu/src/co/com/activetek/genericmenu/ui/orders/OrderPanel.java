package co.com.activetek.genericmenu.ui.orders;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class OrderPanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private JPanel panelTitle = null;
    private JLabel labelTable = null;
    private JCheckBox jCheckBox = null;
    private JPanel panelItems = null;
    /**
     * This is the default constructor
     */
    public OrderPanel( )
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
        this.setLayout( new BorderLayout( ) );
        this.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        this.add( getPanelTitle( ), BorderLayout.NORTH );
        this.add( getPanelItems( ), BorderLayout.CENTER );
    }

    /**
     * This method initializes panelTitle
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelTitle( )
    {
        if( panelTitle == null )
        {
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints( );
            GridBagConstraints gridBagConstraints = new GridBagConstraints( );
            labelTable = new JLabel( );
            labelTable.setText( "Mesa 1" );
            panelTitle = new JPanel( );
            panelTitle.setLayout( new GridBagLayout( ) );;
            panelTitle.add( labelTable, gridBagConstraints );
            panelTitle.add( getJCheckBox( ), gridBagConstraints1 );
        }
        return panelTitle;
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

    /**
     * This method initializes panelItems
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelItems( )
    {
        if( panelItems == null )
        {
            GridLayout gridLayout = new GridLayout( );
            // TODO change 5 for the number of the order items
            gridLayout.setRows( 5 );
            gridLayout.setColumns( 1 );
            panelItems = new JPanel( );
            for( int i = 0; i < 5; i++ )
            {
                panelItems.add( new OrderItemPanel( ) );
            }
            panelItems.setLayout( gridLayout );
        }
        return panelItems;
    }

}
