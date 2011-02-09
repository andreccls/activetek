package co.com.activetek.genericmenu.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import co.com.activetek.genericmenu.server.beans.MenuItem;


public class GenericMenuDAO {

	private static GenericMenuDAO instance;
	private Connection conn;
	
	public GenericMenuDAO() throws SQLException
	{
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/genericMenu","root","");
	}
	
	
	public static GenericMenuDAO getInstance() throws SQLException
	{
		if(instance==null)
		{
			instance = new GenericMenuDAO();
		}
		return instance;
	}
	
	public void showDatabses() throws SQLException
	{
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("show databases");
		while(rs.next())
		{
			System.out.println(rs.getString(1));
		}
	
	}


	public Vector<MenuItem> getChildren(int idParent) throws SQLException 
	{
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM menuitem WHERE parentId "+(idParent==-1?"IS NULL":" = "+ idParent));
		Vector<MenuItem> res = new Vector<MenuItem>();
		while(rs.next())
		{
			res.add(new MenuItem(rs.getInt("menuItem_id"),rs.getString("nombre"),rs.getString("description"),rs.getBoolean("enable"),rs.getString("icon")));
		}	
		
		return res;
	}
}
