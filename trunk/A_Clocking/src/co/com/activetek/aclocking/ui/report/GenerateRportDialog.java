package co.com.activetek.aclocking.ui.report;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

import co.com.activetek.aclocking.ui.AClockingUI;

import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JDateChooser;

public class GenerateRportDialog extends JDialog implements ActionListener
{
    public final static String REPORT_NAME = "ReporteEmpleados_DATE1_DATE2";
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // private JTextField txtInidate;
    // private JTextField txtEnddate;
    private JDateChooser chooser_ini;
    private JDateChooser chooser_end;
    private AClockingUI window;
    private JLabel lblFile;
    private SimpleDateFormat sdf;
    private File fileReport;
    private File defaultDirectory;

    public GenerateRportDialog( AClockingUI window )
    {
        this.window = window;
        this.sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        setSize( 499, 165 );
        getContentPane( ).setLayout( new MigLayout( "", "[grow,right][grow]", "[][][][grow]" ) );

        JLabel lblFechaInicio = new JLabel( "Fecha inicio:" );
        getContentPane( ).add( lblFechaInicio, "cell 0 0,alignx trailing" );

        // txtInidate = new JTextField( );
        // txtInidate.setText( "ini_date" );
        // getContentPane( ).add( txtInidate, "cell 1 0,growx" );
        // txtInidate.setColumns( 10 );
        chooser_ini = new JDateChooser( Calendar.getInstance( ).getTime( ) );
        IDateEditor icoso = ( IDateEditor )chooser_ini.getDateEditor( );
        icoso.getUiComponent( ).addFocusListener( new FocusListener( )
        {

            @Override
            public void focusLost( FocusEvent e )
            {
                // TODO Auto-generated method stub
                fileReport = new File( defaultDirectory, REPORT_NAME.replace( "DATE1", sdf.format( chooser_ini.getDate( ) ) ).replace( "DATE2", sdf.format( chooser_end.getDate( ) ) ) + ".xls" );
                lblFile.setText( fileReport.getAbsolutePath( ) );
            }

            @Override
            public void focusGained( FocusEvent e )
            {
            }
        } );
        getContentPane( ).add( chooser_ini, "cell 1 0,growx" );

        JLabel lblFechaFin = new JLabel( "Fecha fin:" );
        getContentPane( ).add( lblFechaFin, "cell 0 1,alignx trailing" );

        // txtEnddate = new JTextField( );
        // txtEnddate.setText( "end_date" );
        // getContentPane( ).add( txtEnddate, "cell 1 1,growx" );
        // txtEnddate.setColumns( 10 );
        chooser_end = new JDateChooser( Calendar.getInstance( ).getTime( ) );
        IDateEditor icoso2 = ( IDateEditor )chooser_end.getDateEditor( );
        icoso2.getUiComponent( ).addFocusListener( new FocusListener( )
        {

            @Override
            public void focusLost( FocusEvent arg0 )
            {
                fileReport = new File( defaultDirectory, REPORT_NAME.replace( "DATE1", sdf.format( chooser_ini.getDate( ) ) ).replace( "DATE2", sdf.format( chooser_end.getDate( ) ) ) + ".xls" );
                lblFile.setText( fileReport.getAbsolutePath( ) );
            }

            @Override
            public void focusGained( FocusEvent arg0 )
            {
            }
        } );
        getContentPane( ).add( chooser_end, "cell 1 1,growx" );

        JButton btnExportarA = new JButton( "Exportar a:" );
        btnExportarA.setHorizontalAlignment( SwingConstants.RIGHT );
        getContentPane( ).add( btnExportarA, "cell 0 2" );
        btnExportarA.addActionListener( this );
        btnExportarA.setActionCommand( "EXPORT_FILE" );

        JFileChooser fileChooser = new JFileChooser( );
        FileSystemView fw = fileChooser.getFileSystemView( );
        defaultDirectory = fw.getDefaultDirectory( );
        fileReport = new File( defaultDirectory, REPORT_NAME.replace( "DATE1", sdf.format( chooser_ini.getDate( ) ) ).replace( "DATE2", sdf.format( chooser_end.getDate( ) ) ) + ".xls" );
        lblFile = new JLabel( fileReport.getAbsolutePath( ) );
        getContentPane( ).add( lblFile, "cell 1 2" );

        JPanel panel = new JPanel( );
        getContentPane( ).add( panel, "cell 0 3 2 1,grow" );

        JButton btnAceptar = new JButton( "aceptar" );
        btnAceptar.setActionCommand( "ACEPT" );
        btnAceptar.addActionListener( this );
        panel.add( btnAceptar );

        JButton btnCancelar = new JButton( "cancelar" );
        btnCancelar.setActionCommand( "CANCEL" );
        btnCancelar.addActionListener( this );
        panel.add( btnCancelar );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );
        if( command.equals( "ACEPT" ) )
        {
            window.generateRerport( fileReport, chooser_ini.getDate( ), chooser_end.getDate( ) );
            this.dispose( );
        }
        else if( command.equals( "CANCEL" ) )
        {
            super.dispose( );
        }
        else if( command.equals( "EXPORT_FILE" ) )
        {
            JFileChooser fileChooser = new JFileChooser( );
            FileSystemView fw = fileChooser.getFileSystemView( );
            fileChooser.setCurrentDirectory( fw.getDefaultDirectory( ) );
            int status = fileChooser.showOpenDialog( null );
            // fileChooser.setDefaultLocale( arg0 );
            if( status == JFileChooser.APPROVE_OPTION )
            {
                File selectedFile = fileChooser.getSelectedFile( );
                String path = selectedFile.getAbsolutePath( );
                if( !path.toLowerCase( ).endsWith( ".xls" ) )
                    path += ".xls";
                fileReport = new File( path );
                lblFile.setText( fileReport.getAbsolutePath( ) );
            }
            else if( status == JFileChooser.CANCEL_OPTION )
            {

            }
        }
    }

}
