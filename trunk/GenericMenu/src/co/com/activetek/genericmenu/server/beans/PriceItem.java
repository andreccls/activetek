package co.com.activetek.genericmenu.server.beans;

import java.sql.SQLException;

import co.com.activetek.genericmenu.server.util.GenericMenuDAO;
import net.sf.json.JSONObject;

public class PriceItem
{
    private int id;
    private int cuantity;
    private String descripcion;
    private boolean enable;
    private int order;
    private long price;
    private MenuItem menuItem;
    
    
    public PriceItem( int id, int cuantity, String descripcion, boolean enable, int order, long price, MenuItem  menuItemId) throws SQLException
    {
        super( );
        this.id = id;
        this.cuantity = cuantity;
        this.descripcion = descripcion;
        this.enable = enable;
        this.order = order;
        this.price = price;
        this.menuItem = menuItemId;
        //if(id <0)
        //    GenericMenuDAO.getInstance( ).CRUD( this );
    }
    public void modify( int cuantity, String descripcion, boolean enable, int order, long price ) throws SQLException
    {        
        this.cuantity = cuantity;
        this.descripcion = descripcion;
        this.enable = enable;
        this.order = order;
        this.price = price;
        GenericMenuDAO.getInstance( ).CRUD( this );
    }
    public MenuItem getMenuitem()
    {
        return menuItem;
    }
    public void setMenuIemId(MenuItem menuItemId)
    {
        this.menuItem = menuItemId;
    }
    public int getOrder( )
    {
        return order;
    }
    public long getPrice( )
    {
        return price;
    }
    public void setPrice( long price )
    {
        this.price = price;
    }
    public void setOrder( int order )
    {
        this.order = order;
    }

    public int getId( )
    {
        return id;
    }
    public void setId( int id )
    {
        this.id = id;
    }
    public int getCuantity( )
    {
        return cuantity;
    }
    public void setCuantity( int cuantity )
    {
        this.cuantity = cuantity;
    }
    public String getDescripcion( )
    {
        return descripcion;
    }
    public void setDescripcion( String descripcion )
    {
        this.descripcion = descripcion;
    }
    public boolean isEnable( )
    {
        return enable;
    }
    public void setEnable( boolean enable )
    {
        System.out.println("setEnable " + enable );
        this.enable = enable;
    }
    public JSONObject getJSON( )
    {
        JSONObject object = new JSONObject( );
        object.put( "id", id );
        object.put( "cuantity", cuantity );
        object.put( "order", order );
        object.put( "price", new Long( price ) );
        object.put( "descripcion", descripcion );
        return object;
    }
    public String toString( )
    {
        return cuantity + " " + descripcion + " " + price;

    }
    public void delete( ) throws SQLException
    {
        menuItem.deltePrice( this );
        GenericMenuDAO.getInstance( ).delete( this );
    }

}
