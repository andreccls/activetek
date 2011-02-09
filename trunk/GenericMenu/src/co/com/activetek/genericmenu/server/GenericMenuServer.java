package co.com.activetek.genericmenu.server;

import java.sql.SQLException;
import java.util.Vector;

import co.com.activetek.genericmenu.server.beans.MenuItem;
import co.com.activetek.genericmenu.server.exception.GenericMenuException;
import co.com.activetek.genericmenu.server.util.GenericMenuDAO;

public class GenericMenuServer {

	public GenericMenuServer() throws SQLException
	{
		GenericMenuDAO.getInstance();//para verificar que la base de datos esta arriba
	}
	public Vector<MenuItem> getChildren(int idParent) throws SQLException
	{
		return GenericMenuDAO.getInstance().getChildren(idParent);
	}
	
	//TODO eliminar esta cosa
	public final static void main (String[] a) throws SQLException
	{
		GenericMenuDAO.getInstance().showDatabses();
	}
	public MenuItem getMenuTree()throws SQLException, GenericMenuException  {
		try {
			MenuItem root = getChildren(-1).get(0);
			root.loadSons();
			return root;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new GenericMenuException("No se ha definido ningun menu en el sistema");			
		}		
	}
}
