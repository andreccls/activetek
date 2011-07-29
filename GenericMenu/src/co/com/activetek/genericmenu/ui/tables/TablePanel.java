package co.com.activetek.genericmenu.ui.tables;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.ui.OsakiMenu;
import co.com.activetek.genericmenu.ui.utils.MyImageIcon;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.ComponentOrientation;
import java.sql.SQLException;

public class TablePanel extends JPanel implements ActionListener
{
    // ------------------------------------------------------------------------------------
    // CONSTANTES
    // ------------------------------------------------------------------------------------
    public final static String FREE_TABLE = "./images/GenericMenu/ui/free_table.jpg";
    public final static String BUSY_TABLE = "./images/GenericMenu/ui/busy_table.jpg";
    public final static String WAITING_TABLE = "./images/GenericMenu/ui/waiting_table.jpg";

    private final static String DELETE = "DELETE";
    private final static String ADD = "ADD";
    private final static String MOVE_UP = "MOVE_UP";
    private final static String MOVE_DOWN = "MOVE_DOWN";
    private final static String MOVE_LEFT = "MOVE_LEFT";
    private final static String MOVE_RIGHT = "MOVE_RIGHT";
    private final static String SET_FREE = "SET_FREE";
    private final static String EDIT = "EDIT";

    // ------------------------------------------------------------------------------------
    // ATRIBUTOS
    // ------------------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private JLabel labelNumber = null;
    private JPanel panelDatails = null;
    private JLabel labelCapacity = null;
    private JLabel labelPuestos = null;// solo tiene esta info: setText("puestos")

    private JPopupMenu menuTtable = new JPopupMenu( );
    private JMenuItem itemDelete = new JMenuItem( "Eliminar" );
    private JMenuItem itemAdd = new JMenuItem( "Nueva Mesa" );
    private JMenuItem itemMoveUp = new JMenuItem( "Mover arriba" );
    private JMenuItem itemMoveDown = new JMenuItem( "Mover abajo" );
    private JMenuItem itemMoveLeft = new JMenuItem( "Mover izquierda" );
    private JMenuItem itemMoveRight = new JMenuItem( "Mover derecha" );
    private JMenuItem itemFree = new JMenuItem( "Liberar Mesa" );
    private JMenuItem itemEdit = new JMenuItem( "Editar" );

    private OsakiMenu window;
    private int mouseX;
    private int mouseY;
    /**
     * la table del mundo que va a ser pintada en este panel
     */
    private Table table;
    /**
     * No basta com saber solo la mesa, sino tambien hay que saber las coordenadas para cuando se quiere agregar una mesa en un campo en que table == null
     */
    private int x;
    private int y;

    public TablePanel( Table table, int x, int y, OsakiMenu window )
    {
        this.table = table;
        this.x = x;
        this.y = y;
        this.window = window;
        initialize( );
        this.setBorder( BorderFactory.createTitledBorder( null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font( "Dialog", Font.BOLD, 12 ), new Color( 51, 51, 51 ) ) );
    }

    private void initialize( )
    {
        this.setBorder( BorderFactory.createLineBorder( SystemColor.controlHighlight, 1 ) );
        BorderLayout border = new BorderLayout( );
        this.setLayout( border );
        // this.add( getLabelIcon( ), BorderLayout.CENTER );
        this.add( getLabelNumber( ), BorderLayout.NORTH );
        this.add( getPanelDetails( ), BorderLayout.SOUTH );
        this.addMouseListener( new MouseListener( )
        {

            @Override
            public void mouseReleased( MouseEvent e )
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed( MouseEvent e )
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited( MouseEvent e )
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered( MouseEvent e )
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked( MouseEvent e )
            {
                if( e.getButton( ) == MouseEvent.BUTTON3 )
                {
                    getTableMenu( ).show( e.getComponent( ), e.getX( ), e.getY( ) );
                    mouseX = e.getX( );
                    mouseY = e.getY( );
                }

            }
        } );
        itemDelete.setActionCommand( DELETE );
        itemDelete.addActionListener( this );
        itemAdd.setActionCommand( ADD );
        itemAdd.addActionListener( this );
        itemMoveUp.setActionCommand( MOVE_UP );
        itemMoveUp.addActionListener( this );
        itemMoveDown.setActionCommand( MOVE_DOWN );
        itemMoveDown.addActionListener( this );
        itemMoveLeft.setActionCommand( MOVE_LEFT );
        itemMoveLeft.addActionListener( this );
        itemMoveRight.setActionCommand( MOVE_RIGHT );
        itemMoveRight.addActionListener( this );
        itemFree.setActionCommand( SET_FREE );
        itemFree.addActionListener( this );
        itemEdit.setActionCommand( EDIT );
        itemEdit.addActionListener( this );

        menuTtable.add( itemFree );
        menuTtable.add( new JPopupMenu.Separator( ) );
        menuTtable.add( itemDelete );
        menuTtable.add( itemAdd );
        menuTtable.add( new JPopupMenu.Separator( ) );
        menuTtable.add( itemMoveUp );
        menuTtable.add( itemMoveDown );
        menuTtable.add( itemMoveLeft );
        menuTtable.add( itemMoveRight );
        menuTtable.add( new JPopupMenu.Separator( ) );
        menuTtable.add( itemEdit );
    }

