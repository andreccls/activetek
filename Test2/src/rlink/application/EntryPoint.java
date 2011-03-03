package rlink.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import rlink.controls.dndTabPanel.DnDTabPanel;
import rlink.controls.dndTabPanel.DnDTabPanelListener;
import rlink.controls.dndTabPanel.TabItem;
import rlink.controls.dndTabPanel.TabUtils;

public class EntryPoint 
{
	
	public static void main(String[] args)
	{
		try
		{
			// 1. Set up frame
			JFrame frame = new JFrame("Drag-N-Drop Tab Control Demo");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			// 2. Set up tab panels
			JPanel left = new DemoTabPanel("Originally On Left");
			JPanel right = new DemoTabPanel("Originally On Right");
			
			// 3. Set up split pane
			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
			splitPane.setResizeWeight(0.5);
			splitPane.setOneTouchExpandable(true);
			splitPane.setContinuousLayout(true);
			splitPane.setBackground(Color.gray);
			
			// 4. Add the split pane
			Container content = frame.getContentPane();
			content.add(splitPane, BorderLayout.CENTER);
	
			// 5. Show it
			frame.pack();
			frame.setSize(800, 600);
			frame.setVisible(true);
		}
		catch(Exception ex)
		{
			System.out.println("Failed: " + ex.getMessage());
		}
	}
}
