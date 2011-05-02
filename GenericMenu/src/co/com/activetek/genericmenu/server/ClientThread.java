package co.com.activetek.genericmenu.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import co.com.activetek.genericmenu.server.beans.Order;
import co.com.activetek.genericmenu.server.beans.PriceItem;
import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.server.util.Log;

public class ClientThread extends Thread
{
    private Socket socket;
    private BufferedReader read;
    private PrintWriter write;
    private String clientId;
    private GenericMenuServer server;
    private Waitress waitress;
    private Table table;
    private boolean isMaster;
    private Order order;
    private int slaveNumber;
    /**
     * Significa que el usuario ya termino de realizar su pedido
     */
    private boolean ready;

    public ClientThread( Socket socket, GenericMenuServer server ) throws IOException
    {
        this.order = new Order( );
        this.socket = socket;
        this.server = server;
        read = new BufferedReader( new InputStreamReader( socket.getInputStream( ) ) );
        write = new PrintWriter( socket.getOutputStream( ), true );
        String line = read.readLine( );
        if( line.startsWith( "CLIENT:" ) )
        {
            clientId = line.split( ":" )[ 1 ];
        }
        else if( line.startsWith( "RETRY:" ) )
        {
            clientId = line.split( ":" )[ 1 ];// TODO tiene que recupara la informacion que tenia del anterior cliente
        }
        Log.getInstance( ).getLog( ).info( "Nuevo cliente conectado id: " + clientId + " ip: " + this.socket.getInetAddress( ) );
    }
    public void run( )
    {
        try
        {
            init( );
            while( socket.isConnected( ) )
            {
                processLine( read.readLine( ) );
            }
        }
        catch( IOException e )
        {
            e.printStackTrace( );
        }
    }
    private void processLine( String line )
    {
        System.out.println( line );
        if( line != null )
        {
            if( line.startsWith( "ADD:" ) || line.startsWith( "REMOVE:" ) )
            {
                //salveNumber = Integer.parseInt( line.split( ":" )[ 1 ] );// TODO este es el numero del esclavo , o para el master
                int priceItemId = Integer.parseInt( line.split( ":" )[ 2 ] );
                if( line.startsWith( "ADD:" ) )
                {
                    order.add( server.getPriceItemById( priceItemId ) );
                }
                else if( line.startsWith( "REMOVE:" ) )
                {
                    order.remove( server.getPriceItemById( priceItemId ) );
                }
                table.sendMesageToAll( line, this );
            }
            else if( line.startsWith( "END:" ) )
            {
                ready = true;
                if( isMaster && table.allClientsReady( ) )
                {
                    write.println( "READY:" );
                    System.out.println( "READY:" );
                }
            }
        }
    }
    private void init( ) throws IOException
    {
        String line = read.readLine( );
        if( line.startsWith( "MESA:" ) )
        {
            int tableNumber = Integer.parseInt( line.split( ":" )[ 1 ] );
            System.out.println( "tableNumber: " + tableNumber );
            table = server.getTablebyNumber( tableNumber );
            slaveNumber =  table.getNextSalveNumber();
            table.addClient( this );
            if( table.getState( ).equals( Table.FREE ) )
            {
                write.println( "MAESTRO:" );
                isMaster = true;
            }
            else
            {                
                write.println( "ESCLAVO:" + slaveNumber );
                isMaster = false;
            }
        }
        line = read.readLine( );
        if( line.startsWith( "MESERO:" ) )
        {
            int waitressId = Integer.parseInt( line.split( ":" )[ 1 ] );
            waitress = server.getWaitressById( waitressId );
            System.out.println( waitress );
        }
    }
    public boolean isReady( )
    {
        return ready;
    }
    public void sendMesage( String mess )
    {
        write.println( mess );
    }
}
