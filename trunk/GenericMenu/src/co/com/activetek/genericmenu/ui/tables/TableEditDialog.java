package co.com.activetek.genericmenu.ui.tables;

import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;

import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.ui.ActiveMenu;

public class TableEditDialog extends JDialog
{
    private JTextField textFieldTableNumber;
    private JTextField textFieldCapacity;
    private ActiveMenu window;
    private Table table;
    private TablePanel father;
    private int x;
    private int y;
    
    public TableEditDialog( Table table, int mouseX, int mouseY, ActiveMenu swindow, TablePanel tablePanel, int x, int y )
    {
        this.window = swindow;
        this.table = table;
        this.father = tablePanel;
        this.x = x;
        this.y = y;
        
        if( table != null )
            setTitle( "Editar Mesa" );
        else
            setTitle( "Nueva Mesa" );
        GridBagLayout gridBagLayout = new GridBagLayout( );
        gridBagLayout.columnWidths = new int[]{ 0, 0, 0 };
        gridBagLayout.rowHeights = new int[]{ 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[]{ 1.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[]{ 0.0, 0.0, 1.0, Double.MIN_VALUE };
        getContentPane( ).setLayout( gridBagLayout );

        JLabel lblNoMesa = new JLabel( "No Mesa" );
        lblNoMesa.setHorizontalAlignment( SwingConstants.LEFT );
        GridBagConstraints gbc_lblNoMesa = new GridBagConstraints( );
        gbc_lblNoMesa.anchor = GridBagConstraints.EAST;
        gbc_lblNoMesa.insets = new Insets( 0, 0, 5, 5 );
        gbc_lblNoMesa.gridx = 0;
        gbc_lblNoMesa.gridy = 0;
        getContentPane( ).add( lblNoMesa, gbc_lblNoMesa );

        textFieldTableNumber = new JTextField( );
        GridBagConstraints gbc_textFieldTableNumber = new GridBagConstraints( );
        gbc_textFieldTableNumber.insets = new Insets( 0, 0, 5, 0 );
        gbc_textFieldTableNumber.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldTableNumber.gridx = 1;
        gbc_textFieldTableNumber.gridy = 0;
        getContentPane( ).add( textFieldTableNumber, gbc_textFieldTableNumber );
        textFieldTableNumber.setColumns( 10 );
        textFieldTableNumber.setText( table == null ? "" : table.getNumber( ) + "" );

        JLabel lblCapacidad = new JLabel( "Capacidad" );
        lblCapacidad.setHorizontalAlignment( SwingConstants.LEFT );
        GridBagConstraints gbc_lblCapacidad = new GridBagConstraints( );
        gbc_lblCapacidad.anchor = GridBagConstraints.EAST;
        gbc_lblCapacidad.insets = new Insets( 0, 0, 5, 5 );
        gbc_lblCapacidad.gridx = 0;
        gbc_lblCapacidad.gridy = 1;
        getContentPane( ).add( lblCapacidad, gbc_lblCapacidad );

        textFieldCapacity = new JTextField( );
        GridBagConstraints gbc_textFieldCapacity = new GridBagConstraints( );
        gbc_textFieldCapacity.insets = new Insets( 0, 0, 5, 0 );
        gbc_textFieldCapacity.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldCapacity.gridx = 1;
        gbc_textFieldCapacity.gridy = 1;
        getContentPane( ).add( textFieldCapacity, gbc_textFieldCapacity );
        textFieldCapacity.setColumns( 10 );
        textFieldCapacity.setText( table == null ? "" : table.getCapacity( ) + "" );

        JPanel panelButtons = new JPanel( );
        GridBagConstraints gbc_panelButtons = new GridBagConstraints( );
        gbc_panelButtons.gridwidth = 2;
        gbc_panelButtons.insets = new Insets( 0, 0, 0, 5 );
        gbc_panelButtons.fill = GridBagConstraints.BOTH;
        gbc_panelButtons.gridx = 0;
        gbc_panelButtons.gridy = 2;
        getContentPane( ).add( panelButtons, gbc_panelButtons );
        panelButtons.setLayout( new GridLayout( 0, 2, 0, 0 ) );

        JButton btnAceptar = new JButton( "Aceptar" );
        btnAceptar.addActionListener( new ActionListener( )
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                try
                {
                    int number = Integer.parseInt( textFieldTableNumber.getText( ) );
                    int capacity = Integer.parseInt( textFieldCapacity.getText( ) );
                    saveTable( number, capacity );
                    dispose( );
                }
                catch( NumberFormatException e1 )
                {
                    JOptionPane.showMessageDialog( window, "Por favor ingrese valores vaildos ", "ERROR", JOptionPane.ERROR_MESSAGE );
                }

            }
        } );
        panelButtons.add( btnAceptar );

        JButton btnCancelar = new JButton( "Cancelar" );
        btnCancelar.addActionListener( new ActionListener( )
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                dispose( );
            }
        } );
        panelButtons.add( btnCancelar );

        this.setLocation( mouseX + 200, mouseY + 200 );
        this.setSize( new Dimension( 215, 122 ) );
        this.setModal( true );
    }
    private void saveTable( int number, int capacity )
    {
        try
        {
            if(table != null)
            {
                table.setNumber( number );
                table.setCapacity( capacity );
                father.update( number, capacity );
            }
            else
            {
                table = new Table( -1, number, capacity, x, y, Table.FREE , true );
                father.refresh( table );
            }
        }
        catch( SQLException e )
        {
            JOptionPane.showMessageDialog( window, "Error inesperado tratando de acutalizar la mesa \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
            e.printStackTrace( );
        }

    }

}
