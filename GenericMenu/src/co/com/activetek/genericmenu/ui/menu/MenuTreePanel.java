package co.com.activetek.genericmenu.ui.menu;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.GridBagConstraints;
import java.awt.ComponentOrientation;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class MenuTreePanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private JTree treeMenu = null;
    private JScrollPane jScrollPane = null;

    /**
     * This is the default constructor
     */
    public MenuTreePanel( )
    {
        super( );
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
            DefaultTreeModel modelo = new DefaultTreeModel( abuelo );
            
            DefaultMutableTreeNode rollos = new DefaultMutableTreeNode("rollos");
            DefaultMutableTreeNode sopas = new DefaultMutableTreeNode("sopas");
            DefaultMutableTreeNode platos = new DefaultMutableTreeNode("platos pequeños");
            
            DefaultMutableTreeNode rollo0 = new DefaultMutableTreeNode("rollos de cangrejo");
            DefaultMutableTreeNode rollo1 = new DefaultMutableTreeNode("Rollos de Primavera estilo Thai");
            DefaultMutableTreeNode rollo2 = new DefaultMutableTreeNode("Rollitos Primavera");
            DefaultMutableTreeNode rollo3 = new DefaultMutableTreeNode("Rollos Siam");
            
            DefaultMutableTreeNode sopa0 = new DefaultMutableTreeNode("<Miso>");
            DefaultMutableTreeNode sopa1 = new DefaultMutableTreeNode("Sopa Pho ");
            DefaultMutableTreeNode sopa2 = new DefaultMutableTreeNode("Sopa Agripicante de Fideos y Pollo");
            DefaultMutableTreeNode sopa3 = new DefaultMutableTreeNode("<Tom Ka>");
            
            DefaultMutableTreeNode plato0 = new DefaultMutableTreeNode("Calamares crujientes");
            DefaultMutableTreeNode plato1 = new DefaultMutableTreeNode("Pinchos a la Parrilla");
            DefaultMutableTreeNode plato2 = new DefaultMutableTreeNode("Edamame");
            DefaultMutableTreeNode plato3 = new DefaultMutableTreeNode("Raviolis de Camarón");
            
            modelo.insertNodeInto(rollos,abuelo,0);
            modelo.insertNodeInto(sopas,abuelo,0);
            modelo.insertNodeInto(platos,abuelo,0);
            
            modelo.insertNodeInto( rollo0, rollos , 0 );
            modelo.insertNodeInto( rollo1, rollos , 1 );
            modelo.insertNodeInto( rollo2, rollos , 2 );
            modelo.insertNodeInto( rollo3, rollos , 3 );
                        
            modelo.insertNodeInto( sopa0, sopas , 0 );
            modelo.insertNodeInto( sopa1, sopas , 1 );
            modelo.insertNodeInto( sopa2, sopas , 2 );
            modelo.insertNodeInto( sopa3, sopas , 3 );
            
            modelo.insertNodeInto( plato0, platos , 0 );
            modelo.insertNodeInto( plato1, platos , 1 );
            modelo.insertNodeInto( plato2, platos , 2 );
            modelo.insertNodeInto( plato3, platos , 3 );

            
            treeMenu = new JTree( modelo );
            treeMenu.setEditable( false );
            treeMenu.setComponentOrientation( ComponentOrientation.LEFT_TO_RIGHT );
            treeMenu.setRootVisible( true );
            treeMenu.expandRow( 1 );
            treeMenu.expandRow( 6 );
            treeMenu.expandRow( 11 );
            treeMenu.addMouseListener( new MouseListener( )
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
                    System.out.println(treeMenu.getPathForLocation( e.getX( ), e.getY( ) ));
                    
                }
            });
        }
        return treeMenu;
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
