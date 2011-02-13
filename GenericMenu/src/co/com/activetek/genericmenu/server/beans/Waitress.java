package co.com.activetek.genericmenu.server.beans;

import com.sun.xml.internal.bind.marshaller.NioEscapeHandler;

import net.sf.json.JSONObject;

public class Waitress
{
    private int id;
    private String photo;
    private String name;
    private String lastName;
    private String nick;
    private boolean enable;

    public Waitress( int id, String photo, String name, String lastName,String nick, boolean enable )
    {
        super( );
        this.id = id;
        this.photo = photo;
        this.name = name;
        this.lastName = lastName;
        this.nick =  nick;
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
    public String getPhoto( )
    {
        return photo;
    }
    public void setPhoto( String photo )
    {
        this.photo = photo;
    }
    public String getName( )
    {
        return name;
    }
    public void setName( String name )
    {
        this.name = name;
    }
    public String getLastName( )
    {
        return lastName;
    }
    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }
    public String getNick( )
    {
        return nick;
    }
    public void setNick( String nick )
    {
        this.nick = nick;
    }
    public boolean isEnable( )
    {
        return enable;
    }
    public void setEnable( boolean enable )
    {
        this.enable = enable;
    }
    public JSONObject getJSON()
    {
        JSONObject object = new JSONObject( );
        object.put( "nick", nick );
        object.put( "id", id );
        object.put( "photo", photo.substring( 1 ) );
        return object;
    }
}
