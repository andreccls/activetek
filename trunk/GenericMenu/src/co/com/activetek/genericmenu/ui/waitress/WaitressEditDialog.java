package co.com.activetek.genericmenu.ui.waitress;

import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import co.com.activetek.genericmenu.server.beans.Waitress;
import co.com.activetek.genericmenu.ui.ActiveMenu;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WaitressEditDialog extends JDialog implements ActionListener
{
    private static String ACEPTAR = "ACEPTAR";
    private static String CANCELAR = "CANCELAR";

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtNick;
    private JLabel lblImagen;
    private Waitress waitress;
    private File pathPhoto;
    private ActiveMenu window;

    public WaitressEditDialog( ActiveMenu window, Waitress waitress )
    {
        this.waitress = waitress;
        this.window = window;
        if( waitress == null )
            setTitle( "Agregar Menseo" );
        else
            setTitle( "Editar Mesero" );
        GridBagLayout gridBagLayout = new GridBagLayout( );
        gridBagLayout.columnWidths = new int[]{ 0, 0, 0 };
        gridBagLayout.rowHeights = new int[]{ 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[]{ 1.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[]{ 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        getContentPane( ).setLayout( gridBagLayout );

        JLabel lblNobre = new JLabel( "Nombre" );
        lblNobre.setHorizontalAlignment( SwingConstants.LEFT );
        GridBagConstraints gbc_lblNobre = new GridBagConstraints( );
        gbc_lblNobre.anchor = GridBagConstraints.WEST;
        gbc_lblNobre.insets = new Insets( 0, 0, 5, 5 );
        gbc_lblNobre.gridx = 0;
        gbc_lblNobre.gridy = 0;
        getContentPane( ).add( lblNobre, gbc_lblNobre );

        txtNombre = new JTextField( );
        GridBagConstraints gbc_txtNombre = new GridBagConstraints( );
        gbc_txtNombre.insets = new Insets( 0, 0, 5, 0 );
        gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNombre.gridx = 1;
        gbc_txtNombre.gridy = 0;
        gbc_txtNombre.weightx = 20.0;
        getContentPane( ).add( txtNombre, gbc_txtNombre );
        txtNombre.setColumns( 10 );
        if( waitress != null )
            txtNombre.setText( waitress.getName( ) );

        JLabel lblApellido = new JLabel( "Apellido" );
        lblApellido.setHorizontalAlignment( SwingConstants.LEFT );
        GridBagConstraints gbc_lblApellido = new GridBagConstraints( );
        gbc_lblApellido.anchor = GridBagConstraints.WEST;
        gbc_lblApellido.insets = new Insets( 0, 0, 5, 5 );
        gbc_lblApellido.gridx = 0;
        gbc_lblApellido.gridy = 1;
        getContentPane( ).add( lblApellido, gbc_lblApellido );

        txtApellido = new JTextField( );
        GridBagConstraints gbc_txtApellido = new GridBagConstraints( );
        gbc_txtApellido.insets = new Insets( 0, 0, 5, 0 );
        gbc_txtApellido.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtApellido.gridx = 1;
        gbc_txtApellido.gridy = 1;
        getContentPane( ).add( txtApellido, gbc_txtApellido );
        txtApellido.setColumns( 10 );
        if( waitress != null )
            txtApellido.setText( waitress.getLastName( ) );

        JLabel lblNick = new JLabel( "Nick" );
        lblNick.setHorizontalAlignment( SwingConstants.LEFT );
        GridBagConstraints gbc_lblNick = new GridBagConstraints( );
        gbc_lblNick.anchor = GridBagConstraints.WEST;
        gbc_lblNick.insets = new Insets( 0, 0, 5, 5 );
        gbc_lblNick.gridx = 0;
        gbc_lblNick.gridy = 2;
        getContentPane( ).add( lblNick, gbc_lblNick );

        txtNick = new JTextField( );
        GridBagConstraints gbc_txtNick = new GridBagConstraints( );
        gbc_txtNick.insets = new Insets( 0, 0, 5, 0 );
        gbc_txtNick.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNick.gridx = 1;
        gbc_txtNick.gridy = 2;
        getContentPane( ).add( txtNick, gbc_txtNick );
        txtNick.setColumns( 10 );
        if( waitress != null )
            txtNick.setText( waitress.getNick( ) );

        JButton btnAgregarImagen = new JButton( "Agregar Imagen" );
        GridBagConstraints gbc_btnAgregarImagen = new GridBagConstraints( );
        gbc_btnAgregarImagen.anchor = GridBagConstraints.WEST;
        gbc_btnAgregarImagen.insets = new Insets( 0, 0, 5, 5 );
        gbc_btnAgregarImagen.gridx = 0;
        gbc_btnAgregarImagen.gridy = 3;
        btnAgregarImagen.addActionListener( new ActionListener( )
        {
            public void actionPerformed( ActionEvent e )
            {
                JFileChooser fileChooser = new JFileChooser( "." );
                FileFilter filter1 = new ExtensionFileFilter( "Imagenes", new String[]{ "JPG", "JPEG", "BMP" } );
                fileChooser.setFileFilter( filter1 );
                int status = fileChooser.showOpenDialog( null );
                if( status == JFileChooser.APPROVE_OPTION )
                {
                    File selectedFile = fileChooser.getSelectedFile( );
                    pathPhoto = selectedFile;
                    lblImagen.setText( pathPhoto.getAbsolutePath( ) );
                }
                else if( status == JFileChooser.CANCEL_OPTION )
                {

                }
            }
        } );
        getContentPane( ).add( btnAgregarImagen, gbc_btnAgregarImagen );

        lblImagen = new JLabel(  );
        GridBagConstraints gbc_lblImagen = new GridBagConstraints( );
        gbc_lblImagen.anchor = GridBagConstraints.WEST;
        gbc_lblImagen.insets = new Insets( 0, 0, 5, 0 );
        gbc_lblImagen.gridx = 1;
        gbc_lblImagen.gridy = 3;
        getContentPane( ).add( lblImagen, gbc_lblImagen );

        JPanel panel = new JPanel( );
        GridBagConstraints gbc_panel = new GridBagConstraints( );
        gbc_panel.gridwidth = 2;
        gbc_panel.insets = new Insets( 0, 0, 0, 5 );
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 4;
        getContentPane( ).add( panel, gbc_panel );
        panel.setLayout( new GridLayout( 0, 2, 0, 0 ) );

        JButton btnAceptar = new JButton( "aceptar" );
        btnAceptar.addActionListener( this );
        btnAceptar.setActionCommand( ACEPTAR );
        panel.add( btnAceptar );

        JButton btnCancelar = new JButton( "cancelar" );
        btnCancelar.addActionListener( this );
        btnCancelar.setActionCommand( CANCELAR );
        panel.add( btnCancelar );
        setSize( 618, 172 );
    }
    @Override
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );
        if( command.equals( ACEPTAR ) )
        {
            if(waitress == null)
            {
                //waitress = new Waitress( -1, photo, name, lastName, nick, enable )
            }
        }
        else if( command.equals( CANCELAR ) )
        {
            this.dispose( );
        }
    }
    class ExtensionFileFilter extends FileFilter
    {
        String description;

        String extensions[];

        public ExtensionFileFilter( String description, String extension )
        {
            this( description, new String[]{ extension } );
        }

        public ExtensionFileFilter( String description, String extensions[] )
        {
            if( description == null )
            {
                this.description = extensions[ 0 ];
            }
            else
            {
                this.description = description;
            }
            this.extensions = ( String[] )extensions.clone( );
            toLower( this.extensions );
        }

        private void toLower( String array[] )
        {
            for( int i = 0, n = array.length; i < n; i++ )
            {
                array[ i ] = array[ i ].toLowerCase( );
            }
        }

        public String getDescription( )
        {
            return description;
        }

        public boolean accept( File file )
        {
            if( file.isDirectory( ) )
            {
                return true;
            }
            else
            {
                String path = file.getAbsolutePath( ).toLowerCase( );
                for( int i = 0, n = extensions.length; i < n; i++ )
                {
                    String extension = extensions[ i ];
                    if( ( path.endsWith( extension ) && ( path.charAt( path.length( ) - extension.length( ) - 1 ) ) == '.' ) )
                    {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
