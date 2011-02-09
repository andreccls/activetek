package co.com.activetek.genericmenu.ui.menu;

import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.awt.GridBagConstraints;
import java.awt.ComponentOrientation;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.ui.GenericMenu;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.Vector;

public class MenuTreePanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private GenericMenu window;
    private JTree treeMenu = null;
    private JScrollPane jScrollPane = null;

    /**
     * This is the default constructor
     */
    public MenuTreePanel(GenericMenu window )
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
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder(null, "GnericMenu", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        this.add(getJScrollPane(), BorderLayout.CENTER);
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
			buildMenuTree( window.getMenuTree(),abuelo);
			
			treeMenu = new JTree( abuelo );
            treeMenu.setEditable( false );
            treeMenu.setComponentOrientation( ComponentOrientation.LEFT_TO_RIGHT );
            treeMenu.setRootVisible( true );
            treeMenu.addMouseListener( new MouseListener( )
            {
                
                public void mouseReleased( MouseEvent e )
                {
                    // TODO Auto-generated method stub
                    
                }

                public void mousePressed( MouseEvent e )
                {
                    // TODO Auto-generated method stub
                    
                }
                
                public void mouseExited( MouseEvent e )
                {
                    // TODO Auto-generated method stub
                    
                }
                

                public void mouseEntered( MouseEvent e )
                {
                    // TODO Auto-generated method stub
                    
                }
                

                public void mouseClicked( MouseEvent e )
                {
                	TreePath path = treeMenu.getPathForLocation( e.getX( ), e.getY( ) );
                	if(path != null)
                	{
                		String path2 = path.toString().replace("[", "").replace("]", "").replace(" ", "");
                		System.out.println(path2);
                	}
                    
                    
                }
            });
        }
        return treeMenu;
    }

    private void buildMenuTree(MenuItem root, DefaultMutableTreeNode abuelo) {
		Vector<MenuItem> sons= root.getSons();
		
		for (MenuItem menuItem : sons) {
			DefaultMutableTreeNode me = new DefaultMutableTreeNode(menuItem);
			abuelo.add(me);
			buildMenuTree(menuItem, me);
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
            jScrollPane.setViewportView(getTreeMenu());
        }
        return jScrollPane;
    }

}
