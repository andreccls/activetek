package co.com.activetek.genericmenu.ui.utils;

import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

import co.com.activetek.genericmenu.server.util.Customization;

public class MyLookAndFeel extends DefaultMetalTheme
{
    public String getName( )
    {
        return "MyLookAndFeel";
    }

    public ColorUIResource getPrimary1( )
    {
        return Customization.PRIMARY1;
    }
    public ColorUIResource getPrimary2( )
    {
        return Customization.PRIMARY2;
    }
    public ColorUIResource getPrimary3( )
    {
        return Customization.PRIMARY3;
    }
    public ColorUIResource getSecondary1( )
    {
        return Customization.SECONDARY1;
    }
    public ColorUIResource getSecondary2( )
    {
        return Customization.SECONDARY2;
    }
    public ColorUIResource getSecondary3( )
    {
        return Customization.SECONDARY3;
    }
    protected ColorUIResource getBlack( )
    {
        return Customization.BALCK;
    }
    protected ColorUIResource getWhite( )
    {
        return Customization.WHITE;
    }
    public FontUIResource getControlTextFont( )
    {
        return new FontUIResource( "Courier New", Font.PLAIN, 14 );
    }
    public FontUIResource getMenuTextFont( )
    {
        return new FontUIResource( "Courier New", Font.PLAIN, 18 );
    }
}
