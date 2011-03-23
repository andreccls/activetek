package co.com.activetek.genericmenu.ui.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

/**
 * Clase usada para los metodos que se necesitan adicionales de imageIncon
 * @author daniel.rodriguez
 * 
 */
public class MyImageIcon extends ImageIcon
{
    private static MyImageIcon instance;

    public static MyImageIcon getInstance( )
    {
        if( instance == null )
            instance = new MyImageIcon( );
        return instance;
    }
    /**
     * retorna la imagen por parametro, del tamaño indicado
     * @param src
     * @param w
     * @param h
     * @param io
     * @return
     */

    public ImageIcon setSize( Image src, int w, int h, ImageObserver io )
    {
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage dst = new BufferedImage( w, h, type );
        Graphics2D g2 = dst.createGraphics( );
        g2.drawImage( src, 0, 0, w, h, io );
        g2.dispose( );
        return new ImageIcon( dst );
    }

}
