package co.com.activetek.genericmenu.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Observable;

import sun.awt.windows.ThemeReader;

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
    private ActiveMenuServer server;
    private Table table;
    private boolean isMaster;
    // private Order order;
    private int slaveNumber;
    private boolean finished;

    private boolean debug = true;
    /**
     * Significa que el usuario ya termino de realizar su pedido
     */
    private boolean ready;

    public ClientThread( Socket socket, ActiveMenuServer server ) throws IOException
    {
        this.socket = socket;
        this.server = server;
        read = new BufferedReader( new InputStreamReader( socket.getInputStream( ) ) );
        write = new PrintWriter( socket.getOutputStream( ), true );
        this.finished = false;
        String line = read.readLine( );
        if( line.startsWith( "CLIENT:" ) )
        {
            // clientId = line.split( ":" )[ 1 ];
            clientId = line.replace( "CLIENT:", "" );
        }
        else if( line.startsWith( "RETRY:" ) )
        {
            // clientId = line.split( ":" )[ 1 ];// TODO tiene que recupara la informacion que tenia del anterior cliente
            clientId = line.replace( "RETRY:", "" );
        }
        Log.getInstance( ).getLog( ).info( "Nuevo cliente conectado id: " + clientId + " ip: " + this.socket.getInetAddress( ) );
    }
    public void run( )
    {
        try
        {
            init( );
            while( socket.isConnected( ) && !finished )
            {
                processLine( read.readLine( ) );
            }
        }
        catch( IOException e )
        {
            e.printStackTrace( );
        }
        finally
        {
            Log.getInstance( ).getLog( ).info( "Cerrada conexion con cilente: " + clientId );
        }
    }
    private void init( ) throws IOException
    {
        String line = read.readLine( );
        if( line.startsWith( "MESA:" ) )
        {
            if( debug )
                System.out.println( new Date( ) + "\t" + clientId + "\t" + line );

            int tableNumber = Integer.parseInt( line.split( ":" )[ 1 ] );
            this.table = server.getTablebyNumber( tableNumber );

            this.table.addClient( this );
            this.slaveNumber = table.getNextSalveNumber( );

            if( slaveNumber == 1 ) // table.getState( ).equals( Table.FREE ) )
            {
                sendMesage( "MAESTRO:" );
                isMaster = true;
            }
            else
            {
                sendMesage( "ESCLAVO:" + slaveNumber );
                isMaster = false;
            }
        }
        line = read.readLine( );
        if( line.startsWith( "MESERO:" ) )
        {
            if( debug )
                System.out.println( new Date( ) + "\t" + clientId + "\t" + line );

            int waitressId = Integer.parseInt( line.split( ":" )[ 1 ] );
            Waitress waitress = server.getWaitressById( waitressId );
            // this.order = new Order( table, waitress );
            table.setWaitress( waitress );
        }
        else
            // TODO multimesa no esta ben
            System.out.println( line );
    }
    private void processLine( String line )
    {
        if( debug )
            System.out.println( new Date( ) + "\t" + clientId + "\t" + line );

        if( line != null && !line.equalsIgnoreCase( "null" ) )
        {
            if( line.startsWith( "ADD:" ) || line.startsWith( "REMOVE:" ) )
            {
                // salveNumber = Integer.parseInt( line.split( ":" )[ 1 ] );// TODO este es el numero del esclavo , o para el master
                int priceItemId = Integer.parseInt( line.split( ":" )[ 1 ] );
                if( line.startsWith( "ADD:" ) )
                {
                    // order.add( server.getPriceItemById( priceItemId ) );
                    table.addPriceItem( server.getPriceItemById( priceItemId ) );
                }
                else if( line.startsWith( "REMOVE:" ) )
                {
                    // order.remove( server.getPriceItemById( priceItemId ) );
                    table.removePriceItem( server.getPriceItemById( priceItemId ) );
                }
                table.sendMesageToAll( line, this );
            }
            else if( line.startsWith( "END:" ) )
            {
                ready = true;
                if( isMaster && table.allClientsReady( ) )// TODO tambien abra que notificar a las otras mesas
                {
                    try
                    {
                        Thread.sleep( 500 );
                    }
                    catch( InterruptedException e )
                    {
                        e.printStackTrace( );
                    }
                    sendMesage( "READY:" );
                }
            }
            else if( line.startsWith( "confirm" ) )
            {
                // server.addOrder( order );
                // order.orderOrdered( );
                // server.notifyOrderReady( );
                confirmOrder( );
            }
        }
        else
        {
            finished = true;
        }
    }
    public boolean isReady( )
    {
        return ready;
    }
    public void confirmOrder( )
    {
        table.confirmOrder( );
        server.confirmOrder( table );
    }

    public void sendMesage( String mess )
    {
        if( debug )
            System.out.println( new Date( ) + "\t" + "SERVER                 " + "\t" + mess );
        write.println( mess );
    }
    
    public String getClientId()
    {
        return clientId;
    }
}
