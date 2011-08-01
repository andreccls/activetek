package co.com.activetek.genericmenu.ui.menu;

import java.awt.Component;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.beans.PriceItem;
import co.com.activetek.genericmenu.ui.ActiveMenu;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;

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
    /**
     * Valor uado por PriceTableCellEditor para notificar a PricesTableModel que la accion corresponde a borrar el precio
     */
    private static final String DELETE_PRICE_ITEM = "DELETE_PRICE_ITEM";

    // --------------------------------------------------------------------------------------------
    // Atributos
    // --------------------------------------------------------------------------------------------
    private JCheckBox checkBoxEnableProduct = null;
    private JButton buttonDeleteProduct = null;
    private JLabel labelDetails = null;
    private JTextArea textFieldDetails = null;
    private JScrollPane scrollPaneDetails = null;
    private PricesPanel pricesPanel = null;
    private JScrollPane jScrollPane = null;

    private JTable jTable = null;
    private PricesTableModel pricesTableRender = null;

    private MenuItem selected;
    private ActiveMenu window;
    private JButton btnGuardar;
    // --------------------------------------------------------------------------------------------
    // Constructores
    // --------------------------------------------------------------------------------------------
    /**
     * This is the default constructor
     */
    public ProdcutInfoDetailPanel( ActiveMenu window )
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

        labelDetails = new JLabel( );
        labelDetails.setText( "Detalles:" );

        this.setSize( 318, 364 );
        this.setBorder( BorderFactory.createTitledBorder( null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null ) );
        setLayout(new MigLayout("", "[155px][5px][127px]", "[160px:160px,top][23px,top][63px][23px]"));

        this.add( getJScrollPane( ), "cell 0 0 3 1,grow" );
        this.add( labelDetails, "cell 0 1,grow" );
        add( getBtnGuardar( ), "cell 2 1,alignx center,aligny center" );
        this.add( getScrollPaneDetails( ), "cell 0 2 3 1,grow" );
        this.add( getCheckBoxEnableProduct( ), "cell 0 3,grow" );
        this.add( getButtonDeleteProduct( ), "cell 2 3,grow" );
    }

    private PricesPanel getPricesPanel( )
    {
        if( pricesPanel == null )
        {
            pricesPanel = new PricesPanel( window );
        }
        return pricesPanel;
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
            // jScrollPane.setViewportView( getJTable( ) );
            jScrollPane.setViewportView( getPricesPanel( ) );
        }
        return jScrollPane;
    }
    /**
     * This method initializes jTable
     * 
     * @return javax.swing.JTable
     */
    @Deprecated
    private JTable getJTable( )
    {
        if( jTable == null )
        {
            pricesTableRender = new PricesTableModel( );
            jTable = new JTable( pricesTableRender );
            TableColumn deleteButtons = jTable.getColumn( "Eliminar" );
            deleteButtons.setCellEditor( new PriceTableCellEditor( jTable ) );
            deleteButtons.setCellRenderer( new PriceTableCellRender( true ) );

        }
        return jTable;
    }
    public void setSelectedItem( MenuItem selected )
    {
        this.selected = selected;
        // pricesTableRender.setSelected( selected );
        pricesPanel.setSelectedItem( selected );
        textFieldDetails.setText( selected.getDescription( ) );
        // jTable.repaint( );
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
    @Deprecated
    public class PricesTableModel extends AbstractTableModel
    {
        private static final long serialVersionUID = 1L;

        String[] columnNames = { "#Items", "Detalles", "Precio", "Enable", "Eliminar" };
        Object[][] data = {}; // { 1, "", 20000, true }, { 1, "", 20000, false } };
        Vector<PriceItem> prices;
        public PricesTableModel( )
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
            if( value.toString( ).equals( DELETE_PRICE_ITEM ) )
            {
                try
                {
                    if( row != data.length - 1 )
                    {
                        PriceItem p = prices.get( row );
                        p.delete( );
                        setSelectedItem( selected );
                    }
                }
                catch( SQLException e )
                {
                    JOptionPane.showMessageDialog( window, "Error tratando de eliminar el precio \n Si los errores continuarn contacte al administrador del sistema", "ERROR", JOptionPane.ERROR_MESSAGE );
                }
            }
            else
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
                    if( row == data.length - 1 )// es un preico nuevo
                    {
                        PriceItem p = new PriceItem( -1, cuantitiy, descripcion, enable, -1, price, selected );
                        selected.addPriceItem( p );
                        setSelectedItem( selected );
                    }
                    else
                    {
                        PriceItem p = prices.get( row );
                        p.modify( cuantitiy, descripcion, enable, p.getOrder( ), price );// TODO toca solucionar lo del orden
                    }
                }
                catch( SQLException e )
                {
                    JOptionPane.showMessageDialog( window, "Error inesperado tratando de guardar los cambios \n" + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
                    e.printStackTrace( );
                }
            }

        }
        public void setSelected( MenuItem menuitem )
        {
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

    /**
     * 
     * @author daniel.rodriguez
     * 
     */
    public class PriceTableCellRender extends JButton implements TableCellRenderer
    {
        private static final long serialVersionUID = 1L;
        boolean isBordered = true;

        public PriceTableCellRender( boolean isBordered )
        {
            this.isBordered = isBordered;
            setOpaque( true );
        }

        public Component getTableCellRendererComponent( JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column )
        {
            return new JButton( "Render" );
        }
    }
    /**
     * 
     * @author daniel.rodriguez
     * 
     */
    public class PriceTableCellEditor extends AbstractCellEditor implements TableCellEditor
    {
        private static final long serialVersionUID = 1L;
        Boolean currentValue;
        JButton button;
        protected static final String EDIT = "Editor";
        private JTable jTable1;

        public PriceTableCellEditor( JTable jTable1 )
        {
            button = new JButton( );
            button.setActionCommand( EDIT );
            button.addActionListener( new ActionListener( )
            {

                @Override
                public void actionPerformed( ActionEvent e )
                {
                    fireEditingStopped( );// Aqui hace que el envento vall al PriceTableModel donde ya sabe que hacer

                }
            } );
            button.setBorderPainted( false );
            this.jTable1 = jTable1;
        }

        // Implement the one CellEditor method that AbstractCellEditor doesn't.
        public Object getCellEditorValue( )
        {
            return DELETE_PRICE_ITEM;
        }

        // Implement the one method defined by TableCellEditor.
        public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int column )
        {
            // Va a mostrar el botón solo en la última fila.
            // de otra forma muestra un espacio en blanco.
            if( row == table.getModel( ).getRowCount( ) - 1 )
            {
                currentValue = ( Boolean )value;
                return button;
            }
            return button;
        }

    }

}
