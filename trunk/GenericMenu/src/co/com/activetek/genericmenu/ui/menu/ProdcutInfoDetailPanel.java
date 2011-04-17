package co.com.activetek.genericmenu.ui.menu;

import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.beans.PriceItem;
import co.com.activetek.genericmenu.ui.OsakiMenu;
import java.awt.Insets;

/**
 * Este panel muestra los detalles del producto (opciones de precios) especidficiones, ademas permite borrar y activar/desactivar el producto
 * @author daniel.rodriguez
 * 
 */
public class ProdcutInfoDetailPanel extends JPanel
{

    // --------------------------------------------------------------------------------------------
    // Constantes
    // --------------------------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    // --------------------------------------------------------------------------------------------
    // Atributos
    // --------------------------------------------------------------------------------------------
    private JCheckBox checkBoxEnableProduct = null;
    private JButton buttonDeleteProduct = null;
    private JLabel labelDetails = null;
    private JTextArea textFieldDetails = null;
    private JScrollPane scrollPaneDetails = null;

    private JScrollPane jScrollPane = null;

    private JTable jTable = null;
    private PricesTableRender pricesTableRender = null;

    private MenuItem selected;
    private OsakiMenu window;
    private JButton btnGuardar;
    // --------------------------------------------------------------------------------------------
    // Constructores
    // --------------------------------------------------------------------------------------------
    /**
     * This is the default constructor
     */
    public ProdcutInfoDetailPanel( OsakiMenu window )
    {
        super( );
        this.window = window;
        initialize( );
    }

    // --------------------------------------------------------------------------------------------
    // Metodos
    // --------------------------------------------------------------------------------------------
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize( )
    {

        GridBagConstraints gridBagConstraints0 = new GridBagConstraints( );// Para la table de precios
        gridBagConstraints0.insets = new Insets( 0, 0, 5, 0 );
        gridBagConstraints0.fill = GridBagConstraints.BOTH;
        gridBagConstraints0.gridx = 0;
        gridBagConstraints0.gridy = 0;
        gridBagConstraints0.weightx = 1;
        gridBagConstraints0.weighty = 0.4;
        gridBagConstraints0.gridwidth = 2;

        GridBagConstraints gridBagConstraints1 = new GridBagConstraints( );// Para el label detalles
        gridBagConstraints1.insets = new Insets( 0, 0, 5, 5 );
        gridBagConstraints1.fill = GridBagConstraints.BOTH;
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.weightx = 1;
        gridBagConstraints1.gridheight = 1;

        GridBagConstraints gridBagConstraints2 = new GridBagConstraints( );// Para el textField de detalles
        gridBagConstraints2.insets = new Insets( 0, 0, 5, 0 );
        gridBagConstraints2.fill = GridBagConstraints.BOTH;
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.gridy = 2;
        gridBagConstraints2.weightx = 1;
        gridBagConstraints2.weighty = 0.4;
        gridBagConstraints2.gridwidth = 2;

        GridBagConstraints gridBagConstraints3 = new GridBagConstraints( );// Para el chek de enable
        gridBagConstraints3.insets = new Insets( 0, 0, 0, 5 );
        gridBagConstraints3.fill = GridBagConstraints.BOTH;
        gridBagConstraints3.gridx = 0;
        gridBagConstraints3.gridy = 3;
        gridBagConstraints3.weightx = 0.8;
        gridBagConstraints3.gridheight = 1;

        GridBagConstraints gridBagConstraints4 = new GridBagConstraints( );// Para el chek de enable
        gridBagConstraints4.fill = GridBagConstraints.BOTH;
        gridBagConstraints4.gridx = 1;
        gridBagConstraints4.gridy = 3;
        gridBagConstraints4.weightx = 0.2;
        gridBagConstraints4.gridheight = 1;

        labelDetails = new JLabel( );
        labelDetails.setText( "Detalles:" );

        this.setSize( 300, 200 );
        this.setLayout( new GridBagLayout( ) );
        this.setBorder( BorderFactory.createTitledBorder( null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null ) );

        this.add( getJScrollPane( ), gridBagConstraints0 );
        this.add( labelDetails, gridBagConstraints1 );
        GridBagConstraints gbc_btnGuardar = new GridBagConstraints( );
        gbc_btnGuardar.insets = new Insets( 0, 0, 5, 0 );
        gbc_btnGuardar.gridx = 1;
        gbc_btnGuardar.gridy = 1;
        add( getBtnGuardar( ), gbc_btnGuardar );
        this.add( getScrollPaneDetails( ), gridBagConstraints2 );
        this.add( getCheckBoxEnableProduct( ), gridBagConstraints3 );
        this.add( getButtonDeleteProduct( ), gridBagConstraints4 );
    }

