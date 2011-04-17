package co.com.activetek.genericmenu.ui.tables;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import co.com.activetek.genericmenu.ui.OsakiMenu;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablesPanelOptions extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JTextField textWidth;
    private JTextField textHeight;
    private OsakiMenu window;
    public TablesPanelOptions( OsakiMenu window, int width, int height )
    {
        this.window = window;
        setBorder( new TitledBorder( null, "Opciones", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
        GridBagLayout gridBagLayout = new GridBagLayout( );
        gridBagLayout.columnWidths = new int[]{ 0, 0, 0, 0, 0 };
        gridBagLayout.rowHeights = new int[]{ 0, 0 };
        gridBagLayout.columnWeights = new double[]{ 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[]{ 0.0, Double.MIN_VALUE };
        setLayout( gridBagLayout );

        JLabel lblDimesiones = new JLabel( "Dimesiones:" );
        GridBagConstraints gbc_lblDimesiones = new GridBagConstraints( );
        gbc_lblDimesiones.insets = new Insets( 0, 0, 0, 5 );
        gbc_lblDimesiones.anchor = GridBagConstraints.EAST;
        gbc_lblDimesiones.gridx = 0;
        gbc_lblDimesiones.gridy = 0;
        add( lblDimesiones, gbc_lblDimesiones );

        textWidth = new JTextField( );
        GridBagConstraints gbc_textField = new GridBagConstraints( );
        gbc_textField.insets = new Insets( 0, 0, 0, 5 );
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 1;
        gbc_textField.gridy = 0;
        add( textWidth, gbc_textField );
        textWidth.setColumns( 10 );
        textWidth.setText( width + "" );
        textWidth.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed( ActionEvent e )
            {
                notifyDimensionChanged();
            }
        } );

        JLabel lblX = new JLabel( "x" );
        GridBagConstraints gbc_lblX = new GridBagConstraints( );
        gbc_lblX.insets = new Insets( 0, 0, 0, 5 );
        gbc_lblX.anchor = GridBagConstraints.EAST;
        gbc_lblX.gridx = 2;
        gbc_lblX.gridy = 0;
        add( lblX, gbc_lblX );

        textHeight = new JTextField( );
        GridBagConstraints gbc_textField_1 = new GridBagConstraints( );
        gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_1.gridx = 3;
        gbc_textField_1.gridy = 0;
        add( textHeight, gbc_textField_1 );
        textHeight.setColumns( 10 );
        textHeight.setText( height + "" );
        textHeight.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed( ActionEvent e )
            {
                notifyDimensionChanged();
            }
        } );
    }
    private void notifyDimensionChanged( )
    {
        try
        {
            int width = Integer.parseInt( textWidth.getText( ) );
            int height = Integer.parseInt( textHeight.getText( ) );
            window.notifyDimensionChanged(width,height);
        }
        catch( NumberFormatException e )
        {
            JOptionPane.showMessageDialog( window, "Intrudusca un valor de dimensiones valido", "ERROR", JOptionPane.ERROR_MESSAGE );
        }
    }
    
}
