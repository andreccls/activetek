package rlink.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import rlink.controls.dndTabPanel.DnDTabPanel;
import rlink.controls.dndTabPanel.DnDTabPanelListener;
import rlink.controls.dndTabPanel.TabItem;
import rlink.controls.dndTabPanel.TabUtils;

class DemoTabPanel extends JPanel implements DnDTabPanelListener
{
	protected DnDTabPanel tabPanel = new DnDTabPanel();
	protected Component curTabPage = null;
	
	public DemoTabPanel(String prefix) throws Exception
	{	
		// 1. Set up the tab panel
		this.tabPanel.setBackground(SystemColor.gray);
		
		this.setBackground(Color.green);
		
		this.setLayout(new BorderLayout());
		
		this.tabPanel.addTabListener(this);
		
		this.add(this.tabPanel, BorderLayout.NORTH);
		
		// 2. Add the tabs
		this.addTabs(prefix);
	}
	
	protected void addTabs(String prefix) throws Exception
	{
		Color[] colors = new Color[]
		{
			new Color(0x193B54),
			new Color(0xB33B00),
			new Color(0xFFFF00),
			new Color(0x5D92BA),
			new Color(0x787878),
		};
		
		int tabCount = 5;

		for(int i = 0; i < tabCount; i++)
		{
			ImageIcon icon = null;
			
			String title = "Tab with a long name " + i;
			
			if(i % 2 == 0)
			{
				title = "Tab " + i;
				icon = TabUtils.getAppIcon("Tabs/tab" + i + ".png", 16);
			}
			
			this.tabPanel.addTab(i, title, icon, colors[i], new JLabel(prefix + " Tab " + i));
		}
		
		Random rand = new Random();
		
		this.tabPanel.setSelectedIndex(Math.abs(rand.nextInt() % tabCount));
	}

	/**
	 * Update the background whenever the tab changes since we're using
	 * the gradient
	 */
	public void onTabItemChanged(TabItem ti) 
	{	
		this.repaint();
	}

	/**
	 * Change the page whenever a new tab is selected
	 */
	public void onTabSelected(TabItem ti) 
	{	
		if(this.curTabPage != null)
		{
			this.remove(this.curTabPage);
		}
		
		this.curTabPage = (Component) ti.data;
		
		this.add(this.curTabPage, BorderLayout.CENTER);
		
		this.invalidate();
		this.doLayout();
		this.repaint();
	}
	
	public void onTabRemoved(TabItem ti)
	{
		if(this.curTabPage == null)
		{
			return;
		}
		
		if(this.curTabPage != ti.data)
		{
			return;
		}
		
		this.remove(this.curTabPage);
		
		this.curTabPage = null;
		
		this.invalidate();
		this.doLayout();
		this.repaint();
	}

	/**
	 * Paint the tab gradient into the background
	 */
	@Override
	public void paint(Graphics g) 
	{
		TabItem ti = this.tabPanel.getSelectedTab();
		
		if(ti != null)
		{
			GradientPaint gp = ti.getGradient();
			
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setPaint(gp);
			
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			for(Component c : this.getComponents())
			{
				c.paint(g2d);
			}
		}
		else
		{
			super.paint(g);
		}
	}
}
