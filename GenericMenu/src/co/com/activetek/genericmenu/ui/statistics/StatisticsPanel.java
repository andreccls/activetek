package co.com.activetek.genericmenu.ui.statistics;

import javax.swing.JPanel;

import co.com.activetek.genericmenu.server.beans.PriceItem;
import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.ui.ActiveMenu;

public class StatisticsPanel extends JPanel
{
    private ActiveMenu window;

    private PriceItem bestPriceItem;
    private Waitress bestWaitress;

    public StatisticsPanel( ActiveMenu window )
    {
        this.window = window;
    }

    public void refresh( )
    {
        
    }
}