    /**
     * This method initializes checkBoxEnableProduct
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCheckBoxEnableProduct( )
    {
        if( checkBoxEnableProduct == null )
        {
            checkBoxEnableProduct = new JCheckBox( "Mostrar" );
            checkBoxEnableProduct.addItemListener( new ItemListener( )
            {

                @Override
                public void itemStateChanged( ItemEvent arg0 )
                {
                    try
                    {
                        selected.setEnable( checkBoxEnableProduct.isSelected( ) );
                    }
                    catch( SQLException e )
                    {
                        JOptionPane.showMessageDialog( window, "Error inesperado tratando de actualizar el item \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
                        e.printStackTrace( );
                    }
                }
            } );
        }
        return checkBoxEnableProduct;
    }
    private JButton getBtnGuardar( )
    {
        if( btnGuardar == null )
        {
            btnGuardar = new JButton( "Guardar" );
            btnGuardar.addActionListener( new ActionListener( )
            {

                @Override
                public void actionPerformed( ActionEvent e )
                {
                    try
                    {
                        selected.setDescription( textFieldDetails.getText( ) );
                    }
                    catch( SQLException e1 )
                    {
                        JOptionPane.showMessageDialog( window, "Error inesperado tratando de actualizar el item \n " + e1.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
                        e1.printStackTrace( );
                    }
                }
            } );
        }
        return btnGuardar;
    }
    /**
     * This method initializes buttonDeleteProduct
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonDeleteProduct( )
    {
        if( buttonDeleteProduct == null )
        {
            buttonDeleteProduct = new JButton( "Borrar producto" );
        }
        return buttonDeleteProduct;
    }

    /**
     * This method initializes textFieldDetails
     * 
     * @return javax.swing.JTextField
     */
    private JTextArea getTextFieldDetails( )
    {
        if( textFieldDetails == null )
        {
            textFieldDetails = new JTextArea( "Relleno de langostinos frescos blanqueados, con mango dulce, kani-kama, mix de lechugas y salsa dinamita, cubierto de masago " );
            textFieldDetails.setEnabled( true );// TODO cambiar con la cuenta de administardor
            textFieldDetails.setLineWrap( true );
        }
        return textFieldDetails;
    }

    /**
     * This method initializes scrollPaneDetails
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getScrollPaneDetails( )
    {
        if( scrollPaneDetails == null )
        {
            scrollPaneDetails = new JScrollPane( );
            scrollPaneDetails.setViewportView( getTextFieldDetails( ) );
        }
        return scrollPaneDetails;
    }
    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane( )
    {
        if( jScrollPane == null )
        {
            jScrollPane = new JScrollPane( );
            jScrollPane.setViewportView( getJTable( ) );
        }
        return jScrollPane;
    }
    /**
     * This method initializes jTable
     * 
     * @return javax.swing.JTable
     */
    private JTable getJTable( )
    {
        if( jTable == null )
        {
            pricesTableRender = new PricesTableRender( );
            jTable = new JTable( pricesTableRender );
        }
        return jTable;
    }
    public void setSelectedItem( MenuItem selected )
    {
        this.selected = selected;
        pricesTableRender.setSelected( selected );
        textFieldDetails.setText( selected.getDescription( ) );
        jTable.repaint( );
        checkBoxEnableProduct.setSelected( selected.isEnable( ) );
        this.repaint( );
    }
    // --------------------------------------------------------------------------------------------
    // Clases auxiliares
    // --------------------------------------------------------------------------------------------
    /**
     * 
     * @author daniel.rodriguez
     * 
     */
    public class PricesTableRender extends AbstractTableModel
    {
        private static final long serialVersionUID = 1L;

