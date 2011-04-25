package co.com.activetek.genericmenu.ui.waitress;

import java.awt.GridBagLayout;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.ui.OsakiMenu;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import layout.TableLayout;

public class WaitressesPanel extends JPanel implements ActionListener
{

    private final static String ADD_WAITRESS = "Agregar Mesero";

    private static final long serialVersionUID = 1L;
    private final static int width = 5;
    private final static int height = 5;
    private JPopupMenu popupmenuWaitressOptions = new JPopupMenu();
    private JMenuItem menuItemAddWaitress = new JMenuItem( ADD_WAITRESS );
    private OsakiMenu window;
    /**
     * This is the default constructor
     */
    public WaitressesPanel( OsakiMenu widow )
    {
        super( );
        this.window = widow;
        popupmenuWaitressOptions.add( menuItemAddWaitress );
        menuItemAddWaitress.addActionListener( this );
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
                popupmenuWaitressOptions.show( arg0.getComponent( ), arg0.getX( ), arg0.getY( ) );
                
            }
        });
        refresh( );
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void refresh( )
    {

        Vector<Waitress> waitressVector = window.getWaitresses( );

        TableLayout layout = new TableLayout( );

        double[] widthArray = new double[width];
        double[] heightArray = new double[height];

        java.util.Arrays.fill( widthArray, 150 );
        java.util.Arrays.fill( heightArray, 150 );

        layout.setColumn( widthArray );
        layout.setRow( widthArray );

        this.setLayout( layout );

        for( int i = 0; i < waitressVector.size( ); i++ )
        {
            Waitress waitress = waitressVector.get( i );            
            this.add( new WaitressPanel( window , waitress ), " " + ( i % ( waitressVector.size( ) / 2 ) ) + ", " + ( i / ( waitressVector.size( ) / 2 ) ) );
        }

        this.doLayout( );
        this.repaint( );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );
        if( command.equals( ADD_WAITRESS ) )
        {
            WaitressEditDialog d = new WaitressEditDialog( );
            d.setVisible( true );
        }

    }

}
