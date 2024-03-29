package co.com.activetek.genericmenu.server.beans;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Vector;

import co.com.activetek.genericmenu.server.util.GenericMenuDAO;

public class Order extends Vector<PriceItem>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Table table;
    /**
     * Hora a la que el cliente llega comienza a tomar su pedido
     */
    private String startTime;
    /**
     * Hora a la que el cliente termina de hacer el pedido
     */
    private String orderTime;
    /**
     * Hora a la que el pedido se reporta como listo
     */
    private String serverdTime;

    private Waitress waitress;

    private String clientIds;

    private SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

    public Order( Table table )
    {
        super( );
        this.table = table;

        Calendar c = Calendar.getInstance( );
        startTime = sdf.format( c.getTime( ) );
    }

    public Table getTable( )
    {
        return table;
    }

    public Waitress getWaitress( )
    {
        return waitress;
    }

    public String getStartTime( )
    {
        return startTime;
    }

    public String getOderTime( )
    {
        return orderTime;
    }

    public String getServedTime( )
    {
        return serverdTime;
    }

    public void orderOrdered( )
    {
        Calendar c = Calendar.getInstance( );
        orderTime = sdf.format( c.getTime( ) );
    }

    public void closeOrder( ) throws SQLException
    {
        Calendar c = Calendar.getInstance( );
        serverdTime = sdf.format( c.getTime( ) );
        GenericMenuDAO.getInstance( ).CRUD( this );
    }

    public void setWaitress( Waitress waitress2 )
    {
        this.waitress = waitress2;
    }

    public void setClientIs( String concatIds )
    {
        this.clientIds = concatIds;
    }

    public String getClientIds( )
    {
        return clientIds;
    }
}
