package co.com.activetek.genericmenu.ui.menu;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import java.awt.BorderLayout;
import java.awt.Panel;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import co.com.activetek.genericmenu.server.beans.Image;
import co.com.activetek.genericmenu.ui.OsakiMenu;
import co.com.activetek.genericmenu.ui.utils.MyImageIcon;
import java.util.Vector;
import javax.swing.BoxLayout;

/**
 * Clase que contiene las imagenes del producto
 * @author daniel.rodriguez
 * 
 */
public class ProductInfoImagePanel extends JPanel
{
    private final static String NO_IMAGE = "./images/GenericMenu/noimg.gif";
    private final static int WIDHT = 200;
    private final static int HEIGHT = 200;

    private static final long serialVersionUID = 1L;
    private JLabel labelImages = null;
    private JPanel panelImageOptions = null;
    private JCheckBox checkBoxEneble = null;
    private JButton buttonDelete = null;
    private JButton buttonAdd = null;
    private JPanel panelButtonsNextBack = null;
    private JButton buttonBack = null;
    private JButton buttonNext = null;
    private Vector<Image> images; // @jve:decl-index=0:
    private int image;
    private OsakiMenu window;

    /**
     * This is the default constructor
     */
    public ProductInfoImagePanel( OsakiMenu window )
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
        labelImages = new JLabel( );
        labelImages.setIcon( MyImageIcon.getInstance( ).setSize( new ImageIcon( NO_IMAGE ).getImage( ), WIDHT, HEIGHT, this ) );
        labelImages.setText( "" );
        labelImages.setHorizontalAlignment( SwingConstants.CENTER );
        this.setSize( 300, 600 );

        this.setLayout( new BorderLayout( ) );
        this.setBorder( BorderFactory.createTitledBorder( null, "Fotos", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null ) );
        this.add( labelImages, BorderLayout.NORTH );