        String[] columnNames = { "#Items", "Detalles", "Precio", "Enable" };
        Object[][] data = {}; // { 1, "", 20000, true }, { 1, "", 20000, false } };
        Vector<PriceItem> prices;
        public PricesTableRender( )
        {
            super( );
        }
        @Override
        public int getRowCount( )
        {
            return data.length;
        }

        @Override
        public int getColumnCount( )
        {
            return columnNames.length;
        }

        @Override
        public Object getValueAt( int rowIndex, int columnIndex )
        {
            return data[ rowIndex ][ columnIndex ];
        }

        public String getColumnName( int col )
        {
            return columnNames[ col ];
        }
        public Class getColumnClass( int c )
        {
            return c == 3 ? Boolean.class : String.class;
        }
        public boolean isCellEditable( int row, int col )
        {
            return true;// TODO cambiar para que solo lo pueda hacer el usuario de administrador
        }
        public void setValueAt( Object value, int row, int col )
        {
            data[ row ][ col ] = value;
            fireTableCellUpdated( row, col );

            String cuantityStr = data[ row ][ 0 ] + "";
            int cuantitiy = -1;
            try
            {
                if( !cuantityStr.trim( ).equals( "" ) )
                    cuantitiy = Integer.parseInt( cuantityStr );
            }
            catch( NumberFormatException e )
            {
                JOptionPane.showMessageDialog( window, "Ingrese una cantidad valida", "ERROR", JOptionPane.ERROR_MESSAGE );
                return;
            }

            String descripcion = data[ row ][ 1 ] + "";

            String priceStr = data[ row ][ 2 ] + "";
            int price = -1;
            try
            {
                if( !priceStr.trim( ).equals( "" ) )
                    price = Integer.parseInt( priceStr ); 
            }
            catch( NumberFormatException e )
            {
                JOptionPane.showMessageDialog( window, "Ingrese un valor para el precio valido", "ERROR", JOptionPane.ERROR_MESSAGE );
                return;
            }

            boolean enable = new Boolean( value + "" );
                        
            try
            {
                if( row == data.length - 1)// es un preico nuevo
                {
                    PriceItem p = new PriceItem( -1, cuantitiy, descripcion, enable, -1, price, selected.getId( ) );
                    selected.addPriceItem( p );
                    setSelectedItem( selected );
                }
                else
                {
                    PriceItem p = prices.get( row );
                    p.modify( cuantitiy, descripcion, enable, p.getOrder( ), price );//TODO toca solucionar lo del orden 
                }
            }
            catch( SQLException e )
            {
                JOptionPane.showMessageDialog( window, "Error inesperado tratando de guardar los cambios \n" + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
                e.printStackTrace();
            }
        }
        public void setSelected( MenuItem menuitem )
        {
            System.out.println(123);
            prices = menuitem.getPrices( );
            this.data = new Object[prices.size( ) + 1][columnNames.length];

            for( int i = 0; i < prices.size( ); i++ )
            {
                PriceItem p = prices.get( i );
                data[ i ][ 0 ] = p.getCuantity( );
                data[ i ][ 1 ] = p.getDescripcion( );
                data[ i ][ 2 ] = p.getPrice( );
                data[ i ][ 3 ] = p.isEnable( );
            }

            data[ prices.size( ) ][ 0 ] = "";// TODO aqui deberia tener un tono de letra menos relevante, mostrarse solo cuando esta en cuenta de administrador
            data[ prices.size( ) ][ 1 ] = "Nuevo Precio";
            data[ prices.size( ) ][ 2 ] = "";
            data[ prices.size( ) ][ 3 ] = true;
        }

    }
}
