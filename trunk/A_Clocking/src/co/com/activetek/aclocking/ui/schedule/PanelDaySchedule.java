package co.com.activetek.aclocking.ui.schedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelDaySchedule extends JPanel implements ActionListener
{
    private JTextField textField;
    private DialogAddEditSchedule owner;
    private JTextField textField_1;
    private JCheckBox chckbxNewCheckBox;

    public PanelDaySchedule( String day, DialogAddEditSchedule owner )
    {
        this.owner = owner;
        setLayout( new MigLayout( "", "[grow]", "[][][][]" ) );

        JLabel lblDay = new JLabel( day );
        add( lblDay, "cell 0 0" );

        chckbxNewCheckBox = new JCheckBox( );
        chckbxNewCheckBox.setSelected( true );
        chckbxNewCheckBox.setHorizontalAlignment( SwingConstants.CENTER );
        chckbxNewCheckBox.addActionListener( this );
        add( chckbxNewCheckBox, "cell 0 1" );

        textField = new JTextField( );
        textField.setText( "08:00" );
        textField.setColumns( 5 );
        add( textField, "cell 0 2,growx" );
        textField.setColumns( 10 );

        textField_1 = new JTextField( );
        textField_1.setText( "18:00" );
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
    }

}
