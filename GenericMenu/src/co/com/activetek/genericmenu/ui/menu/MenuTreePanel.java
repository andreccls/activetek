package co.com.activetek.genericmenu.ui.menu;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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

import co.com.activetek.genericmenu.server.beans.Image;
import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.beans.PriceItem;
import co.com.activetek.genericmenu.ui.OsakiMenu;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Vector;

/**
 * Clase que contiene el arbol del muenu
 * @author daniel.rodriguez
 * 
 */
public class MenuTreePanel extends JPanel implements ActionListener
{
    // --------------------------------------------------------------------------------
    // CONSTANTES
    // --------------------------------------------------------------------------------
    /**
     * Constante usada para colocar el nobre a los items nuevos
     */
    private final static String NUEVO_ITEM = "Nuevo Item";

    /**
     * Estas constantes son usadas para colocar el nombre a los JMenuItem y para capturar el action command de esta clase
     */
    private final static String ADD_MENUITEM = "Agregar Item";
    private final static String DEL_MENUITEM = "Eliminar Item";
    private final static String DEL_CATEGORY = "Eliminar Categoria";
    private final static String ADD_CATEGORY = "Agregar Categoria";

    private static final long serialVersionUID = 1L;

    // --------------------------------------------------------------------------------
    // ATRIBUTOS
    // --------------------------------------------------------------------------------
    private OsakiMenu window;
    private JTree treeMenu = null;
    private JScrollPane jScrollPane = null;
    /**
     * usado para cuando se quiere agregar un nuevo item al menu. Este string almacena el padre seleccionado para el nuevo item
     */
    private TreePath selectedNode;

    /**
     */
    private DefaultMutableTreeNode selectedNodeTreeNode;

    private JPopupMenu popupmenuCategory = new JPopupMenu( );
    /**
     * menu item usado para agregar un nuevo item a la categoria seleccionada
     */
    private JMenuItem itemAddMenuItem = new JMenuItem( ADD_MENUITEM );
    private JMenuItem itemDelCategory = new JMenuItem( DEL_CATEGORY );
    private JPopupMenu popupmenuMenu = new JPopupMenu( );

    private JMenuItem itemAddCategory = new JMenuItem( ADD_CATEGORY );

    private JPopupMenu popupmenuItem = new JPopupMenu( );
    private JMenuItem itemDel = new JMenuItem( DEL_MENUITEM );

    // --------------------------------------------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------------------------------------------
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

        itemAddMenuItem.addActionListener( this );
        itemDelCategory.addActionListener( this );
        popupmenuCategory.add( itemAddMenuItem );
        popupmenuCategory.add( itemDelCategory );

        itemDel.addActionListener( this );
        popupmenuItem.add( itemDel );

        itemAddCategory.addActionListener( this );
        popupmenuMenu.add( itemAddCategory );
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

            MenuItem rootMenu = window.getMenuTree( );
            DefaultMutableTreeNode rootDMTN = new DefaultMutableTreeNode( rootMenu );

            loadNodes( rootDMTN, rootMenu );

