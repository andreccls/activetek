package co.com.activetek.genericmenu.server.beans;

import net.sf.json.JSONObject;

public class Table
{
    private int id;
    private String state;
    private boolean enable;

    public Table( int id, String state,boolean enable )
    {
        super( );
        this.id = id;
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
    public String getState( )
    {
        return state;
    }
    public void setState( String state )
    {
        this.state = state;
    }
    public JSONObject getJSON( )
    {
        JSONObject object = new JSONObject( );
        object.put( "id", id );
        object.put( "busy", state.equals( "busy" ) ? 1 : 0 );
        return object;
    }
    public boolean isEnable( )
    {
        return enable;
    }
    public void setEnable( boolean enable )
    {
        this.enable = enable;
    }

}
