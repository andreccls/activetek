package co.com.activetek.aclocking.world;

import java.awt.TrayIcon;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.LinkedBlockingQueue;

import co.com.activetek.aclocking.entitybeans.Employee;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.readers.DPFPReadersCollection;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

public class VerificationThread extends Thread
{

    /**
     * Enrollment control test
     */
    private ArrayList<Employee> employees;
    private int farAchieved;
    private boolean matched;
    private TrayIcon trayIcon;
    private AClock aclock;

    static final String FAR_PROPERTY = "FAR";
    static final String MATCHED_PROPERTY = "Matched";
    private SimpleDateFormat sdf;
    private SimpleDateFormat sdf2;

    public VerificationThread( ArrayList<Employee> emp, AClock aclock, TrayIcon trayIconn )
    {
        this.aclock = aclock;
        employees = emp;
        trayIcon = trayIconn;
        sdf = new SimpleDateFormat( "HH:mm" );
        sdf2 = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        //sdf2 = new SimpleDateFormat( "dd/mm/yyyy HH:mm:ss" );
    }

    public void run( )
    {
        while( true )
        {
            try
            {
                verify( selectReader( ) );
            }
            catch( IndexOutOfBoundsException e )
            {
                trayIcon.displayMessage( "Active Clocking", "Por favor conecte el lector de huella al computador", TrayIcon.MessageType.ERROR );
            }
        }
    }

    private void verify( String activeReader )
    {
        try
        {
            DPFPSample sample = getSample( activeReader );
            if( sample == null )
                throw new Exception( );

            DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory( ).createFeatureExtraction( );
            DPFPFeatureSet featureSet = featureExtractor.createFeatureSet( sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION );

            DPFPVerification matcher = DPFPGlobal.getVerificationFactory( ).createVerification( );
            matcher.setFARRequested( DPFPVerification.MEDIUM_SECURITY_FAR );

            int bestFAR = DPFPVerification.PROBABILITY_ONE;
            boolean hasMatch = false;
            for( Employee empl : VerificationThread.this.employees )
            {
                for( DPFPTemplate template : empl.getTemplates( ).values( ) )
                {
                    DPFPVerificationResult result = matcher.verify( featureSet, template ); // report matching status
                    bestFAR = Math.min( bestFAR, result.getFalseAcceptRate( ) );
                    if( result.isVerified( ) )
                    {
                        hasMatch = true;

                        Calendar c = Calendar.getInstance( );
                        String hour = sdf.format( c.getTime( ) );
                        trayIcon.displayMessage( empl.getNombre( ), "Hora: " + hour, TrayIcon.MessageType.INFO );
                        aclock.addEvent( empl, sdf2.format( c.getTime( ) ) );
                        // System.out.println("El empleado en verificación es: "+empl.getNombre());
                        break;
                    }
                }
                if( hasMatch )
                    break;
            }
            if( !hasMatch )
            {
                trayIcon.displayMessage( "Error", "La persona en el lector no está registrada en la base de datos", TrayIcon.MessageType.ERROR );
                // System.out.println("La persona en el lector no está registrada en la base de datos");
            }

        }
        catch( Exception e )
        {
            System.out.printf( "Failed to perform verification." );
            e.printStackTrace( );
        }
    }

    private DPFPSample getSample( String activeReader ) throws InterruptedException
    {
        final LinkedBlockingQueue<DPFPSample> samples = new LinkedBlockingQueue<DPFPSample>( );
        DPFPCapture capture = DPFPGlobal.getCaptureFactory( ).createCapture( );
        capture.setReaderSerialNumber( activeReader );
        capture.setPriority( DPFPCapturePriority.CAPTURE_PRIORITY_LOW );
        capture.addDataListener( new DPFPDataListener( )
        {
            public void dataAcquired( DPFPDataEvent e )
            {
                if( e != null && e.getSample( ) != null )
                {
                    try
                    {
                        samples.put( e.getSample( ) );
                    }
                    catch( InterruptedException e1 )
                    {
                        e1.printStackTrace( );
                    }
                }
            }
        } );
        capture.addReaderStatusListener( new DPFPReaderStatusAdapter( )
        {
            int lastStatus = DPFPReaderStatusEvent.READER_CONNECTED;
            public void readerConnected( DPFPReaderStatusEvent e )
            {
                if( lastStatus != e.getReaderStatus( ) )
                    trayIcon.displayMessage( "Active Clocking", "Lector de huellas conectado", TrayIcon.MessageType.INFO );
                lastStatus = e.getReaderStatus( );
            }
            public void readerDisconnected( DPFPReaderStatusEvent e )
            {
                if( lastStatus != e.getReaderStatus( ) )
                    trayIcon.displayMessage( "Active Clocking", "Se ha desconectado el lector de huella, por favor conectelo nuevamente", TrayIcon.MessageType.ERROR );
                lastStatus = e.getReaderStatus( );
            }

        } );
        try
        {
            capture.startCapture( );
            return samples.take( );
        }
        catch( RuntimeException e )
        {
            System.out.printf( "Failed to start capture. Check that reader is not used by another application.\n" );
            throw e;
        }
        finally
        {
            capture.stopCapture( );
        }
    }

    public String selectReader( ) throws IndexOutOfBoundsException
    {
        DPFPReadersCollection readers = DPFPGlobal.getReadersFactory( ).getReaders( );
        return readers.get( 0 ).getSerialNumber( );
    }

    public int getFAR( )
    {
        return farAchieved;
    }

    protected void setFAR( int far )
    {
        farAchieved = far;
        System.out.println( "" + farAchieved );
    }

    public boolean getMatched( )
    {
        return matched;
    }

    protected void setMatched( boolean matched )
    {
        this.matched = matched;
        System.out.println( "" + matched );
    }
}