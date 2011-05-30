package co.com.activetek.genericmenu.server.beans;

import java.util.Map;
import java.util.Vector;

public class Order extends Vector<PriceItem>
{
    private Table table;
    public Order(Table table)
    {
        super();
        this.table = table;
    }
    
    public Table getTable()
    {
        return table;
    }
}
