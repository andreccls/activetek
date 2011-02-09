package co.com.activetek.genericmenu.ui.tables;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class TablePanel extends JPanel
{

    private static final long serialVersionUID = 1L;
    private JLabel labelNumber = null;
    private JLabel labelIcon = null;
    JPopupMenu menuTtable = new JPopupMenu();	
    JMenuItem itemDelete = new JMenuItem("delete");
    private int x;
    private int y;
    /**
     * This is the default constructor
     */
    public TablePanel( int x, int y)
    {
        super( );
        this.x =x;
        this.y =y;
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
        labelIcon.setHorizontalTextPosition(SwingConstants.CENTER);
        labelIcon.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon ic=new ImageIcon("./images/GenericMenu/ui/free_table.jpg");       
        labelIcon.setIcon(setSize( ic.getImage( ), 70,70 ) );
        labelNumber = new JLabel();
        labelNumber.setText(""+(x+1+y*MapTablesPanel.WIDTH));
        labelNumber.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        itemDelete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.out.println("delte!!!");
				
			}
		});
        menuTtable.add(itemDelete);
        this.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()==MouseEvent.BUTTON3) {
					menuTtable.show(e.getComponent(), e.getX(), e.getY());
				}
				
			}
		});
        
        this.setLayout(new BorderLayout());
        this.add(labelNumber, BorderLayout.NORTH);
        this.add(labelIcon, BorderLayout.CENTER);
    }
    

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
