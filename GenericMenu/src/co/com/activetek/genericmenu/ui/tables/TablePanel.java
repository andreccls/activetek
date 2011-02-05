package co.com.activetek.genericmenu.ui.tables;

import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class TablePanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private JLabel labelNumber = null;
    private JLabel labelIcon = null;

    /**
     * This is the default constructor
     */
    public TablePanel( )
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
        labelIcon = new JLabel();
        labelIcon.setText("");
        ImageIcon ic=new ImageIcon("./images/GenericMenu/ui/free_table.jpg");       
        labelIcon.setIcon(setSize( ic.getImage( ), 200,200 ) );
        labelNumber = new JLabel();
        labelNumber.setText("1");
        labelNumber.setHorizontalAlignment(SwingConstants.CENTER);
        
        this.setLayout(new BorderLayout());
        this.add(labelNumber, BorderLayout.NORTH);
        this.add(labelIcon, BorderLayout.CENTER);
    }
    
    @SuppressWarnings("unused")
    private ImageIcon scale(Image src, double scale) {
        int w = (int)(scale*src.getWidth(this));
        int h = (int)(scale*src.getHeight(this));
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage dst = new BufferedImage(w, h, type);
        Graphics2D g2 = dst.createGraphics();
        g2.drawImage(src, 0, 0, w, h, this);
        g2.dispose();
        return new ImageIcon(dst);
    }
    private ImageIcon setSize(Image src, int w,int h) {
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage dst = new BufferedImage(w, h, type);
        Graphics2D g2 = dst.createGraphics();
        g2.drawImage(src, 0, 0, w, h, this);
        g2.dispose();
        return new ImageIcon(dst);
    }

}
