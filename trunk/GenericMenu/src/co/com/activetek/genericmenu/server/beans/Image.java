package co.com.activetek.genericmenu.server.beans;

import net.sf.json.JSONObject;

public class Image
{
    private int id;
    private String url;
    private boolean enable;
    private int order;
    private int menuItemId;
    public Image( int id, String url, boolean enable, int order,int menuItemId )
    {
        super( );
        this.id = id;
        this.url = url;
        this.enable = enable;
        this.order = order;
        this.menuItemId = menuItemId;
    }
    public String toString( )
    {
        return url;
    }
    public String getUrl( )
    {
        return url;
    }
    public JSONObject getJson( )
    {
        JSONObject object = new JSONObject( );
        object.put( "order", new Integer( order ) );
        object.put( "url", url.substring( 1 ) );
        return object;
    }
}
