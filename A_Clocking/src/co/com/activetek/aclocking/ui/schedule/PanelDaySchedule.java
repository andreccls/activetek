package co.com.activetek.aclocking.ui.schedule;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelDaySchedule extends JPanel
{
    private JTextField textField;
    private DialogAddEditSchedule owner;
    
    public PanelDaySchedule( String day , DialogAddEditSchedule owner)
    {
        this.owner = owner;
        setLayout( new MigLayout( "", "[grow]", "[][][]" ) );

        JLabel lblDay = new JLabel( day );
        add( lblDay, "cell 0 0" );

        JCheckBox chckbxNewCheckBox = new JCheckBox( );
        chckbxNewCheckBox.setSelected( true );
        chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        add( chckbxNewCheckBox, "cell 0 1" );

        textField = new JTextField( );
        textField.setText( "08:00" );
        textField.setColumns( 5 );
        add( textField, "cell 0 2,growx" );
        textField.setColumns( 10 );
    }

}
