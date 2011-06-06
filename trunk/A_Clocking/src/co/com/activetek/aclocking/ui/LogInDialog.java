package co.com.activetek.aclocking.ui;

import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LogInDialog extends JDialog implements KeyListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JTextField txtAdmin;
    private JPasswordField passwordField;
    public LogInDialog( )
    {
        setTitle( "Inciar Sesion" );
        this.setSize( 354, 138 );
        this.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        getContentPane( ).setLayout( new MigLayout( "", "[grow][grow]", "[][][grow]" ) );

        JLabel lblUsuario = new JLabel( "Usuario:" );
        getContentPane( ).add( lblUsuario, "cell 0 0,alignx trailing" );

        txtAdmin = new JTextField( );
        txtAdmin.setText( "admin" );
        getContentPane( ).add( txtAdmin, "cell 1 0,growx" );
        txtAdmin.setColumns( 10 );

        JLabel lblPassword = new JLabel( "Password:" );
        getContentPane( ).add( lblPassword, "cell 0 1,alignx trailing" );

        passwordField = new JPasswordField( );
        getContentPane( ).add( passwordField, "cell 1 1,growx" );
        passwordField.addKeyListener( this );

        JPanel panel = new JPanel( );
        getContentPane( ).add( panel, "cell 0 2 2 1,grow" );

        JButton butAcept = new JButton( "Aceptar" );
        butAcept.addActionListener( new ActionListener( )
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                checkPassword( );
            }
        } );
        panel.add( butAcept );

        JButton butCancel = new JButton( "Cancelar" );
        butCancel.addActionListener( new ActionListener( )
        {
            public void actionPerformed( ActionEvent arg0 )
            {
                dispose( );
            }
        } );
        panel.add( butCancel );
    }
    public void checkPassword( )
    {
        if( passwordField.getText( ).equals( "pass" ) )
        {
            // TODO tiene que mostrar el JFrame            
            this.dispose( );
        }
        else
        {
            JOptionPane.showMessageDialog( this, "Usuario o contraseña invalida", "Error", JOptionPane.ERROR_MESSAGE );
        }

    }
    @Override
    public void keyPressed( KeyEvent arg0 )
    {
        System.out.println( "keyy!!!" );
        if( arg0.getKeyCode( ) == KeyEvent.VK_ENTER )
        {
            checkPassword( );
        }
    }
    @Override
    public void keyReleased( KeyEvent arg0 )
    {

    }
    @Override
    public void keyTyped( KeyEvent arg0 )
    {

    }

}
