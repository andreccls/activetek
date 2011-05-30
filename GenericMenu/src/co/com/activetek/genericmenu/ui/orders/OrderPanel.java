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
import net.miginfocom.swing.MigLayout;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;

import co.com.activetek.genericmenu.server.beans.Order;

import java.awt.FlowLayout;

public class OrderPanel extends JPanel
{
    public OrderPanel(Order order )
    {
        setLayout( new MigLayout( "", "[grow][]", "[grow][grow][grow]" ) );

        JPanel nortgPanel = new JPanel( );
        add( nortgPanel, "cell 0 0 2 1,grow" );
        nortgPanel.setLayout( new GridLayout( 1, 2, 0, 0 ) );

        JLabel lblMesa = new JLabel( "Mesa:" );
        lblMesa.setHorizontalAlignment( SwingConstants.RIGHT );
        nortgPanel.add( lblMesa );

        JLabel tableNumberlbl = new JLabel( order.getTable( ).getId( ) + "" );
        tableNumberlbl.setFont( new Font( "Tahoma", Font.PLAIN, 24 ) );
        nortgPanel.add( tableNumberlbl );

        OrderItemPanel orderItem = new OrderItemPanel( );
        add( orderItem, "cell 0 1 2 1,grow" );

        JPanel buttonsPanel = new JPanel( );
        add( buttonsPanel, "cell 0 2 2 1,grow" );
        buttonsPanel.setLayout( new FlowLayout( FlowLayout.CENTER, 5, 5 ) );

        JButton btnLiberarMesa = new JButton( "Liberar Mesa" );
        buttonsPanel.add( btnLiberarMesa );

        JButton btnCancelarPedido = new JButton( "Cancelar Pedido" );
        buttonsPanel.add( btnCancelarPedido );
    }

}
