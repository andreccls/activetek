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
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.ComponentOrientation;

public class TablePanel extends JPanel implements ActionListener
{
	//------------------------------------------------------------------------------------
	//				CONSTANTES
	//------------------------------------------------------------------------------------
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

	//------------------------------------------------------------------------------------
	//				ATRIBUTOS
	//------------------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    private JLabel labelNumber = null;
    private JLabel labelIcon = null;
    private JPanel panelDatails = null;
    private JLabel labelCapacity = null;
    private JLabel labelPuestos = null;// solo tiene esta info: setText("puestos")
    
    private JPopupMenu menuTtable = new JPopupMenu( );
    private JMenuItem itemDelete = new JMenuItem( "Eliminar" );
    private JMenuItem itemAdd = new JMenuItem( "Nueva Mesa" );
    private JMenuItem itemMoveUp = new JMenuItem("Mover arriba");
    private JMenuItem itemMoveDown = new JMenuItem("Mover abajo");
    private JMenuItem itemMoveLeft = new JMenuItem("Mover izquierda");
    private JMenuItem itemMoveRight = new JMenuItem("Mover derecha");
    private JMenuItem itemFree = new JMenuItem("Liberar Mesa");

    /**
     * la table del mundo que va a ser pintada en este panel
     */
    private Table table;
    /**
     * No basta com saber solo la mesa, sino tambien hay que saber las coordenadas para cuando se quiere agregar una mesa en un campo en que table ==  null
     */
    private int x;
    private int y;

    public TablePanel( Table table, int x, int y )
    {
        this.table = table;
        this.x = x;
        this.y = y;
        initialize( );
        this.setBorder( BorderFactory.createTitledBorder( null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font( "Dialog", Font.BOLD, 12 ), new Color( 51, 51, 51 ) ) );
    }

    private void initialize( )
    {
        this.setBorder( BorderFactory.createLineBorder( SystemColor.controlHighlight, 1 ) );
        BorderLayout border = new BorderLayout( );
        this.setLayout( border );
        this.add( getLabelIcon( ), BorderLayout.CENTER );
        this.add( getLabelNumber( ), BorderLayout.NORTH );
        this.add( getPanelDetails( ), BorderLayout.SOUTH );
        this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {	
				if(e.getButton() == MouseEvent.BUTTON3)
				{
					System.out.println(x +","+y);
					getTableMenu().show(e.getComponent(),e.getX(),e.getY());
				}
				
			}
		});
        itemDelete.setActionCommand(DELETE);
        itemAdd.setActionCommand(ADD);
        itemMoveUp.setActionCommand(MOVE_UP);
        itemMoveDown.setActionCommand(MOVE_DOWN);
        itemMoveLeft.setActionCommand(MOVE_LEFT);
        itemMoveRight.setActionCommand(MOVE_RIGHT);
        itemFree.setActionCommand(SET_FREE);
        
        menuTtable.add(itemFree);
        menuTtable.add(new JPopupMenu.Separator());
        menuTtable.add(itemDelete);
        menuTtable.add(itemAdd);
        menuTtable.add(new JPopupMenu.Separator());
        menuTtable.add(itemMoveUp);
        menuTtable.add(itemMoveDown);
        menuTtable.add(itemMoveLeft);
        menuTtable.add(itemMoveRight);
        	
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
   
    /**
     * Reotorna las copciones opciones disponibles para la mesa
     * 
     * mover arriba, mover abajo, mover izquierda, mover derecha siempre aparecen siempre y cuando un borde no lo impida
     * Eliminar aparece siempre y cuando la mesa exista.
     * Crear aparece si la table ==  null
     * BUSY || WAITING Table: Liberar mesa.
     * FREE Table: 
     * @return
     */
    private JPopupMenu getTableMenu()
    {
    	
    	
    	
		if(y!=0 && table != null)
    		itemMoveUp.setEnabled(true);
    	else
    		itemMoveUp.setEnabled(false);
    	
    	if(x!=0 && table != null)
    		itemMoveLeft.setEnabled(true);
    	else
    		itemMoveLeft.setEnabled(false);
    	
    	if(table != null)
    	{
    		if(table.getState().equals(Table.BUSY) || table.getState().equals(Table.WAITING))
    		{
    			itemFree.setEnabled(true);
    		}
    		else
    		{
    			itemFree.setEnabled(false);
    		}
    		itemDelete.setEnabled(true);
    		itemMoveDown.setEnabled(true);
    	}
    	else
    	{
    		itemDelete.setEnabled(false);
    		itemMoveDown.setEnabled(false);
    		itemMoveRight.setEnabled(false);
    		itemFree.setEnabled(false);
    	}
    	
    	
    	
    	return menuTtable;
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		System.out.println(command);
		
	}

}