            treeMenu = new JTree( rootDMTN );
            treeMenu.setModel( new menuItemModel( ( TreeNode )treeMenu.getModel( ).getRoot( ) ) );
            treeMenu.setRootVisible( false );
            // treeMenu.getModel( ).addTreeModelListener( new menuItemListener( treeMenu ) );
            // treeMenu.setCellRenderer( new menuItemRender( ) );
            // treeMenu.setCellEditor( new menuItemEditor( new JTextField( "CONSTRUCTOR!!!") ) );
            treeMenu.setEditable( true );// TODO cambiar para que solo lo tenga el administrador
            treeMenu.setComponentOrientation( ComponentOrientation.LEFT_TO_RIGHT );
            treeMenu.setRootVisible( true );
            treeMenu.addMouseListener( new MouseListener( )// TODO poner listener para el teclado
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
                        selectedNode = path;
                        String path2 = path.toString( ).replace( "[", "" ).replace( "]", "" ).replace( ", ", "," );
                        if( e.getButton( ) == MouseEvent.BUTTON3 && window.getSelectedItem( ).getLevel( ) == MenuItem.LEVEl_CATEGORY )
                        {
                            popupmenuCategory.show( e.getComponent( ), e.getX( ), e.getY( ) );
                        }
                        else if( e.getButton( ) == MouseEvent.BUTTON3 && window.getSelectedItem( ).getLevel( ) == MenuItem.LEVEl_ITEM )
                        {
                            popupmenuItem.show( e.getComponent( ), e.getX( ), e.getY( ) );
                        }
                        else if( e.getButton( ) == MouseEvent.BUTTON3 && window.getSelectedItem( ).getLevel( ) == MenuItem.LEVEL_MENU )
                        {
                            popupmenuMenu.show( e.getComponent( ), e.getX( ), e.getY( ) );
                        }
                        else
                        {
                            window.setSelectedItem( getMenuItembyPath( selectedNode ) );
                        }
                    }
                }
            } );
        }
        return treeMenu;
    }

    private void loadNodes( DefaultMutableTreeNode rootDMTN, MenuItem rootMenu )
    {

        for( MenuItem menuItem : rootMenu )
        {
            DefaultMutableTreeNode son = new DefaultMutableTreeNode( menuItem );
            rootDMTN.add( son );
            loadNodes( son, menuItem );
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

    private MenuItem getMenuItembyPath( TreePath path )
    {
        Object obj = path.getPath( )[ path.getPath( ).length - 1 ];
        if( obj instanceof DynamicUtilTreeNode )
        {
            return ( ( MenuItem ) ( ( DynamicUtilTreeNode ) ( path.getPath( )[ path.getPath( ).length - 1 ] ) ).getUserObject( ) );

        }
        else if( obj instanceof DefaultMutableTreeNode )// Aqui entra cuando el MenuItem fue creado en esta secion
        {
            return ( ( MenuItem ) ( ( DefaultMutableTreeNode ) ( path.getPath( )[ path.getPath( ).length - 1 ] ) ).getUserObject( ) );
        }
        else
        {
            return null;
        }
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
            Object obj = path.getPath( )[ path.getPath( ).length - 1 ];
            if( obj instanceof DynamicUtilTreeNode )
            {
                try
                {
                    ( ( MenuItem ) ( ( DynamicUtilTreeNode ) ( path.getPath( )[ path.getPath( ).length - 1 ] ) ).getUserObject( ) ).setName( newValue.toString( ) );
                }
                catch( SQLException e )
                {
                    JOptionPane.showMessageDialog( window, "Error inesperado tratando de actualizar el nombre del item del menu \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
                    e.printStackTrace( );
                }
            }
            else if( obj instanceof DefaultMutableTreeNode )// Aqui entra cuando el MenuItem fue creado en esta secion
            {
                try
                {
                    ( ( MenuItem ) ( ( DefaultMutableTreeNode ) ( path.getPath( )[ path.getPath( ).length - 1 ] ) ).getUserObject( ) ).setName( newValue.toString( ) );
                }
                catch( SQLException e )
                {
                    JOptionPane.showMessageDialog( window, "Error inesperado tratando de actualizar el nombre del item del menu \n " + e.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
                    e.printStackTrace( );
                }
            }
            else
            {
                System.err.println( obj.getClass( ) );
            }
            // System.out.println( ( ( MenuItem ) ( ( DynamicUtilTreeNode ) ( path.getPath( )[ path.getPath( ).length - 1 ] ) ).getUserObject( ) ) );
            // ( ( MenuItem ) ( ( DynamicUtilTreeNode ) ( path.getPath( )[ path.getPath( ).length - 1 ] ) ).getUserObject( ) ).setNombre( newValue.toString( ) );
            // System.out.println( path );
            // System.out.println( newValue );
        }

    }

    public void actionPerformed( ActionEvent e )
    {

        String command = e.getActionCommand( );

        if( command.endsWith( ADD_MENUITEM ) )
        {
            try
            {
                Enumeration<TreePath> a = treeMenu.getExpandedDescendants( selectedNode.getParentPath( ) );// se guardan los paths abiertos para poder repintar el argbol y
                                                                                                           // posteriormente volverlos a abrir

                DefaultMutableTreeNode d = ( DefaultMutableTreeNode )selectedNode.getPath( )[ selectedNode.getPath( ).length - 1 ];
                MenuItem padre = ( MenuItem )d.getUserObject( );
                MenuItem newSon = new MenuItem( -1, NUEVO_ITEM, "description", 2, true, "icon", new Vector<Image>( ), padre, new Vector<PriceItem>( ), true ); // TODO
                                                                                                                                                               // cambiar
                                                                                                                                                               // por los
                                                                                                                                                               // valores
                                                                                                                                                               // que
                                                                                                                                                               // deberia
                // entrar por default

                padre.add( newSon );

                DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode( newSon );
                selectedNodeTreeNode = nuevo;

                // TODO hay que actualizar los paneles

                d.add( nuevo );// TODO aqui hay un BUG, cada vez que se agrega a un nodo que no se a habierto antes, se pinga dos veces
                ( ( DefaultTreeModel )treeMenu.getModel( ) ).reload( );
                treeMenu.repaint( );

                while( a.hasMoreElements( ) )// vuelve a abrir los paths que estaban abiertos abntes de que se comenzara la edicion
                {
                    TreePath p = a.nextElement( );
                    treeMenu.expandPath( p );
                    System.out.println( p );
                }
                TreePath tp = treeMenu.getNextMatch( NUEVO_ITEM, treeMenu.getRowCount( ) - 1, Position.Bias.Backward ); // TODO no deberia buscar en todo el arbol sino
                                                                                                                        // desde
                                                                                                                        // la parte de abajo de la rama en la que se agrefo
                                                                                                                        // el
                                                                                                                        // nuevo item
                treeMenu.startEditingAtPath( tp );
            }
            catch( SQLException e1 )
            {
                JOptionPane.showMessageDialog( window, "Error inesperado tratando de crear el nuevo item \n " + e1.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
                e1.printStackTrace( );
            }

        }
        else if( command.equals( DEL_MENUITEM ) )
        {
            try
            {
                Enumeration<TreePath> a = treeMenu.getExpandedDescendants( selectedNode.getParentPath( ).getParentPath( ) );// se guardan los paths abiertos para poder
                                                                                                                            // repintar el argbol y
                // posteriormente volverlos a abrir

                DefaultMutableTreeNode d = ( DefaultMutableTreeNode )selectedNode.getPath( )[ selectedNode.getPath( ).length - 1 ];
                MenuItem menuItem = ( MenuItem )d.getUserObject( );
                menuItem.delete( );
                treeMenu.removeSelectionPath( selectedNode );

                ( ( DefaultTreeModel )treeMenu.getModel( ) ).removeNodeFromParent( d );
                ( ( DefaultTreeModel )treeMenu.getModel( ) ).reload( );
                treeMenu.repaint( );

                while( a.hasMoreElements( ) )// vuelve a abrir los paths que estaban abiertos abntes de que se comenzara la edicion
                {
                    TreePath p = a.nextElement( );
                    System.out.println( p );
                    treeMenu.expandPath( p );
                }
            }
            catch( SQLException e1 )
            {
                JOptionPane.showMessageDialog( window, "Error inesperado tratando de eleminar el item \n " + e1.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
                e1.printStackTrace( );
            }
        }
        else if( command.equals( DEL_CATEGORY ) )
        {
            try
            {
                Enumeration<TreePath> a = treeMenu.getExpandedDescendants( selectedNode );// se guardan los paths abiertos para poder
                                                                                          // repintar el argbol y
                // posteriormente volverlos a abrir

                DefaultMutableTreeNode d = ( DefaultMutableTreeNode )selectedNode.getPath( )[ selectedNode.getPath( ).length - 1 ];
                MenuItem menuItem = ( MenuItem )d.getUserObject( );
                
                
                int reponce = JOptionPane.showConfirmDialog( window,"Esta apunto de eliminar la categoria " + menuItem + ". \n Esta operacion no se pude deshacer, \n ¿Desea eliminar la categoria y todos sus items? ","Advertencia",JOptionPane.OK_CANCEL_OPTION);
                if(reponce == JOptionPane.CANCEL_OPTION)
                {
                    return;
                }
                
                menuItem.delete( );
                treeMenu.removeSelectionPath( selectedNode );

                ( ( DefaultTreeModel )treeMenu.getModel( ) ).removeNodeFromParent( d );
                ( ( DefaultTreeModel )treeMenu.getModel( ) ).reload( );
                treeMenu.repaint( );

                while( a != null && a.hasMoreElements( ) )// vuelve a abrir los paths que estaban abiertos abntes de que se comenzara la edicion
                {
                    TreePath p = a.nextElement( );
                    System.out.println( p );
                    treeMenu.expandPath( p );
                }
            }
            catch( SQLException e1 )
            {
                JOptionPane.showMessageDialog( window, "Error inesperado tratando de eleminar el item \n " + e1.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
                e1.printStackTrace( );
            }
        }
        else if( command.equals( ADD_CATEGORY ) )
        {
            try
            {
                Enumeration<TreePath> a = treeMenu.getExpandedDescendants( selectedNode );// se guardan los paths abiertos para poder repintar el argbol y
                                                                                          // posteriormente volverlos a abrir

                DefaultMutableTreeNode d = ( DefaultMutableTreeNode )selectedNode.getPath( )[ selectedNode.getPath( ).length - 1 ];
                MenuItem padre = ( MenuItem )d.getUserObject( );
                MenuItem newSon = new MenuItem( -1, NUEVO_ITEM, "description", 2, true, "icon", new Vector<Image>( ), padre, new Vector<PriceItem>( ), true ); // TODO
                                                                                                                                                               // cambiar
                                                                                                                                                               // por los
                                                                                                                                                               // valores
                                                                                                                                                               // que
                                                                                                                                                               // deberia
                // entrar por default

                padre.add( newSon );

                DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode( newSon );
                selectedNodeTreeNode = nuevo;

                // TODO hay que actualizar los paneles

                d.add( nuevo );// TODO aqui hay un BUG, cada vez que se agrega a un nodo que no se a habierto antes, se pinga dos veces
                ( ( DefaultTreeModel )treeMenu.getModel( ) ).reload( );
                treeMenu.repaint( );

                while( a != null && a.hasMoreElements( ) )// vuelve a abrir los paths que estaban abiertos abntes de que se comenzara la edicion
                {
                    TreePath p = a.nextElement( );
                    treeMenu.expandPath( p );
                    System.out.println( p );
                }
                TreePath tp = treeMenu.getNextMatch( NUEVO_ITEM, treeMenu.getRowCount( ) - 1, Position.Bias.Backward ); // TODO no deberia buscar en todo el arbol sino
                                                                                                                        // desde
                                                                                                                        // la parte de abajo de la rama en la que se agrefo
                                                                                                                        // el
                                                                                                                        // nuevo item
                treeMenu.startEditingAtPath( tp );
            }
            catch( SQLException e1 )
            {
                JOptionPane.showMessageDialog( window, "Error inesperado tratando de crear el nuevo item \n " + e1.getMessage( ), "ERROR", JOptionPane.ERROR_MESSAGE );
                e1.printStackTrace( );
            }
        }

    }
}
