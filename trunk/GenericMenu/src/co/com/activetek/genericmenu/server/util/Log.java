package co.com.activetek.genericmenu.server.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log
{
    private static Log instance;
    private static String LOG_DIRECTORY = "./log/out.log";

    Logger log = Logger.getLogger( "Log" );

    public Log( )
    {
        FileHandler hand;
        try
        {
            hand = new FileHandler( LOG_DIRECTORY, true );
            SimpleFormatter formatter = new SimpleFormatter( );
            hand.setFormatter( formatter );
            log.addHandler( hand );
        }
        catch( SecurityException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace( );
        }
        catch( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace( );
        }

    }

    public static Log getInstance( )
    {
        if( instance == null )
            instance = new Log( );
        return instance;
    }

    public Logger getLog( )
    {
        return log;
    }

}
