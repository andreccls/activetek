package co.com.activetek.genericmenu.server.beans;

import net.sf.json.JSONObject;

public class PriceItem
{
    private int id;
    private int cuantity;
    private String descripcion;
    private boolean enable;
    private int order;
    private long price;

    public PriceItem( int id, int cuantity, String descripcion, boolean enable, int order, long price )
    {
        super( );
        this.id = id;
        this.cuantity = cuantity;
        this.descripcion = descripcion;
        this.enable = enable;
        this.order = order;
        this.price = price;
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

}
