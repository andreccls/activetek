import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class TestSock
{
    private static BufferedReader read;
    private static PrintWriter write;
    
    public final static void main(String[] ar) throws IOException
    {
        ServerSocket s = new ServerSocket(9999 );
        while(true)
        {
            Socket socket = s.accept( );
            read = new BufferedReader( new InputStreamReader( socket.getInputStream( ) ) );
            write = new PrintWriter( socket.getOutputStream( ) ,true);
            System.out.println(read.readLine( ));
            write.println("MAESTRO" );
        }
    }
}
