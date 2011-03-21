package co.com.activetek.genericmenu.ui.menu;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.JScrollPane;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.ui.OsakiMenu;

import java.awt.BorderLayout;
import java.util.Enumeration;
import java.util.EventObject;
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
    /**
     * usado para cuando se quiere agregar un nuevo item al menu. Este string almacena el padre seleccionado para el nuevo item
     */
    private TreePath selectedNode;

    JPopupMenu menuTtable = new JPopupMenu( );
    /**
     * meny item usado para agregar un nuebo item a la categoria seleccionada
     */
    JMenuItem itemAdd = new JMenuItem( "Agregar Item" );
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
                Enumeration<TreePath> a = treeMenu.getExpandedDescendants( selectedNode.getParentPath( ));//se guardan los paths abiertos para poder repintar el argbol y posteriormente volverlos a abrir

                System.out.println(selectedNode.getPath( )[ selectedNode.getPath( ).length - 1]);
//                System.out.println( selectedNode.getPath( )[ 0 ].getClass( ) );
                DefaultMutableTreeNode d = ( DefaultMutableTreeNode )selectedNode.getPath( )[ selectedNode.getPath( ).length - 1 ];
                d.add( new DefaultMutableTreeNode( "NEW" ) );
                ((DefaultTreeModel)treeMenu.getModel( )).reload( );
                treeMenu.repaint( );
                
                
                //treeMenu.startEditingAtPath( d )   ;
//                repaint( );
//                System.out.println( treeMenu.getLastSelectedPathComponent( ) );
                //updateTree( );
                
                
                while(a.hasMoreElements( ))//vuelve a abrir los paths que estaban abiertos abntes de que se comenzara la edicion
                {
                    TreePath p = a.nextElement( );
                    System.out.println(p);
                    treeMenu.expandPath(  p);
                }
                
                
                TreePath tp = treeMenu.getNextMatch( "N", treeMenu.getRowCount( ) -1  , Position.Bias.Backward );
                treeMenu.startEditingAtPath( tp );
                System.out.println("tp: " + tp);
            }
        } );
        menuTtable.add( itemAdd );
    }
    
    private void updateTree()
    {
        treeMenu = new JTree( createNodes( ));
//        treeMenu.invalidate( );
//        treeMenu.validate( );
//        treeMenu.repaint( );
//        
//        treeMenu.setRowHeight(300);
//     // repaint this puppy
//        treeMenu.repaint();
        ((DefaultTreeModel)treeMenu.getModel( )).reload( );
        //window.repaint( );
//        treeMenu.setModel( new DefaultTreeModel( createNodes( )) );
//        treeMenu.revalidate( );
//        this.repaint( );
        System.out.println("repainted");
    }
    
    private DefaultMutableTreeNode createNodes( ) {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("top" );
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;
        
        category = new DefaultMutableTreeNode("Books for Java Programmers");
        top.add(category);
        
        //original Tutorial
        book = new DefaultMutableTreeNode("The Java Tutorial: A Short Course on the Basics tutorial.html");
        category.add(book);
        
        //Tutorial Continued
        book = new DefaultMutableTreeNode( ("The Java Tutorial Continued: The Rest of the JDK tutorialcont.html"));
        category.add(book);
        
        //JFC Swing Tutorial
        book = new DefaultMutableTreeNode( "The JFC Swing Tutorial: A Guide to Constructing GUIs swingtutorial.html");
        category.add(book);

        //...add more books for programmers...

        category = new DefaultMutableTreeNode("Books for Java Implementers");
        top.add(category);

        //VM
        book = new DefaultMutableTreeNode("The Java Virtual Machine Specification vm.html");
        category.add(book);

        //Language Spec
        book = new DefaultMutableTreeNode( "The Java Language Specification jls.html");
        category.add(book);
        return top;
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

            //treeMenu = new JTree( window.getMenuTree( ) );
            treeMenu = new JTree( window.getMenuTree( ) );
            //treeMenu.getModel( ).addTreeModelListener( new menuItemListener( treeMenu ) );
            // treeMenu.setCellRenderer( new menuItemRender( ) );
            // treeMenu.setCellEditor( new menuItemEditor( treeMenu ) );
            treeMenu.setEditable( true );// TODO cambiar para que solo lo tenga el administrador
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
                        if( e.getButton( ) == MouseEvent.BUTTON3 && window.getSelectedItem( ).getLevel( ) == MenuItem.LEVEl_CATEGORY )
                        {
                            selectedNode = path;
                            menuTtable.show( e.getComponent( ), e.getX( ), e.getY( ) );
                        }
                        else
                        {
                            window.setSelectedItem( path2 );
                        }
                    }
                }
            } );
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
            jScrollPane.setViewportView( getTreeMenu( ) );
        }
        return jScrollPane;
    }

    // -----------------------------------------------------------------------------------
    // Otras clases
    // -----------------------------------------------------------------------------------
    /**
     * 
     */
    class menuItemEditor extends DefaultCellEditor
    {

        public menuItemEditor( JTextField textField )
        {
            super( textField );
        }

        //
        // @Override
        // public Component getTreeCellEditorComponent( JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row )
        // {
        // System.out.println(hashCode( ) + " getTreeCellEditorComponent");
        // Component editor = render.getTreeCellRendererComponent( tree, value, isSelected, expanded, leaf, row, true );
        //
        // ItemListener itemListener = new ItemListener( )
        // {
        // public void itemStateChanged( ItemEvent itemEvent )
        // {
        // if( stopCellEditing( ) )
        // {
        // fireEditingStopped( );
        // }
        // }
        // };
        //
        // return editor;
        // }

    }

    /**
     * 
     * @author daniel.rodriguez
     * 
     */
    class menuItemRender implements TreeCellRenderer
    {

        @Override
        public Component getTreeCellRendererComponent( JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus )
        {
            System.out.println( hashCode( ) + " getTreeCellRendererComponent" );
            String stringValue = tree.convertValueToText( value, selected, expanded, leaf, row, false );
            JLabel l = new JLabel( stringValue );
            return l;
        }

    }
    class menuItemListener implements TreeModelListener
    {

        JTree tree_;
        public menuItemListener(JTree tree)
        {
            tree_= tree;
        }
        @Override
        public void treeNodesChanged( TreeModelEvent e )
        {
            // TODO Auto-generated method stub
            tree_.firePropertyChange(JTree.ROOT_VISIBLE_PROPERTY, !tree_.isRootVisible(), tree_.isRootVisible());
        }

        @Override
        public void treeNodesInserted( TreeModelEvent e )
        {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void treeNodesRemoved( TreeModelEvent e )
        {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void treeStructureChanged( TreeModelEvent e )
        {
            // TODO Auto-generated method stub
            
        }
        
    }
}
