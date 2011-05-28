package co.com.activetek.genericmenu.ui.orders;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.EtchedBorder;

public class OrderItem extends JPanel
{
    public OrderItem() {
        setBorder(null);
        setLayout(new MigLayout("", "[14px][grow][]", "[14px][][]"));
        
        JPanel cuantityPanel = new JPanel();
        cuantityPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(cuantityPanel, "cell 0 0 1 3,alignx left,aligny top");
        
        JLabel label = new JLabel("1");
        label.setFont(new Font("Tahoma", Font.PLAIN, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        cuantityPanel.add(label);
        
        JLabel lblMenuitemdescriptionlabel = new JLabel("menuItemDescriptionLabel");
        add(lblMenuitemdescriptionlabel, "cell 1 0");
        
        JCheckBox checkBox = new JCheckBox("");
        checkBox.setHorizontalAlignment(SwingConstants.RIGHT);
        add(checkBox, "cell 2 0");
        
        JLabel lblPricedescriptionlabel = new JLabel("priceDescriptionLabel");
        add(lblPricedescriptionlabel, "cell 1 1");
        
        JLabel lblUsercomentslabel = new JLabel("userComentsLabel");
        add(lblUsercomentslabel, "cell 1 2");
    }

}
