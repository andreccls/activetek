package co.com.activetek.genericmenu.ui.waitress;

import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class WaitressEditDialog extends JDialog
{
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtNick;
    public WaitressEditDialog() {
        setTitle("Agregar Menseo");
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
        
        JLabel lblNobre = new JLabel("Nombre");
        lblNobre.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNobre = new GridBagConstraints();
        gbc_lblNobre.anchor = GridBagConstraints.WEST;
        gbc_lblNobre.insets = new Insets(0, 0, 5, 5);
        gbc_lblNobre.gridx = 0;
        gbc_lblNobre.gridy = 0;
        getContentPane().add(lblNobre, gbc_lblNobre);
        
        txtNombre = new JTextField();
        txtNombre.setText("Nombre");
        GridBagConstraints gbc_txtNombre = new GridBagConstraints();
        gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
        gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNombre.gridx = 1;
        gbc_txtNombre.gridy = 0;
        gbc_txtNombre.weightx = 20.0;
        getContentPane().add(txtNombre, gbc_txtNombre);
        txtNombre.setColumns(10);
        
        JLabel lblApellido = new JLabel("Apellido");
        lblApellido.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblApellido = new GridBagConstraints();
        gbc_lblApellido.anchor = GridBagConstraints.WEST;
        gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
        gbc_lblApellido.gridx = 0;
        gbc_lblApellido.gridy = 1;
        getContentPane().add(lblApellido, gbc_lblApellido);
        
        txtApellido = new JTextField();
        txtApellido.setText("Apellido");
        GridBagConstraints gbc_txtApellido = new GridBagConstraints();
        gbc_txtApellido.insets = new Insets(0, 0, 5, 0);
        gbc_txtApellido.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtApellido.gridx = 1;
        gbc_txtApellido.gridy = 1;
        getContentPane().add(txtApellido, gbc_txtApellido);
        txtApellido.setColumns(10);
        
        JLabel lblNick = new JLabel("Nick");
        lblNick.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblNick = new GridBagConstraints();
        gbc_lblNick.anchor = GridBagConstraints.WEST;
        gbc_lblNick.insets = new Insets(0, 0, 5, 5);
        gbc_lblNick.gridx = 0;
        gbc_lblNick.gridy = 2;
        getContentPane().add(lblNick, gbc_lblNick);
        
        txtNick = new JTextField();
        txtNick.setText("nick");
        GridBagConstraints gbc_txtNick = new GridBagConstraints();
        gbc_txtNick.insets = new Insets(0, 0, 5, 0);
        gbc_txtNick.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNick.gridx = 1;
        gbc_txtNick.gridy = 2;
        getContentPane().add(txtNick, gbc_txtNick);
        txtNick.setColumns(10);
        
        JButton btnAgregarImagen = new JButton("Agregar Imagen");
        GridBagConstraints gbc_btnAgregarImagen = new GridBagConstraints();
        gbc_btnAgregarImagen.anchor = GridBagConstraints.WEST;
        gbc_btnAgregarImagen.insets = new Insets(0, 0, 5, 5);
        gbc_btnAgregarImagen.gridx = 0;
        gbc_btnAgregarImagen.gridy = 3;
        getContentPane().add(btnAgregarImagen, gbc_btnAgregarImagen);
        
        JLabel lblImagen = new JLabel("imagen");
        GridBagConstraints gbc_lblImagen = new GridBagConstraints();
        gbc_lblImagen.anchor = GridBagConstraints.WEST;
        gbc_lblImagen.insets = new Insets(0, 0, 5, 0);
        gbc_lblImagen.gridx = 1;
        gbc_lblImagen.gridy = 3;
        getContentPane().add(lblImagen, gbc_lblImagen);
        
        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.gridwidth = 2;
        gbc_panel.insets = new Insets(0, 0, 0, 5);
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 4;
        getContentPane().add(panel, gbc_panel);
        panel.setLayout(new GridLayout(0, 2, 0, 0));
        
        JButton btnAceptar = new JButton("aceptar");
        panel.add(btnAceptar);
        
        JButton btnCancelar = new JButton("cancelar");
        panel.add(btnCancelar);
        setSize( 618 , 172 );
    }

}