        Panel p = new Panel( );
        p.setLayout( new BoxLayout( p, BoxLayout.Y_AXIS ) );
        p.add( getPanelButtonsNextBack( ) );
        p.add( getPanelImageOptions( ) );
        this.add( p );
    }

    /**
     * This method initializes panelImageOptions
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelImageOptions( )
    {
        if( panelImageOptions == null )
        {
            GridLayout gridLayout = new GridLayout( );
            gridLayout.setRows( 1 );
            gridLayout.setColumns( 2 );
            panelImageOptions = new JPanel( );
            panelImageOptions.setLayout( gridLayout );
            panelImageOptions.add( getButtonDelete( ), null );
            panelImageOptions.add( getButtonAdd( ), null );
            panelImageOptions.add( getCheckBoxEneble( ), null );
        }
        return panelImageOptions;
    }

    /**
     * This method initializes checkBoxEneble
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCheckBoxEneble( )
    {
        if( checkBoxEneble == null )
        {
            checkBoxEneble = new JCheckBox( "enable" );
        }
        return checkBoxEneble;
    }

    /**
     * This method initializes buttonDelete
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonDelete( )
    {
        if( buttonDelete == null )
        {
            buttonDelete = new JButton( "Eliminar imagen" );
        }
        return buttonDelete;
    }
    /**
     * This method initializes buttonDelete
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonAdd( )
    {
        if( buttonAdd == null )
        {
            buttonAdd = new JButton( "Agregar Imagenes" );
            buttonAdd.addActionListener( new ActionListener( )
            {
                public void actionPerformed( ActionEvent e )
                {
                    final JFileChooser fc = new JFileChooser( );

                    int returnVal = fc.showOpenDialog( window );
                    fc.addChoosableFileFilter( new ImageFilter( ) );// TODO por alguna extraña razon este filtro no esta funcionando
                    // TODO como default tiene que salir el pantallaso miniatura de las imagenes con el look and feel de windows
                    fc.setAcceptAllFileFilterUsed( false );

                    if( returnVal == JFileChooser.APPROVE_OPTION )
                    {
                        File file = fc.getSelectedFile( );
                        // This is where a real application would open the file.
                        window.addMenuImtemImage( file );
                    }
                    else
                    {
                        System.out.println( "Open command cancelled by user." );
                    }
                }
            } );
        }
        return buttonAdd;
    }

    /**
     * This method initializes panelButtonsNextBack
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPanelButtonsNextBack( )
    {
        if( panelButtonsNextBack == null )
        {
            GridLayout gridLayout1 = new GridLayout( );
            gridLayout1.setRows( 1 );
            gridLayout1.setColumns( 2 );
            panelButtonsNextBack = new JPanel( );
            panelButtonsNextBack.setLayout( gridLayout1 );
            panelButtonsNextBack.add( getButtonBack( ), null );
            panelButtonsNextBack.add( getButtonNext( ), null );
        }
        return panelButtonsNextBack;
    }

    /**
     * This method initializes buttonBack
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonBack( )
    {
        if( buttonBack == null )
        {
            buttonBack = new JButton( "anterior" );
            buttonBack.addActionListener( new ActionListener( )
            {
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    if( image > 0 )
                    {
                        image--;
                        updateImage( );
                    }

                }
            } );
        }
        return buttonBack;
    }

    /**
     * This method initializes buttonNext
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonNext( )
    {
        if( buttonNext == null )
        {
            buttonNext = new JButton( "siguiente" );
            buttonNext.addActionListener( new ActionListener( )
            {

                @Override
                public void actionPerformed( ActionEvent e )
                {
                    if( image >= 0 && image < images.size( ) )
                    {
                        image++;
                        updateImage( );
                    }

                }
            } );
        }
        return buttonNext;
    }

    public void setImages( Vector<Image> images2 )
    {
        images = images2;
        image = 0;
        updateImage( );

    }

    public void updateImage( )
    {
        if( images.size( ) > 0 )
        {
            labelImages.setIcon( MyImageIcon.getInstance( ).setSize( new ImageIcon( images.get( image ).getUrl( ) ).getImage( ), WIDHT, HEIGHT, this ) );
        }
        else
        {
            image = -1;
            labelImages.setIcon( MyImageIcon.getInstance( ).setSize( new ImageIcon( NO_IMAGE ).getImage( ), WIDHT, HEIGHT, this ) );
        }
        updateButtons( );
    }
    public void updateButtons( )
    {
        if( image == -1 || images.size( ) == 1 )
        {
            buttonNext.setEnabled( false );
            buttonBack.setEnabled( false );
        }
        else if( image == images.size( ) - 1 )
        {
            buttonNext.setEnabled( false );
            buttonBack.setEnabled( true );
        }
        else if( image == 0 )
        {
            buttonNext.setEnabled( true );
            buttonBack.setEnabled( false );
        }
        else
        {
            buttonNext.setEnabled( true );
            buttonBack.setEnabled( true );
        }
    }

    public class ImageFilter extends FileFilter
    {

        // Accept all directories and all gif, jpg, tiff, or png files.
        public boolean accept( File f )
        {
            if( f.isDirectory( ) )
            {
                return true;
            }

            String extension = Utils.getExtension( f );
            if( extension != null )
            {
                if( extension.equals( Utils.tiff ) || extension.equals( Utils.tif ) || extension.equals( Utils.gif ) || extension.equals( Utils.jpeg ) || extension.equals( Utils.jpg ) || extension.equals( Utils.png ) )
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }

            return false;
        }

        // The description of this filter
        public String getDescription( )
        {
            return "Just Images";
        }
    }

    public static class Utils
    {

        public final static String jpeg = "jpeg";
        public final static String jpg = "jpg";
        public final static String gif = "gif";
        public final static String tiff = "tiff";
        public final static String tif = "tif";
        public final static String png = "png";

        /*
         * Get the extension of a file.
         */
        public static String getExtension( File f )
        {
            String ext = null;
            String s = f.getName( );
            int i = s.lastIndexOf( '.' );

            if( i > 0 && i < s.length( ) - 1 )
            {
                ext = s.substring( i + 1 ).toLowerCase( );
            }
            return ext;
        }
    }
}
