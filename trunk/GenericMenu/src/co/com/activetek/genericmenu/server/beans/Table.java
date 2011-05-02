package co.com.activetek.genericmenu.server.beans;

import java.sql.SQLException;
import java.util.Vector;

import co.com.activetek.genericmenu.server.ClientThread;
import co.com.activetek.genericmenu.server.util.GenericMenuDAO;
import net.sf.json.JSONObject;

public class Table
{
	//------------------------------------------------------------------------------------
	//				CONSTANTES
	//------------------------------------------------------------------------------------
	/**
	 * Representa el estado en que la mesa esta libre
	 */
	public final static String FREE = "free";
	/**
	 * Representa el estado para una que se encuentra ocupada y el pedido ya ha sido llevado a la mesa
	 */
	public final static String BUSY = "busy";
	/**
	 * Representa el estado para una mesa en que los clientes ya hicieron el pedido y se encuentran esperando la orden
	 */
	public final static String WAITING = "waiting";
    /**
     * Id generado automatico por la base de datos
     */
	//------------------------------------------------------------------------------------
	//				ATRIBUTOS
	//------------------------------------------------------------------------------------
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
    private Vector<ClientThread> clients;
	//------------------------------------------------------------------------------------
	//				CONSTRUCTOR
	//------------------------------------------------------------------------------------
    public Table( int id, int number, int capacity, int x, int y, String state, boolean enable ) throws SQLException
    {
        super( );
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.x = x;
        this.y = y;
        this.state = state;
        this.enable = enable;
        clients = new Vector<ClientThread>( );
        if(id < 0)
            GenericMenuDAO.getInstance( ).CRUD( this );
    }
    
	//------------------------------------------------------------------------------------
	//				METODOS
	//------------------------------------------------------------------------------------
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
    public void setNumber( int number ) throws SQLException
    {
        this.number = number;
        GenericMenuDAO.getInstance( ).CRUD( this );
    }
    public int getCapacity( )
    {
        return capacity;
    }
    public void setCapacity( int capacity ) throws SQLException
    {
        this.capacity = capacity;
        GenericMenuDAO.getInstance( ).CRUD( this );
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

    public void delete( ) throws SQLException
    {
        GenericMenuDAO.getInstance( ).delete( this );        
    }

    public void addClient( ClientThread clientThread )
    {
        clients.add( clientThread );
    }
    public boolean allClientsReady()
    {     
        for( ClientThread clientThread : clients )
        {
            if(!clientThread.isReady())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param line
     * @param client: el cliente que envia el mesaje a los demas, por esto a este no se le envia
     */
    public void sendMesageToAll( String line, ClientThread client )
    {
        for( ClientThread clientThread : clients )
        {
            if(!client.equals( clientThread ))
            {
                clientThread.sendMesage( line );
            }
        }
    }

    public int getNextSalveNumber( )
    {
        return clients.size( );
    }
}
