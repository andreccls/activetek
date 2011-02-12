package co.com.activetek.genericmenu.server.beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import co.com.activetek.genericmenu.server.util.GenericMenuDAO;

public class MenuItem
{
    private int id;
    private String nombre;
    private String description;
    private boolean enable;
    private String icon;
    private Vector<MenuItem> sons;
    private Vector<Image> images;

    public MenuItem( int id, String nombre, String description, boolean enable, String icon, Vector<Image> images )
    {
        super( );
        this.id = id;
        this.nombre = nombre;
        this.description = description;
        this.enable = enable;
        this.icon = icon;
        sons = new Vector<MenuItem>( );
        this.images = images;
    }
    public Vector<Image> getImages( )
    {
        return images;
    }
    public void addSon( MenuItem son )
    {
        sons.add( son );
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
        return nombre;
    }
    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }
    public String getDescription( )
    {
        return description;
    }
    public void setDescription( String description )
    {
        this.description = description;
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
        sons = GenericMenuDAO.getInstance( ).getChildren( id );
        for( MenuItem son : sons )
        {
            son.loadSons( );
        }
    }
    public Vector<MenuItem> getSons( )
    {
        return sons;
    }
    public String toString( )
    {
        return nombre;
    }
    public MenuItem findByName( String string )
    {
        if(nombre.equals( string ))
        {
            return this;
        }
        for( MenuItem menuitem : sons )
        {
            MenuItem temp = menuitem.findByName( string );
            if(temp!=null)
                return temp;
        }
        return null;
    }

}
