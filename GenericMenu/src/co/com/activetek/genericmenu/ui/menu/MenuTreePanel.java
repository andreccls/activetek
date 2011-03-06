package co.com.activetek.genericmenu.ui.menu;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.ui.OsakiMenu;

import java.awt.BorderLayout;
import java.util.Vector;

/**
 * Clase que contiene el arbol del muenu
 * @author daniel.rodriguez
 *
 */
public class MenuTreePanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private OsakiMenu window;
    private JTree treeMenu = null;
    private JScrollPane jScrollPane = null;

    
    JPopupMenu menuTtable = new JPopupMenu();   
    /**
     * meny item usado para agregar un nuebo item a la categoria seleccionada
     */
    JMenuItem itemAdd = new JMenuItem("Agregar Item");
    /**
     * This is the default constructor
     */
    public MenuTreePanel( OsakiMenu window )
    {
        super( );
        this.window = window;
        initialize( );
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize( )
    {
        this.setSize( 300, 200 );
        this.setLayout( new BorderLayout( ) );
        this.setBorder( BorderFactory.createTitledBorder( null, "GnericMenu", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null ) );
        this.add( getJScrollPane( ), BorderLayout.CENTER );
        itemAdd.addActionListener( new ActionListener( )
        {            
            @Override
            public void actionPerformed( ActionEvent e )
            {
                // TODO Auto-generated method stub                
            }
        });
        menuTtable.add( itemAdd );
    }

    /**
     * This method initializes treeMenu
     * 
     * @return javax.swing.JTree
     */
    private JTree getTreeMenu( )
    {
        if( treeMenu == null )
        {

            DefaultMutableTreeNode abuelo = new DefaultMutableTreeNode( "GenericMenu" );
            buildMenuTree( window.getMenuTree( ), abuelo );

            treeMenu = new JTree( abuelo );
            treeMenu.setEditable( false );
            treeMenu.setComponentOrientation( ComponentOrientation.LEFT_TO_RIGHT );
            treeMenu.setRootVisible( true );
            treeMenu.addMouseListener( new MouseListener( )
            {

                public void mouseReleased( MouseEvent e )
                {

                }

                public void mousePressed( MouseEvent e )
                {

                }

                public void mouseExited( MouseEvent e )
                {

                }

                public void mouseEntered( MouseEvent e )
                {

                }

                public void mouseClicked( MouseEvent e )
                {
                    TreePath path = treeMenu.getPathForLocation( e.getX( ), e.getY( ) );
                    if( path != null )
                    {
                        String path2 = path.toString( ).replace( "[", "" ).replace( "]", "" ).replace( ", ", "," );
                        window.setSelectedItem( path2 );
                    }
                    if( e.getButton( ) == MouseEvent.BUTTON3  && window.getSelectedItem( ).getLevel( ) == MenuItem.LEVEl_CATEGORY )
                    {                        
                        menuTtable.show( e.getComponent( ), e.getX( ), e.getY( ) );
                    }
                }
            } );
        }
        return treeMenu;
    }

    private void buildMenuTree( MenuItem root, DefaultMutableTreeNode abuelo )
    {
        Vector<MenuItem> sons = root.getSons( );

        for( MenuItem menuItem : sons )
        {
            DefaultMutableTreeNode me = new DefaultMutableTreeNode( menuItem );
            abuelo.add( me );
            buildMenuTree( menuItem, me );
        }

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
            jScrollPane.setViewportView( getTreeMenu( ) );
        }
        return jScrollPane;
    }

}
