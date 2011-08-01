package co.com.activetek.genericmenu.ui.orders;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;

import co.com.activetek.genericmenu.server.beans.Order;
import co.com.activetek.genericmenu.server.beans.PriceItem;
import co.com.activetek.genericmenu.ui.ActiveMenu;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderPanel extends JPanel implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ActiveMenu window;
    private Order order;

    public OrderPanel( ActiveMenu window, Order order, int i )
    {
        this.window = window;
        this.order = order;
        this.setBackground( i % 2 == 0 ? OrdersPanel.COLOR_1 : OrdersPanel.COLOR_2 );
        setLayout( new MigLayout( "", "[grow][]", "[][][]" ) );
        int y = 0;
        JPanel nortgPanel = new JPanel( );
        nortgPanel.setBackground( i % 2 == 0 ? OrdersPanel.COLOR_1 : OrdersPanel.COLOR_2 );
        add( nortgPanel, "cell 0 " + y++ + " 2 1,grow" );
        nortgPanel.setLayout( new GridLayout( 1, 2, 0, 0 ) );

        JLabel lblMesa = new JLabel( "Mesa:" );
        lblMesa.setHorizontalAlignment( SwingConstants.RIGHT );
        nortgPanel.add( lblMesa );

        JLabel tableNumberlbl = new JLabel( order.getTable( ).getNumber( ) + "" );
        tableNumberlbl.setFont( new Font( "Tahoma", Font.PLAIN, 24 ) );
        nortgPanel.add( tableNumberlbl );

        for( PriceItem priceItem : order )
        {
            OrderItemPanel orderItem = new OrderItemPanel( priceItem, i );
            add( orderItem, "cell 0 " + y++ + " 2 1,grow" );
        }

        JPanel buttonsPanel = new JPanel( );
        buttonsPanel.setBackground( i % 2 == 0 ? OrdersPanel.COLOR_1 : OrdersPanel.COLOR_2 );
        add( buttonsPanel, "cell 0 " + y++ + " 2 1,grow" );
        buttonsPanel.setLayout( new FlowLayout( FlowLayout.CENTER, 5, 5 ) );

        JButton btnLiberarMesa = new JButton( "Liberar Mesa" );
        btnLiberarMesa.addActionListener( this );
        btnLiberarMesa.setActionCommand( "RELEASE_TABLE" );
        buttonsPanel.add( btnLiberarMesa );

        JButton btnCancelarPedido = new JButton( "Cancelar Pedido" );
        btnCancelarPedido.addActionListener( this );
        btnCancelarPedido.setActionCommand( "CANCEL_ORDER" );
        buttonsPanel.add( btnCancelarPedido );
        this.setSize( 50, 50 );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );
        if( command.equals( "RELEASE_TABLE" ) )
        {
            window.closeOrder( order );
        }
        else if( command.equals( "CANCEL_ORDER" ) )
        {
            window.cancelOrder( order );
        }
    }

}
