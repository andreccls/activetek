package co.com.activetek.genericmenu.ui.orders;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import javax.swing.border.EtchedBorder;

import co.com.activetek.genericmenu.server.beans.PriceItem;

public class OrderItemPanel extends JPanel
{
    public OrderItemPanel(PriceItem priceItem, int i )
    {
        this.setBackground( i % 2 == 0 ? OrdersPanel.COLOR_1 :  OrdersPanel.COLOR_2 );
        setBorder( null );
        setLayout( new MigLayout( "", "[14px][grow][]", "[14px][][]" ) );        

        JPanel cuantityPanel = new JPanel( );
        cuantityPanel.setBackground( i % 2 == 0 ? OrdersPanel.COLOR_1 :  OrdersPanel.COLOR_2 );
        cuantityPanel.setBorder( new EmptyBorder( 0, 0, 0, 0 ) );
        add( cuantityPanel, "cell 0 0 1 3,alignx left,aligny top" );

        JLabel label = new JLabel( "1" );
        label.setBackground( i % 2 == 0 ? OrdersPanel.COLOR_1 :  OrdersPanel.COLOR_2 );
        label.setFont( new Font( "Tahoma", Font.PLAIN, 18 ) );
        label.setHorizontalAlignment( SwingConstants.CENTER );
        cuantityPanel.add( label );

        JLabel lblMenuitemdescriptionlabel = new JLabel( priceItem.getMenuitem( ).getName( ) );
        lblMenuitemdescriptionlabel.setBackground( i % 2 == 0 ? OrdersPanel.COLOR_1 :  OrdersPanel.COLOR_2 );
        add( lblMenuitemdescriptionlabel, "cell 1 0" );

        JCheckBox checkBox = new JCheckBox( "" );
        checkBox.setBackground( i % 2 == 0 ? OrdersPanel.COLOR_1 :  OrdersPanel.COLOR_2 );
        checkBox.setHorizontalAlignment( SwingConstants.RIGHT );
        add( checkBox, "cell 2 0" );

        JLabel lblPricedescriptionlabel = new JLabel( priceItem.getDescripcion( ) );
        add( lblPricedescriptionlabel, "cell 1 1" );

//        JLabel lblUsercomentslabel = new JLabel( "userComentsLabel" );
//        add( lblUsercomentslabel, "cell 1 2" );
    }

}
