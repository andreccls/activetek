package co.com.activetek.genericmenu.ui.statistics;

import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import co.com.activetek.genericmenu.server.beans.PriceItem;
import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.server.util.GenericMenuDAO;
import co.com.activetek.genericmenu.ui.ActiveMenu;

public class StatisticsPanel extends JPanel
{
    private ActiveMenu window;

    private PriceItem bestPriceItem;
    private Waitress bestWaitress;

    public StatisticsPanel( ActiveMenu window )
    {
        this.window = window;
        refresh( );
    }

    public void refresh( )
    {
        this.removeAll( );
        // Adicional el numero de platos vendidos por mesero
        try
        {
            JPanel statisticsPanel1 = new JPanel( );
            GridLayout gl = new GridLayout( 0, 2 );
            statisticsPanel1.setLayout( gl );

            JLabel labTitleWaitress = new JLabel( "Mesero" );
            JLabel labTitleSales = new JLabel( "Total ventas" );

            statisticsPanel1.add( labTitleWaitress );
            statisticsPanel1.add( labTitleSales );

            HashMap<Waitress, Integer> sales = window.getTopWaitress( );
            for( Waitress w : sales.keySet( ) )
            {
                JLabel labName = new JLabel( w.getNick( ) );
                statisticsPanel1.add( labName );

                JLabel labSales = new JLabel( Integer.toString( sales.get( w ) ) );
                statisticsPanel1.add( labSales );
            }
            this.add( statisticsPanel1 );
        }
        catch( SQLException e )
        {
            this.add( new JLabel( "ERROR" ) );
            e.printStackTrace( );
        }
        this.revalidate( );
        this.repaint( );
    }
}
