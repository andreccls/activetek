package co.com.activetek.genericmenu.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase encargada escuchar las conexiones de los nuevos clietes, y crear un socket y thread para cada uno
 * @author daniel.rodriguez
 * 
 */
public class ListenerThread extends Thread
{

    /**
     * referencia a la clase principal del mundo
     */
    private ActiveMenuServer main;
    private int port;

    private ServerSocket serverSocket;

    public ListenerThread( ActiveMenuServer genericMenuServer, int puerto ) throws IOException
    {
        this.main = genericMenuServer;
        this.port = puerto;
        serverSocket = new ServerSocket( port );
    }

    public void run( )
    {
        try
        {            
            while( true )
            {
                Socket newSocket = serverSocket.accept( );
                ClientThread newClient = new ClientThread( newSocket, main );
                newClient.start( );                
            }
        }
        catch( IOException e )
        {
            // TODO Que se deberia hacer con esta excepcion??
            e.printStackTrace( );
        }

    }

}