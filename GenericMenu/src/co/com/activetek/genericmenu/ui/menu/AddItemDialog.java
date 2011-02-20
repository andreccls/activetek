package co.com.activetek.genericmenu.ui.menu;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Panel;

import javax.swing.JDialog;
import java.awt.Dimension;

public class AddItemDialog extends JDialog
{

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    /**
     * @param owner
     */
    public AddItemDialog( Panel owner )
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
        this.setSize(640, 407);
        this.setContentPane( getJContentPane( ) );
    }
    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane( )
    {
        if( jContentPane == null )
        {
            jContentPane = new JPanel( );
            jContentPane.setLayout( new BorderLayout( ) );
        }
        return jContentPane;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
