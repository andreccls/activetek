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
import javax.swing.JTree.DynamicUtilTreeNode;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
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

    public final static String NUEVO_ITEM = "Nuevo Item";

    private static final long serialVersionUID = 1L;
    private OsakiMenu window;
    private JTree treeMenu = null;
    private JScrollPane jScrollPane = null;
    /**
     * usado para cuando se quiere agregar un nuevo item al menu. Este string almacena el padre seleccionado para el nuevo item
     */
    private TreePath selectedNode;

    /**
     * 
     */
    private DefaultMutableTreeNode selectedNodeTreeNode;

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
                Enumeration<TreePath> a = treeMenu.getExpandedDescendants( selectedNode.getParentPath( ) );// se guardan los paths abiertos para poder repintar el argbol y
                                                                                                           // posteriormente volverlos a abrir
                DefaultMutableTreeNode d = ( DefaultMutableTreeNode )selectedNode.getPath( )[ selectedNode.getPath( ).length - 1 ];
                MenuItem m = ( MenuItem )d.getUserObject( );
                System.out.println( m.getClass( ) );
                m.add( new MenuItem( 100, "nombre", "description", true, "icon", null, m, null ) );

                DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode( NUEVO_ITEM );
                selectedNodeTreeNode = nuevo;

                d.add( nuevo );
                ( ( DefaultTreeModel )treeMenu.getModel( ) ).reload( );
                treeMenu.repaint( );

                while( a.hasMoreElements( ) )// vuelve a abrir los paths que estaban abiertos abntes de que se comenzara la edicion
                {
                    TreePath p = a.nextElement( );
                    treeMenu.expandPath( p );
                }
                System.out.println( treeMenu.getLeadSelectionRow( ) );
                TreePath tp = treeMenu.getNextMatch( NUEVO_ITEM, treeMenu.getRowCount( ) - 1, Position.Bias.Backward ); // TODO no deberia buscar en todo el arbol sino desde
                                                                                                                        // la parte de abajo de la rama en la que se agrefo el
                                                                                                                        // nuevo item
                treeMenu.startEditingAtPath( tp );
            }
        } );
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

            // treeMenu = new JTree( window.getMenuTree( ) );
            treeMenu = new JTree( window.getMenuTree( ) );
            treeMenu.setModel( new menuItemModel( ( TreeNode )treeMenu.getModel( ).getRoot( ) ) );
            // treeMenu.getModel( ).addTreeModelListener( new menuItemListener( treeMenu ) );
            // treeMenu.setCellRenderer( new menuItemRender( ) );
            // treeMenu.setCellEditor( new menuItemEditor( new JTextField( "CONSTRUCTOR!!!") ) );
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
     * Clase usada para capturar los cambios hehcos al menu
     */
    class menuItemModel extends DefaultTreeModel
    {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        public menuItemModel( TreeNode root )
        {
            super( root );
        }
        /**
         * Eschucha el MenuItem para el cual se a cambiado el nombre, y lo persiste en el bean
         */
        public void valueForPathChanged( TreePath path, Object newValue )
        {
            System.out.println( ((MenuItem)((DynamicUtilTreeNode)( path.getPath( )[ path.getPath( ).length - 1 ] )).getUserObject( )));
            ((MenuItem)((DynamicUtilTreeNode)( path.getPath( )[ path.getPath( ).length - 1 ] )).getUserObject( )).setNombre( newValue.toString( ) );
            System.out.println( path );
            System.out.println( newValue );
        }

    }

    /**
     * Esta clase a final tampoco me sirvio
     */
    @Deprecated
    class menuItemEditor extends DefaultCellEditor
    {

        public menuItemEditor( JTextField textField )
        {
            super( textField );
        }
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

    /**
     * Deprecado poarque al parecer esta no fue la solucion
     * @author daniel.rodriguez
     * 
     */
    @Deprecated
    class menuItemListener implements TreeModelListener
    {

        public menuItemListener( JTree tree )
        {

        }
        @Override
        public void treeNodesChanged( TreeModelEvent e )
        {
            // TODO Auto-generated method stub
            // System.out.println("treeNodesChanged");
            // System.out.println(e.getPath( )[0].getClass( ));
            // System.out.println(((DefaultTreeModel)e.getSource( )));

            System.out.println( e.getTreePath( ) );
            Object[] o = e.getPath( );
            for( Object object : o )
            {
                System.out.println( object );
            }
            System.out.println( ( ( ( ( DefaultMutableTreeNode )e.getPath( )[ e.getPath( ).length - 1 ] ).getUserObject( ) ) ).getClass( ) );
            System.out.println( ( ( ( ( DefaultMutableTreeNode )e.getPath( )[ e.getPath( ).length - 1 ] ).getUserObject( ) ) ) );
            System.out.println( "" );
            // System.out.println( ((MenuItem)(((DefaultMutableTreeNode)e.getPath( )[e.getPath( ).length -1]).getUserObject( ))).isEnable( ) );
        }

        @Override
        public void treeNodesInserted( TreeModelEvent e )
        {
            // TODO Auto-generated method stub
            System.out.println( "treeNodesInserted" );
        }

        @Override
        public void treeNodesRemoved( TreeModelEvent e )
        {
            // TODO Auto-generated method stub
            System.out.println( "treeNodesRemoved" );
        }

        @Override
        public void treeStructureChanged( TreeModelEvent e )
        {
            // TODO Auto-generated method stub
            System.out.println( "TreeModelEvent" );
        }

    }
}
