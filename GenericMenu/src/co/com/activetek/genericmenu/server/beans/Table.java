package co.com.activetek.genericmenu.server.beans;

import net.sf.json.JSONObject;

public class Table
{
    /**
     * Id generado automatico por la base de datos
     */
    private int id;
    /**
     * Id usado por el negocio
     */    
    private int number;
    private int capacity;
    private int x;
    private int y;
    private String state;
    private boolean enable;

    public Table( int id, int number, int capacity, int x, int y, String state, boolean enable )
    {
        super( );
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.x = x;
        this.y = y;
        this.state = state;
        this.enable = enable;
    }
    public int getId( )
    {
        return id;
    }
    public void setId( int id )
    {
        this.id = id;
    }
    public int getNumber( )
    {
        return number;
    }
    public void setNumber( int number )
    {
        this.number = number;
    }
    public int getCapacity( )
    {
        return capacity;
    }
    public void setCapacity( int capacity )
    {
        this.capacity = capacity;
    }
    public int getX( )
    {
        return x;
    }
    public void setX( int x )
    {
        this.x = x;
    }
    public int getY( )
    {
        return y;
    }
    public void setY( int y )
    {
        this.y = y;
    }
    public String getState( )
    {
        return state;
    }
    public void setState( String state )
    {
        this.state = state;
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
        object.put( "busy", state.equals( "busy" ) ? 1 : 0 );
        return object;
    }
    public String toString( )
    {
        return x + "," + y;
    }

}
