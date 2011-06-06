package co.com.activetek.aclocking.ui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import co.com.activetek.aclocking.world.utilities.Utilities;

import com.digitalpersona.onetouch.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DialogAddEmployee extends JDialog implements ActionListener
{
    private JTextField txtNombre;
    private JTextField txtIdentificacion;
    private AClockingUI window;
    private EnumMap<DPFPFingerIndex, DPFPTemplate> templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>( DPFPFingerIndex.class );
    private EnumMap<DPFPFingerIndex, JCheckBox> checkBoxes = new EnumMap<DPFPFingerIndex, JCheckBox>( DPFPFingerIndex.class );

    public DialogAddEmployee( AClockingUI window )
    {
        setTitle( "Agregar Empleado" );
        this.window = window;
        this.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        setSize( 265, 392 );
        JPanel panel = new JPanel( );
        getContentPane( ).add( panel, BorderLayout.CENTER );
        panel.setLayout( new MigLayout( "", "[grow][grow]", "[][][][][grow][grow][grow]" ) );

        JLabel lblNombre = new JLabel( "Nombre" );
        panel.add( lblNombre, "cell 0 0,alignx trailing" );

        txtNombre = new JTextField( );
        panel.add( txtNombre, "cell 1 0,growx" );
        txtNombre.setColumns( 10 );

        JLabel lblCc = new JLabel( "C.C." );
        panel.add( lblCc, "cell 0 1,alignx trailing" );

        txtIdentificacion = new JTextField( );
        panel.add( txtIdentificacion, "cell 1 1,growx" );
        txtIdentificacion.setColumns( 10 );

        JLabel lblTurno = new JLabel( "Horario" );
        panel.add( lblTurno, "cell 0 3,alignx trailing" );

        JComboBox comboBox = new JComboBox( );
        panel.add( comboBox, "cell 1 3,growx" );

        JPanel fingersPanel = new JPanel( new GridBagLayout( ) );
        fingersPanel.setBorder( BorderFactory.createTitledBorder( "Huellas Registradas" ) );
        for( DPFPFingerIndex finger : DPFPFingerIndex.values( ) )
        {
            JCheckBox jCheckBox = new JCheckBox( Utilities.fingerName( finger ) );
            GridBagConstraints gridBagConstraints = new GridBagConstraints( );
            final int rows = DPFPFingerIndex.values( ).length / 2;
            gridBagConstraints.gridx = finger.ordinal( ) / rows;
            gridBagConstraints.gridy = rows - 1 - Math.abs( rows - 1 - finger.ordinal( ) ) + gridBagConstraints.gridx;
            gridBagConstraints.anchor = GridBagConstraints.WEST;
            fingersPanel.add( jCheckBox, gridBagConstraints );
            checkBoxes.put( finger, jCheckBox );

            final DPFPFingerIndex dummyFinger = finger;
            jCheckBox.addActionListener( new ActionListener( )
            {
                DPFPFingerIndex index;
                {
                    index = dummyFinger;
                }
                public void actionPerformed( ActionEvent e )
                {
                    JCheckBox cb = ( JCheckBox )e.getSource( );
                    if( cb.isSelected( ) )
                    {
                        JOptionPane.showMessageDialog( DialogAddEmployee.this, "Para registrar una huella haga click en \"Registar Huella\"", "Fingerprint Enrollment", JOptionPane.INFORMATION_MESSAGE );
                        cb.setSelected( false );
                        // templates.put(index, fakeTemplate);
                    }
                    else
                    {
                        if( JOptionPane.showConfirmDialog( DialogAddEmployee.this, "¿Esta seguro de que quiere remover la huella " + Utilities.fingerprintName( index ) + "?", "Fingerprint Enrollment", JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION )
                            templates.remove( index );
                        else
                            cb.setSelected( true );
                    }
                    Refresh( );
                }
            } );
        }
        panel.add( fingersPanel, "cell 0 4 2 1,grow" );

        JPanel panel_1 = new JPanel( );
        panel.add( panel_1, "cell 0 5 2 1,grow" );

        JButton btnRegistrarHuella = new JButton( "Registrar Huella" );
        btnRegistrarHuella.setActionCommand( "REGISTRY" );
        btnRegistrarHuella.addActionListener( this );
        panel_1.add( btnRegistrarHuella );

        JPanel panel_2 = new JPanel( );
        panel.add( panel_2, "cell 0 6 2 1,grow" );

        JButton btnAceptar = new JButton( "Aceptar" );
        panel_2.add( btnAceptar );

        JButton btnCancelar = new JButton( "Cancelar" );
        btnCancelar.setActionCommand( "CANCEL" );
        btnCancelar.addActionListener( this );
        panel_2.add( btnCancelar );

    }
    private void Refresh( )
    {
        // update enrolled fingers checkboxes
        for( DPFPFingerIndex finger : DPFPFingerIndex.values( ) )
            checkBoxes.get( finger ).setSelected( templates.containsKey( finger ) );

    }
    @Override
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );
        if( command.equals( "CANCEL" ) )
        {
            this.dispose( );
        }
        if( command.equals( "REGISTRY" ) )
        {
            new EnrollmentDialog( this, 10, null, templates ).setVisible( true );
        }
    }
}
