package com.activetek.servertest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest 
{
	private static BufferedReader read;
	private static PrintWriter write;

	public final static void main(String[] ar) throws IOException
	{
		ServerSocket s = new ServerSocket(9999 );
		while(true)
		{
			System.out.println("Funciona!");
			Socket socket = s.accept( );
			System.out.println("llego conexion");
			read = new BufferedReader( new InputStreamReader( socket.getInputStream( ) ) );
			write = new PrintWriter( socket.getOutputStream( ) ,true);
			while(true)
			{
				String a=read.readLine( );
				System.out.println(a);
				int c=a.indexOf(":");
				String b="";
				if(c!=-1)
					b= a.substring(0,c);
				if(b.equals("MESA"))
					write.println("MAESTRO");
				else if(b.equals("MESERO"));
				else if(b.equals("end"));
				else
					write.println(a);
			}
		}
	}
}
