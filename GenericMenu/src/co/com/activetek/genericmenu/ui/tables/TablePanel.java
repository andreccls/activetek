package co.com.activetek.genericmenu.ui.tables;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import co.com.activetek.genericmenu.server.beans.Table;
import co.com.activetek.genericmenu.ui.utils.MyImageIcon;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.ComponentOrientation;

public class TablePanel extends JPanel
{
    public final static String FREE_TABLE = "./images/GenericMenu/ui/free_table.jpg";
    public final static String BUSY_TABLE = "./images/GenericMenu/ui/busy_table.jpg";
    public final static String WAITING_TABLE = "./images/GenericMenu/ui/waiting_table.jpg";

    private static final long serialVersionUID = 1L;
    private JLabel labelNumber = null;
    private JLabel labelIcon = null;
    private JPanel panelDatails = null;
    private JLabel labelCapacity = null;
    private JLabel labelPuestos = null;// solo tiene esta info: setText("puestos")
    JPopupMenu menuTtable = new JPopupMenu( );
    JMenuItem itemDelete = new JMenuItem( "delete" );

    /**
     * la table del mundo que va a ser pintada en este panel
     */
    private Table table;

    public TablePanel( Table table )
    {
        this.table = table;
        initialize( );
    }

    private void initialize( )
    {
        this.setBorder( BorderFactory.createLineBorder( SystemColor.controlHighlight, 1 ) );
        BorderLayout border = new BorderLayout( );
        this.setLayout( border );
        this.add( getLabelIcon( ), BorderLayout.CENTER );
        this.add( getLabelNumber( ), BorderLayout.NORTH );
        this.add( getPanelDetails( ), BorderLayout.SOUTH );
    }
    private JLabel getLabelIcon( )
    {
        if( labelIcon == null )
        {
            labelIcon = new JLabel( );
            labelIcon.setText( "" );
            labelIcon.setHorizontalTextPosition( SwingConstants.CENTER );
            labelIcon.setHorizontalAlignment( SwingConstants.CENTER );
            if( table != null )
            {
                String image = table.getState( ).equalsIgnoreCase( "FREE" ) ? FREE_TABLE : table.getState( ).equalsIgnoreCase( "BUSY" ) ? BUSY_TABLE : WAITING_TABLE;
                ImageIcon ic = new ImageIcon( image );
                labelIcon.setIcon( MyImageIcon.getInstance( ).setSize( ic.getImage( ), 70, 70, this ) );
            }
        }

        return labelIcon;
    }
    private JLabel getLabelNumber( )
    {
        if( labelNumber == null )
        {
            labelNumber = new JLabel( );
            if( table != null )
            {
                labelNumber.setText( table.getNumber( ) + "" );
                labelNumber.setHorizontalAlignment( SwingConstants.CENTER );
            }

        }
        return labelNumber;
    }
    private JPanel getPanelDetails( )
    {
        if( panelDatails == null )
        {
            panelDatails = new JPanel( );
            if( table != null )
            {
                // panelDatails.setLayout( new GridLayout( 1, 2) );
                labelCapacity = new JLabel( table.getCapacity( ) + "" );
                labelCapacity.setFont( new Font( "Arial", Font.BOLD | Font.ITALIC, 12 ) );
                labelCapacity.setHorizontalAlignment( SwingConstants.RIGHT );
                labelCapacity.setHorizontalTextPosition( SwingConstants.RIGHT );
                labelCapacity.setComponentOrientation( ComponentOrientation.LEFT_TO_RIGHT );
                labelPuestos = new JLabel( " puestos" );
                labelPuestos.setFont( new Font( "Arial", Font.BOLD | Font.ITALIC, 9 ) );
                labelPuestos.setComponentOrientation( ComponentOrientation.RIGHT_TO_LEFT );
                panelDatails.add( labelCapacity );
                panelDatails.add( labelPuestos );
            }

        }
        return panelDatails;
    }
    // /**
    // * This method initializes this
    // *
    // * @return void
    // */
    // private void initializeOld( )
    // {
    // labelIcon = new JLabel( );
    // labelIcon.setText( "" );
    // labelIcon.setHorizontalTextPosition( SwingConstants.CENTER );
    // labelIcon.setHorizontalAlignment( SwingConstants.CENTER );
    // ImageIcon ic = new ImageIcon( "./images/GenericMenu/ui/free_table.jpg" );
    // labelIcon.setIcon( MyImageIcon.getInstance( ).setSize( ic.getImage( ), 70, 70, this ) );
    // labelNumber = new JLabel( );
    // labelNumber.setText( "" + ( x + 1 + y * MapTablesPanel.WIDTH ) );
    // labelNumber.setHorizontalAlignment( SwingConstants.CENTER );
    //
    // itemDelete.addActionListener( new ActionListener( )
    // {
    //
    // public void actionPerformed( ActionEvent e )
    // {
    // System.out.println( "delte!!!" );
    //
    // }
    // } );
    // menuTtable.add( itemDelete );
    // this.addMouseListener( new MouseListener( )
    // {
    //
    // public void mouseReleased( MouseEvent e )
    // {
    //
    // }
    //
    // public void mousePressed( MouseEvent e )
    // {
    //
    // }
    //
    // public void mouseExited( MouseEvent e )
    // {
    //
    // }
    //
    // public void mouseEntered( MouseEvent e )
    // {
    //
    // }
    //
    // public void mouseClicked( MouseEvent e )
    // {
    // if( e.getButton( ) == MouseEvent.BUTTON3 )
    // {
    // menuTtable.show( e.getComponent( ), e.getX( ), e.getY( ) );
    // }
    //
    // }
    // } );
    //
    // this.setLayout( new BorderLayout( ) );
    // this.add( labelNumber, BorderLayout.NORTH );
    // this.add( labelIcon, BorderLayout.CENTER );
    // }

}
