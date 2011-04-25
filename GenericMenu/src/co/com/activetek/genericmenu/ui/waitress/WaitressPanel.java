package co.com.activetek.genericmenu.ui.waitress;

import javax.swing.JPanel;

import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.ui.OsakiMenu;
import co.com.activetek.genericmenu.ui.utils.MyImageIcon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

public class WaitressPanel extends JPanel implements ActionListener
{
    private final static String NON_WAITRESS = "./images/GenericMenu/waitresses/none.jpg";
    private final static String DEL_WAITRESS = "Eliminar mesero";
    private final static String EDIT_WAITRESS = "Editar mesero";
    private final static int WIDHT = 80;
    private final static int HEIGHT = 80;

    private Waitress waitress;
    private JLabel labelPhoto = null;
    private OsakiMenu window;
    private JPopupMenu popupmenuWaitresOptions = new JPopupMenu( );
    private JMenuItem menuItemDelWaitress = new JMenuItem( DEL_WAITRESS );
    private JMenuItem menuItemEditWaitress = new JMenuItem( EDIT_WAITRESS );

    public WaitressPanel( OsakiMenu window,Waitress waitress )
    {
        super( );
        this.window = window;
        this.waitress = waitress;
        menuItemDelWaitress.addActionListener( this );
        menuItemEditWaitress.addActionListener( this );
        popupmenuWaitresOptions.add( menuItemDelWaitress );
        popupmenuWaitresOptions.add( menuItemEditWaitress );
        this.addMouseListener( new MouseListener( )
        {
            
            @Override
            public void mouseReleased( MouseEvent arg0 )
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mousePressed( MouseEvent arg0 )
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mouseExited( MouseEvent arg0 )
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mouseEntered( MouseEvent arg0 )
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void mouseClicked( MouseEvent arg0 )
            {
                popupmenuWaitresOptions.show( arg0.getComponent( ), arg0.getX( ), arg0.getY( ) );                
            }
        });
        initialize( );
    }
    /**
     * This method initializes this
     * 
     */
    private void initialize( )
    {
        labelPhoto = new JLabel( );
        ImageIcon ic = new ImageIcon( waitress.getPhoto( ) == null ? NON_WAITRESS : waitress.getPhoto( ) );
        ic = MyImageIcon.getInstance( ).setSize( ic.getImage( ), WIDHT, HEIGHT, this );
        labelPhoto.setIcon( ic );
        labelPhoto.setHorizontalTextPosition( SwingConstants.CENTER );
        labelPhoto.setHorizontalAlignment( SwingConstants.CENTER );
        this.setLayout( new BorderLayout( ) );
        this.setSize( new Dimension( 189, 163 ) );
        this.setBorder( BorderFactory.createTitledBorder( null, waitress.getNick( ), TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font( "DialogInput", Font.ITALIC, 12 ), new Color( 146, 51, 51 ) ) );
        this.add( labelPhoto, BorderLayout.CENTER );

    }
    @Override
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );
        if(command.equals( DEL_WAITRESS ))
        {
            System.out.println("eliminar mesero");
        }
        else if(command.equals( EDIT_WAITRESS ))
        {
            System.out.println("editar el mesero");
        }
    }
} // @jve:decl-index=0:visual-constraint="10,10"
