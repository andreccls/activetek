package co.com.activetek.aclocking.ui.employee;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.ui.swing.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Enrollment control test
 */
public class EnrollmentDialog extends JDialog
{
    private EnumMap<DPFPFingerIndex, DPFPTemplate> templates;
    private DialogAddEditEmployee owner;
    public EnrollmentDialog( DialogAddEditEmployee owner, int maxCount, final String reasonToFail, EnumMap<DPFPFingerIndex, DPFPTemplate> templates )
    {
        this.templates = templates;
        this.owner = owner;

        setTitle( "Registro de huella" );

        DPFPEnrollmentControl enrollmentControl = new DPFPEnrollmentControl( );

        EnumSet<DPFPFingerIndex> fingers = EnumSet.noneOf( DPFPFingerIndex.class );
        fingers.addAll( templates.keySet( ) );
        enrollmentControl.setEnrolledFingers( fingers );
        enrollmentControl.setMaxEnrollFingerCount( maxCount );

        enrollmentControl.addEnrollmentListener( new DPFPEnrollmentListener( )
        {
            public void fingerDeleted( DPFPEnrollmentEvent e ) throws DPFPEnrollmentVetoException
            {
                if( reasonToFail != null )
                {
                    throw new DPFPEnrollmentVetoException( reasonToFail );
                }
                else
                {
                    // EnrollmentDialog.this.templates.remove( e.getFingerIndex( ) );//logica pasada el mundo
                    EnrollmentDialog.this.owner.removeFinger( e );
                }                
            }

            public void fingerEnrolled( DPFPEnrollmentEvent e ) throws DPFPEnrollmentVetoException
            {
                if( reasonToFail != null )
                {
                    // e.setStopCapture(false);
                    throw new DPFPEnrollmentVetoException( reasonToFail );
                }
                else
                {
                    // EnrollmentDialog.this.templates.put( e.getFingerIndex( ), e.getTemplate( ) ); //logica pasada al mundo
                    EnrollmentDialog.this.owner.enrollFinger( e );
                }                
            }
        } );

        getContentPane( ).setLayout( new BorderLayout( ) );

        JButton closeButton = new JButton( "Close" );
        closeButton.addActionListener( new ActionListener( )
        {
            public void actionPerformed( ActionEvent e )
            {
                setVisible( false ); // End Dialog
            }
        } );

        JPanel bottom = new JPanel( );
        bottom.add( closeButton );
        add( enrollmentControl, BorderLayout.CENTER );
        add( bottom, BorderLayout.PAGE_END );

        pack( );
        setLocationRelativeTo( null );
    }
}