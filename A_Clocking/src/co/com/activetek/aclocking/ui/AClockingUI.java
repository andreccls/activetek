package co.com.activetek.aclocking.ui;

import javax.swing.JFrame;

import co.com.activetek.aclocking.world.AClock;

public class AClockingUI extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private AClock aclock;

    public AClockingUI( )
    {
        this.aclock = new AClock( );
    }

    public final static void main( String[] arg )
    {
        LogInDialog dialog = new LogInDialog( );
        dialog.setVisible( true );
    }
}
