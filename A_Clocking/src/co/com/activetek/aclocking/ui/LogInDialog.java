package co.com.activetek.aclocking.ui;

import javax.swing.JDialog;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class LogInDialog extends JDialog
{
    private JTextField txtAdmin;
    private JPasswordField passwordField;
    public LogInDialog() {
        setTitle("Inciar Sesion");
        getContentPane().setLayout(new MigLayout("", "[][grow]", "[][]"));
        
        JLabel lblUsuario = new JLabel("Usuario:");
        getContentPane().add(lblUsuario, "cell 0 0,alignx trailing");
        
        txtAdmin = new JTextField();
        txtAdmin.setText("admin");
        getContentPane().add(txtAdmin, "cell 1 0,growx");
        txtAdmin.setColumns(10);
        
        JLabel lblPassword = new JLabel("Password:");
        getContentPane().add(lblPassword, "cell 0 1,alignx trailing");
        
        passwordField = new JPasswordField();
        getContentPane().add(passwordField, "cell 1 1,growx");
    }

}
