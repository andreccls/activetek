package co.com.activetek.genericmenu.server.beans;

import java.sql.SQLException;
import java.util.Vector;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import co.com.activetek.genericmenu.server.util.GenericMenuDAO;

public class MenuItem extends Vector<MenuItem>
{
    /**
     * Constante que representa los las caterias en el arbol del menu.
     * Es decir las categorias son lso nodos de nivel 2 en el arbol
     */
    public final static int LEVEl_CATEGORY = 1;
    
    private int id;
    private String name;
    private String details;
    private boolean enable;
    private String icon;
    private Vector<Image> images;
    private Vector<PriceItem> prices;
    private MenuItem father;

    public MenuItem( int id, String nombre, String description, boolean enable, String icon, Vector<Image> images, MenuItem father, Vector<PriceItem> prices )
    {
        super( );
        this.id = id;
        this.name = nombre;
        this.details = description;
        this.enable = enable;
        this.icon = icon;
        this.images = images;
        this.father = father;
        this.prices = prices;
    }
    public Vector<Image> getImages( )
    {
        return images;
    }
    public int getId( )
    {
        return id;
    }
    public void setId( int id )
    {
        this.id = id;
    }
    public String getName( )
    {
        return name;
    }
    public void setNombre( String nombre )
    {
        this.name = nombre;
    }
    public String getDescription( )
    {
        return details;
    }
    public void setDescription( String description )
    {
        this.details = description;
    }
    public boolean isEnable( )
    {
        return enable;
    }
    public void setEnable( boolean enable )
    {
        this.enable = enable;
    }
    public String getIcon( )
    {
        return icon;
    }
    public void setIcon( String icon )
    {
        this.icon = icon;
    }
    public void loadSons( ) throws SQLException
    {
        addAll( GenericMenuDAO.getInstance( ).getChildren( this ) );
        for( MenuItem menuItem : this )
        {
            menuItem.loadSons( );
        }
    }

    public String toString( )
    {
        return name;
    }
    public MenuItem findByName( String string )
    {
        if( name.equals( string ) )
        {
            return this;
        }
        for( MenuItem menuItem : this )
        {
            MenuItem temp = menuItem.findByName( string );
            if( temp != null )
                return temp;
        }
        return null;
    }
    public JSONObject getJSON( )
    {
        JSONObject object = new JSONObject( );
        
        if( getLevel( ) == 2 )
        {
            object.put( "name", name );
            object.put( "details", details );
            object.put( "price", getPricesJSONArray( ) );
            object.put( "images", getImagesJSONArray( ) );            
        }
        if( getLevel( ) == 1 )
        {
            object.put( "name", name );
            object.put( "menuitem", getSonsJSONArray( ) ); 
        }
        if( getLevel( ) == 0)
        {
            object.put( "categorias", getSonsJSONArray( ) );
        }

        return object;

    }
    /**
     * usado unicamente para los que son hoja
     * @return
     */
    public JSONArray getImagesJSONArray( )
    {
        JSONArray object = new JSONArray( );
        for( Image image : images )
        {
            object.add( image.getJson( ) );
        }
        return object;
    }
    /**
     * usado unicamente para los que son hoja
     * @return
     */
    public JSONArray getPricesJSONArray( )
    {        
        JSONArray array = new JSONArray( );
        for( PriceItem priceItem : prices )
        {
            array.add( priceItem.getJSON( ) );
        }
        return array;
    }
    /**
     * usado unicamente para los que son categorias
     * @return
     */
    public JSONArray getSonsJSONArray( )
    {
        JSONArray object = new JSONArray( );
        for( MenuItem menuItem : this )
        {
            object.add( menuItem.getJSON( ) );
        }
        return object;
    }
    public boolean isLeaf( )
    {
        return this.size( ) == 0;
    }
    public int getLevel( )
    {
        return father == null ? 0 : father.getLevel( ) + 1;
    }
    public Vector<PriceItem> getPrices()
    {
        return prices;
    }
}
