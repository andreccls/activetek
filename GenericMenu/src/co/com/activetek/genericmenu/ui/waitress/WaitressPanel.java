package co.com.activetek.genericmenu.ui.waitress;

import javax.swing.JPanel;

import co.com.activetek.genericmenu.server.beans.Waitress;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class WaitressPanel extends JPanel
{
    private final static String NON_WAITRESS = "./images/GenericMenu/waitresses/none.jpg";
    private Waitress waitress;
    private JLabel labelPhoto = null;
    public WaitressPanel( Waitress waitress )
    {
        super( );
        this.waitress = waitress;
        initialize( );
    }
    /**
     * This method initializes this
     * 
     */
    private void initialize( )
    {
        labelPhoto = new JLabel( );
        labelPhoto.setIcon( new ImageIcon( waitress.getPhoto( ) == null ? NON_WAITRESS : waitress.getPhoto( ) ) );
        labelPhoto.setHorizontalTextPosition(SwingConstants.CENTER);
        labelPhoto.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout( new BorderLayout( ) );
        this.setSize( new Dimension( 189, 163 ) );
        this.setBorder( BorderFactory.createTitledBorder( null, waitress.getNick( ), TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font( "DialogInput", Font.ITALIC, 12 ), new Color( 146, 51, 51 ) ) );
        this.add( labelPhoto, BorderLayout.CENTER );

    }
} // @jve:decl-index=0:visual-constraint="10,10"
