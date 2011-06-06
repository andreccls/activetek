package co.com.activetek.aclocking.ui;

import javax.swing.JFrame;

import co.com.activetek.aclocking.world.AClock;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.Panel;
import javax.swing.JButton;
import java.awt.Label;
import javax.swing.JPanel;

public class AClockingUI extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private AClock aclock;

    public AClockingUI( )
    {
        setTitle("Control de empleados");
        this.aclock = new AClock( );
        this.setSize( 548,330 );
        getContentPane().setLayout(new MigLayout("", "[grow][grow]", "[][grow][grow][]"));
        
        Panel panelEmployees = new Panel();
        getContentPane().add(panelEmployees, "cell 0 0");
        panelEmployees.setLayout(new MigLayout("", "[][]", "[][][][]"));
        
        JLabel lblEmployee = new JLabel("Empleados");
        panelEmployees.add(lblEmployee, "cell 0 1");
        
        JList listEmployees = new JList();
        listEmployees.setSelectedIndex(1);
        listEmployees.setListData( aclock.getEmployees( ).toArray( ) );
        listEmployees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //scrollPaneEmployees.setViewportView(list);
        
        JScrollPane scrollPaneEmployees = new JScrollPane(listEmployees);
        panelEmployees.add(scrollPaneEmployees, "cell 0 2");
        
        Panel panelEmplyeeButtons = new Panel();
        panelEmployees.add(panelEmplyeeButtons, "cell 0 3");
        
        JButton btnAddEmployee = new JButton("Agregar");
        panelEmplyeeButtons.add(btnAddEmployee);
        
        JButton btnEditEmployee = new JButton("Editar");
        panelEmplyeeButtons.add(btnEditEmployee);
        
        JButton btnDeleteEmployee = new JButton("Eliminar");
        panelEmplyeeButtons.add(btnDeleteEmployee);
        
        Panel panelSchedules = new Panel();
        getContentPane().add(panelSchedules, "cell 1 0");
        panelSchedules.setLayout(new MigLayout("", "[grow][]", "[][][][grow]"));
        
        Label labelSchedules = new Label("Horarios");
        panelSchedules.add(labelSchedules, "flowy,cell 0 1");
        
        JList listSchedules = new JList();
        listSchedules.setSelectedIndex(1);
        listSchedules.setListData( aclock.getSchedules( ).toArray( ) );
        //scrollPane.setViewportView(list);
        
        JScrollPane scrollPane = new JScrollPane(listSchedules);
        panelSchedules.add(scrollPane, "cell 0 2");
        
        JPanel panelScheduleButtons = new JPanel();
        panelSchedules.add(panelScheduleButtons, "cell 0 3,grow");
        
        JButton btnAddSchedule = new JButton("Agregar");
        panelScheduleButtons.add(btnAddSchedule);
        
        JButton btnEditSchedule = new JButton("Editar");
        panelScheduleButtons.add(btnEditSchedule);
        
        JButton btnDeleteSchedule = new JButton("Eliminar");
        panelScheduleButtons.add(btnDeleteSchedule);
        
        JPanel panel = new JPanel();
        getContentPane().add(panel, "cell 0 1 2 1,grow");
        
        JButton btnExit = new JButton("Salir");
        panel.add(btnExit);
        
        JButton btnGenerateReport = new JButton("Generar Reporte");
        panel.add(btnGenerateReport);
        
        
        
    }

    public final static void main( String[] arg )
    {
        AClockingUI window = new AClockingUI( );
        LogInDialog dialog = new LogInDialog( window );
        dialog.setVisible( true );
    }
}
