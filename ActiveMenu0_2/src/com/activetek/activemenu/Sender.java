package com.activetek.activemenu;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;

public class Sender{

	private PrintWriter write;
	private Socket sock;
	private static Sender instance;
	
	public Sender(Socket s)
	{
		sock=s;
		try {
			write=new PrintWriter(sock.getOutputStream(),true);
		} catch (IOException e) {
			Log.d("Sender",e.getMessage());
		}
	}
	
	public Sender()
	{
		sock=null;
		write=null;
	}
	
	public static synchronized Sender getInstance() {
		if (instance == null)
			instance = new Sender();
		return instance;
	}
	
	public PrintWriter getWrite()
	{
		return write;
	}
	
}
