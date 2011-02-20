package co.com.activetek.genericmenu.ui.menu;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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

    // --------------------------------------------------------------------------------------------
    // Constructores
    // --------------------------------------------------------------------------------------------
    /**
     * This is the default constructor
     */
    public ProdcutInfoDetailPanel( )
    {
        super( );
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
        gridBagConstraints0.fill = GridBagConstraints.BOTH;
        gridBagConstraints0.gridx = 0;
        gridBagConstraints0.gridy = 0;
        gridBagConstraints0.weightx = 1;
        gridBagConstraints0.weighty = 0.4;
        gridBagConstraints0.gridwidth =2;

        GridBagConstraints gridBagConstraints1 = new GridBagConstraints( );// Para el label detalles
        gridBagConstraints1.fill = GridBagConstraints.BOTH;
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.weightx = 1;
        gridBagConstraints1.gridheight = 1;

        GridBagConstraints gridBagConstraints2 = new GridBagConstraints( );// Para el textField de detalles
        gridBagConstraints2.fill = GridBagConstraints.BOTH;
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.gridy = 2;
        gridBagConstraints2.weightx = 1;
        gridBagConstraints2.weighty = 0.4;
        gridBagConstraints2.gridwidth =2;
        

        GridBagConstraints gridBagConstraints3 = new GridBagConstraints( );// Para el chek de enable
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
            checkBoxEnableProduct = new JCheckBox( "enable" );
        }
        return checkBoxEnableProduct;
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
            textFieldDetails.setEnabled( false );// TODO cambiar con la cuenta de administardor
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

    // --------------------------------------------------------------------------------------------
    // Clases auxiliares
    // --------------------------------------------------------------------------------------------
    /**
     * Objeto grafico que representa una opcion de prcio de un producto
     * @author daniel.rodriguez
     * 
     */
    public class PriceItem extends JPanel
    {
        private int cuantity;
        private String PreiceItemDescription;
        private long price;
    }
    public class PriceItemWrapper extends JPanel
    {
        private int cuantity;
        private String PreiceItemDescription;
        private long price;
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
            String[] columnNames = { "First Name", "Last Name", "Sport", "# of Years", "Vegetarian" };
            Object[][] data = { { "Kathy", "Smith", "Snowboarding", new Integer( 5 ), new Boolean( false ) }, { "John", "Doe", "Rowing", new Integer( 3 ), new Boolean( true ) }, { "Sue", "Black", "Knitting", new Integer( 2 ), new Boolean( false ) },
                    { "Jane", "White", "Speed reading", new Integer( 20 ), new Boolean( true ) }, { "Joe", "Brown", "Pool", new Integer( 10 ), new Boolean( false ) } };

            jTable = new JTable( data, columnNames );
        }
        return jTable;
    }

}