    private JLabel getLabelNumber( )
    {
        if( labelNumber == null )
        {
            labelNumber = new JLabel( );
            labelNumber.setHorizontalAlignment( SwingConstants.CENTER );
            if( table != null )
            {
                labelNumber.setText( table.getNumber( ) + "" );
            }

        }
        return labelNumber;
    }
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        if( table != null )
        {
            String image = table.getState( ).equalsIgnoreCase( "FREE" ) ? FREE_TABLE : table.getState( ).equalsIgnoreCase( "BUSY" ) ? BUSY_TABLE : WAITING_TABLE;
            ImageIcon ic = MyImageIcon.getInstance( ).setSize( new ImageIcon( image ).getImage( ), this.getWidth( ), this.getHeight( ), this );

            g.drawImage( ic.getImage( ), 0, 0, null );
        }
    }
    private JPanel getPanelDetails( )
    {
        if( panelDatails == null )
        {
            panelDatails = new JPanel( );
            labelCapacity = new JLabel( );
            labelPuestos = new JLabel( );

            labelCapacity.setFont( new Font( "Arial", Font.BOLD | Font.ITALIC, 12 ) );
            labelCapacity.setHorizontalAlignment( SwingConstants.RIGHT );
            labelCapacity.setHorizontalTextPosition( SwingConstants.RIGHT );
            labelCapacity.setComponentOrientation( ComponentOrientation.LEFT_TO_RIGHT );

            labelPuestos.setFont( new Font( "Arial", Font.BOLD | Font.ITALIC, 9 ) );
            labelPuestos.setComponentOrientation( ComponentOrientation.RIGHT_TO_LEFT );

            if( table != null )
            {
                // panelDatails.setLayout( new GridLayout( 1, 2) );
                labelCapacity.setText( table.getCapacity( ) + "" );
                labelPuestos.setText( " puestos" );

            }
            panelDatails.add( labelCapacity );
            panelDatails.add( labelPuestos );

        }
        return panelDatails;
    }

    /**
     * Reotorna las copciones opciones disponibles para la mesa
     * 
     * mover arriba, mover abajo, mover izquierda, mover derecha siempre aparecen siempre y cuando un borde no lo impida Eliminar aparece siempre y cuando la mesa exista.
     * Crear aparece si la table == null BUSY || WAITING Table: Liberar mesa. FREE Table:
     * @return
     */
    private JPopupMenu getTableMenu( )
    {

        if( y != 0 && table != null )
            itemMoveUp.setEnabled( true );
        else
            itemMoveUp.setEnabled( false );

        if( x != 0 && table != null )
            itemMoveLeft.setEnabled( true );
        else
            itemMoveLeft.setEnabled( false );

        if( table != null )
        {
            if( table.getState( ).equals( Table.BUSY ) || table.getState( ).equals( Table.WAITING ) )
            {
                itemFree.setEnabled( true );
            }
            else
            {
                itemFree.setEnabled( false );
            }
            itemDelete.setEnabled( true );
            itemMoveDown.setEnabled( true );
            itemEdit.setEnabled( true );
            itemAdd.setEnabled( false );
        }
        else
        {
            itemDelete.setEnabled( false );
            itemMoveDown.setEnabled( false );
            itemMoveRight.setEnabled( false );
            itemFree.setEnabled( false );
            itemEdit.setEnabled( false );
        }

        return menuTtable;
    }

    @Override
    public void actionPerformed( ActionEvent arg0 )
    {
        String command = arg0.getActionCommand( );
        if( command.equals( DELETE ) )
        {
            try
            {
                table.delete( );
                refresh( null );
            }
            catch( SQLException e )
            {
                JOptionPane.showMessageDialog( window, "Error inesperado eliminando la mesa de la base de datos \n" + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
                // TODO Auto-generated catch block
                e.printStackTrace( );
            }
        }
        else if( command.equals( ADD ) )
        {
            TableEditDialog d = new TableEditDialog( table, mouseX, mouseY, window, this, x, y );
            d.setVisible( true );
        }
        else if( command.equals( MOVE_UP ) )
        {

        }
        else if( command.equals( MOVE_DOWN ) )
        {

        }
        else if( command.equals( MOVE_LEFT ) )
        {

        }
        else if( command.equals( MOVE_RIGHT ) )
        {

        }
        else if( command.equals( SET_FREE ) )
        {

        }
        else if( command.equals( EDIT ) )
        {
            TableEditDialog d = new TableEditDialog( table, mouseX, mouseY, window, this, x, y );
            d.setVisible( true );
        }
    }
    /**
     * Este metodo pinta la mesa cuando es creada
     * @param table
     */
    public void refresh( Table table )
    {
        this.table = table;
        if( table != null )
        {
            labelCapacity.setText( table.getCapacity( ) + "" );
            labelPuestos.setText( " puestos" );
            labelNumber.setText( table.getNumber( ) + "" );
        }
        else
        {
            labelCapacity.setText( "" );
            labelPuestos.setText( "" );
            labelNumber.setText( "" );            
        }

    }
    public void update( int number, int capacity )
    {
        labelCapacity.setText( capacity + "" );
        labelNumber.setText( number + "" );
    }

}
