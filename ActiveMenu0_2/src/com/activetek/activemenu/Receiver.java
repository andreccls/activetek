package com.activetek.activemenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import android.util.Log;

public class Receiver extends Thread{
	
	private AbstractActivityGroup owner;
	private Socket sock;
	private BufferedReader read;
	private static Receiver instance;
	
	public Receiver(AbstractActivityGroup a, Socket s)
	{
		owner=a;
		sock=s;
		try {
			read=new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("Receiver", e.getMessage());
		}
	}
	
	public Receiver()
	{
		owner=null;
		sock=null;
		read=null;
	}
	public static synchronized Receiver getInstance() {
		if (instance == null)
			instance = new Receiver();
		return instance;
	}

	public void setOwner(AbstractActivityGroup a)
	{
		owner=a;
	}
	public void setSocket(Socket s)
	{
		sock=s;
		try {
			read=new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("Receiver", e.getMessage());
		}
	}
	public void run()
	{
		String message="";
		while(true)
		{
			try {
				message = read.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d("Receiver", e.getMessage());
			}
			if(message!=null)
				owner.notifier(message);
			else
			{
				owner.toastHandler.post(owner.toastRunnable);
				boolean success=false;
				while(!success)
				{
					try
					{
						Socket s=new Socket(ActiveMenu.SERVER_IP,9999);
						setSocket(s);
						Sender send=Sender.getInstance();
						send.setSocket(s);
						success=true;
					}
					catch(Exception e)
					{
						success=false;
					}
				}
				owner.toastHandler.post(owner.toastRunnable2);
			}
		}
	}
}
