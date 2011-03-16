package co.com.activetek.genericmenu.ui.menu;

import javax.swing.AbstractCellEditor;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.ui.OsakiMenu;

import java.awt.BorderLayout;
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

            treeMenu = new JTree( window.getMenuTree( ) );
            treeMenu.setCellEditor( new menuItemEditor( treeMenu ) );
            treeMenu.setEditable( true );//TODO cambiar para que solo lo tenga el administrador
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
    
    //-----------------------------------------------------------------------------------
    //              Otras clases    
    //-----------------------------------------------------------------------------------
    /**
     * 
     */
    class menuItemEditor extends AbstractCellEditor implements TreeCellEditor
    {

        private static final long serialVersionUID = 1L;
        ChangeEvent changeEvent = null;
        JTree tree;
        
        public menuItemEditor( JTree treeMenu )
        {
            tree = treeMenu;
        }

        @Override
        public Object getCellEditorValue( )
        {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean isCellEditable( EventObject anEvent )
        {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean shouldSelectCell( EventObject anEvent )
        {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean stopCellEditing( )
        {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void cancelCellEditing( )
        {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void addCellEditorListener( CellEditorListener l )
        {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void removeCellEditorListener( CellEditorListener l )
        {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Component getTreeCellEditorComponent( JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row )
        {
            // TODO Auto-generated method stub
            return null;
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
            // TODO Auto-generated method stub
            return null;
        }
        
    }
}
