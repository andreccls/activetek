package co.com.activetek.aclocking.ui.schedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import co.com.activetek.aclocking.entitybeans.Schedule;

public class PanelDaySchedule extends JPanel implements ActionListener
{
    private JTextField textField;
    private DialogAddEditSchedule owner;
    private JTextField textField_1;
    private JCheckBox chckbxNewCheckBox;
    private Schedule s;
    private String day;
    public PanelDaySchedule( String day, DialogAddEditSchedule owner, Schedule schedule )
    {
        this.owner = owner;
        this.s = schedule;
        this.day = day;
        setLayout( new MigLayout( "", "[grow]", "[][][][]" ) );

        JLabel lblDay = new JLabel( day );
        add( lblDay, "cell 0 0" );

        chckbxNewCheckBox = new JCheckBox( );
        chckbxNewCheckBox.setSelected( true );
        chckbxNewCheckBox.setHorizontalAlignment( SwingConstants.CENTER );
        chckbxNewCheckBox.addActionListener( this );
        add( chckbxNewCheckBox, "cell 0 1" );

        textField = new JTextField( );
        textField.setText( "08:00:00" );
        textField.setColumns( 5 );
        add( textField, "cell 0 2,growx" );
        textField.setColumns( 10 );

        textField_1 = new JTextField( );
        textField_1.setText( "18:00:00" );
        add( textField_1, "cell 0 3,growx" );
        textField_1.setColumns( 10 );

        refresh( );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        refresh( );
    }
    public void refresh( )
    {
        textField.setEditable( chckbxNewCheckBox.isSelected( ) );
        textField_1.setEditable( chckbxNewCheckBox.isSelected( ) );
        if( s != null )
        {
            if( day.equals( "lunes" ) )
            {
                textField.setText( s.getLunes( ) );
                textField_1.setText( s.getLunes_out( ) );
                chckbxNewCheckBox.setSelected( s.isLunes( ) );
            }
            else if( day.equals( "martes" ) )
            {
                textField.setText( s.getMartes( ) );
                textField_1.setText( s.getMartes_out( ) );
                chckbxNewCheckBox.setSelected( s.isMartes( ) );
            }
            else if( day.equals( "miercoles" ) )
            {
                textField.setText( s.getMiercoles( ) );
                textField_1.setText( s.getMiercoles_out( ) );
                chckbxNewCheckBox.setSelected( s.isMiercoles( ) );
            }
            else if( day.equals( "jueves" ) )
            {
                textField.setText( s.getJueves( ) );
                textField_1.setText( s.getJueves_out( ) );
                chckbxNewCheckBox.setSelected( s.isJueves( ) );
            }
            else if( day.equals( "viernes" ) )
            {
                textField.setText( s.getViernes( ) );
                textField_1.setText( s.getViernes_out( ) );
                chckbxNewCheckBox.setSelected( s.isViernes( ) );
            }
            else if( day.equals( "sabado" ) )
            {
                textField.setText( s.getSabado( ) );
                textField_1.setText( s.getSabado_out( ) );
                chckbxNewCheckBox.setSelected( s.isSabado( ) );
            }
            else if( day.equals( "domingo" ) )
            {
                textField.setText( s.getDomingo( ) );
                textField_1.setText( s.getDomingo_out( ) );
                chckbxNewCheckBox.setSelected( s.isDomingo( ) );
            }
            else if( day.equals( "festivo" ) )
            {
                textField.setText( s.getFestivo( ) );
                textField_1.setText( s.getFestivo_out( ) );
                chckbxNewCheckBox.setSelected( s.isFestivo( ) );
            }
        }
    }
    public String getIniHour( )
    {
        return textField.getText( ).trim( );
    }
    public String getEndHour( )
    {
        return textField_1.getText( ).trim( );
    }
    public boolean isSelected( )
    {
        return chckbxNewCheckBox.isSelected( );
    }
    public boolean dataOk( )
    {
        return hourOk( textField.getText( ) ) && hourOk( textField_1.getText( ) );
    }
    private boolean hourOk( String hour )
    {
        String[] split = hour.split( ":" );
        if( split.length != 3 )
        {
            return false;
        }
        try
        {
            int num = Integer.parseInt( split[ 0 ] );
            if( num > 23 || num < 0 )
                return false;
        }
        catch( NumberFormatException e )
        {
            return false;
        }
        try
        {
            int num = Integer.parseInt( split[ 1 ] );
            if( num > 60 || num < 0 )
                return false;
        }
        catch( NumberFormatException e )
        {
            return false;
        }
        return true;
    }
}