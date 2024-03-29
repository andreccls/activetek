package com.activetek.activemenu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
/**
 * Actividad inicial del Programa
 * Realiza la gestión de conexiones y crea el árbol de datos
 * @author juan
 * Implementa la clase OnClickListener para algunos botones
 * Implementa Runnable para correr como thread en espera
 */
public class ActiveMenu extends Activity implements OnClickListener, Runnable {

	/**
	 * Constante que indica la dirección IP del servidor
	 */
	//public final static String SERVER_IP="192.168.0.103";
	//public final static String SERVER_IP="192.168.43.168";
	public final static String SERVER_IP="192.168.0.194";
	public final static String ROOT_DIR = "/data/menu";
	/**
	 * Diálogo de espera
	 */
	private ProgressDialog dialog;

	/**
	 * Proceso de carga a través de consola
	 */
	private Process process;
	
	private Toast toast;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Dispone el layout xml a utilizar
		setContentView(R.layout.main);
		// Eliminar la barra de estado
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//Botón de arranque de la aplicación, declarado en el layout
		Button addItem=(Button) findViewById(R.id.startButton);
		//Indicamos que clase implementa el listener para el botón
		addItem.setOnClickListener(this);
		toast=Toast.makeText(this, "La aplicación no pudo ser iniciada adecuadamente, por favor intente más tarde o consulte con su mesero", Toast.LENGTH_LONG);
	}

	/**
	 * Este método implementa el listener para el botón
	 * @param arg0 botón sobre el que se ha generado un evento
	 */
	@Override
	public void onClick(View arg0) {
		//Verificamos la identidad del botñon
		if (arg0 ==findViewById(R.id.startButton))
		{
			// prepare the dialog box
			dialog = new ProgressDialog(this);

			// make the progress bar cancelable
			dialog.setCancelable(false);

			// set a message text
			dialog.setMessage("Cargando Informacion...");

			// show it
			dialog.show();
			// Creamos el thread para el proceso sobre consola
			Thread t= new Thread(this);
			t.start();
		}

	}

	/**
	 * Este método implementa el hilo de ejecución sobre consola
	 */
	@Override
	public void run() {
		try {
			//Primero ejecutamos una consola virtual
			process = Runtime.getRuntime().exec("su sh");
			//Obtenemos la línea de escritura sobre la consola
			OutputStream os = process.getOutputStream();
			//Introducimos el comando a ser utilizado
			//String cmd="/data/data/eu.kowalczuk.rsync4android/files/rsync -avz Daniel@"+SERVER_IP+"::AMenu " + ROOT_DIR + "/images/ && exit";
			//writeLine( os, cmd );
            writeLine( os,"sleep 1 && exit");
			// iniciamos ejecución sobre consola
			os.flush();
			//Esperamos a que el proceso se complete
			process.waitFor();

		}
		catch ( IOException e ) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try
		{
			//Llamamos al gestor de contenidos
			CreateContent();
			//Abrimos conexión con el servidor en el puerto 9999
			Socket s= new Socket(SERVER_IP,9999);
			// Creamos la instancia de receptor de mensajes
			Receiver rec= Receiver.getInstance();
			//Ajustamos el socket creado para ser utilizado por el receptor
			rec.setSocket(s);
			//Iniciamos el hilo de escucha
			rec.start();
			//Creamos el envío de mensajes
			Sender send=Sender.getInstance();
			// Ajustamos el socket creado para ser utilizado para envìos
			send.setSocket(s);
			WifiManager wifiMan = (WifiManager) this.getSystemService(
					Context.WIFI_SERVICE);
			WifiInfo wifiInf = wifiMan.getConnectionInfo();
			String macAddr = wifiInf.getMacAddress()+"";
			Log.d("MAC", macAddr);
			send.getWrite().println("CLIENT:"+macAddr);
			// enviamos señal de que se completo el hilo, al proceso principal
			handler.sendEmptyMessage(0);
		}
		catch (Exception e)
		{
			Log.e("Socket", e.getMessage());
			// enviamos señal de que se completo el hilo, al proceso principal
			handler.sendEmptyMessage(1);
			finish();
		}

	}


	/**
	 * Este método permite escribir líneas sobre un OutputStream
	 * @param os El OutputStream para escribir
	 * @param value El mensaje a ser escrito
	 * @throws IOException Si el OutputStream no puede ser accedido correctamente
	 */
	public static void writeLine(OutputStream os, String value) throws IOException
	{
		String line = value + "\n";
		os.write( line.getBytes() );
	}

	/**
	 * Este objeto permite manejar las notificaciones de los hilos
	 */
	private Handler handler = new Handler() {
		/**
		 * Método manejador de mensajes
		 */
		@Override
		public void handleMessage(Message msg) {
			//Destruir el diálogo
			if(msg.what==0)
			{
				dialog.dismiss();
				// Preparar la siguiente actividad
				Intent in= new Intent(ActiveMenu.this, TableActivity.class);
				// Iniciar actividad y ceder el runtime a ella
				ActiveMenu.this.startActivityForResult(in,1);
				// Desechar esta actividad para liberar recursos
				finish();
			}
			else
			{
				dialog.dismiss();
				toast.show();
				finish();
			}
		}
	};

	/**
	 * Este método controla los puntos de retorno con el boton back
	 * está sobreescrito para que no haya retornos inesperados
	 */
	/**
	@Override
	public void onBackPressed() {

		return;
	}
	 */
	/**
	 * Gestor de Contenidos
	 * @throws Exception 
	 */
	public void CreateContent() throws Exception
	{
		try {
			// Intentamos abrir el json principal
			FileInputStream fis= new FileInputStream(ROOT_DIR + "/images/json.txt");
			// Convertimos el FIS a String para interpretarlo
			String str = convertStreamToString(fis);
			// Creamos un JSONObject para explorar
			JSONObject json = new JSONObject(str);
			// Llamamos al interprete
			parse(json);
		} catch (Exception e) {
			Log.e("Menu", e.toString());
			throw e;
		}
	}

	/**
	 * Este método interpreta un JSON con una estructura predefinida
	 * @param json el JSONObject a interpretar
	 */
	public void parse(JSONObject json) {
		try {
			//Obtenemos el array de categorías
			JSONArray foo=json.getJSONArray("categorias");
			//Tamaño del arreglo
			int cuenta = foo.length();
			//recorremos el arreglo
			for(int j=0; j < cuenta;j++)
			{
				//Extraemos cada categoría
				JSONObject obi= foo.getJSONObject(j);
				//Tomamos el nombre de la categoría
				String nombre= obi.getString("name");
				String thumb;
				try{
					thumb=obi.getString("photo");	
				}
				catch(Exception e)
				{
					thumb="/images/GenericMenu/waitresses/none.jpg";
				}
				//Extraemos el array de MenuItem
				JSONArray wow=obi.getJSONArray("menuitem");
				//Tamaño del arreglo
				int alfa = wow.length();
				// Creamos un ArrayList vacío para los MenuItem
				ArrayList<MenuItem> arr=new ArrayList<MenuItem>();
				//Recorremos el arreglo
				for (int k=0; k<alfa;k++)
				{
					//Extraemos cada MenuItem
					JSONObject fin=wow.getJSONObject(k);
					//Tomamos el nombre del MenuItem
					String name=fin.getString("name");
					//Extraemos el array de Imágenes
					JSONArray arraimag=fin.getJSONArray("images");
					//Creamos un arreglo vacío para los path's de las imágenes
					ArrayList<String> arras=new ArrayList<String>();
					// Recorremos el arreglo de imágenes
					if(arraimag.length()==0)
						arras.add(ROOT_DIR + "/images/GenericMenu/waitresses/none.jpg");
					else
					{
						for(int z=0; z<arraimag.length();z++)
						{
							// Extraemos cada imágen
							JSONObject finn=arraimag.getJSONObject(z);
							//Creamos el path absoluto de las imágenes
							arras.add(ROOT_DIR+finn.getString("url"));
						}
					}
					// Creamos un arreglo vacío para los precios
					ArrayList<Price> arrapric=new ArrayList<Price>();
					// Extraemos el array de Precios
					JSONArray arraprec=fin.getJSONArray("price");
					//Recorremos el array de precios
					for(int z=0; z<arraprec.length();z++)
					{
						// Extraemos cada precio
						JSONObject finn=arraprec.getJSONObject(z);
						// Creamos objeto Price y lo añadimos al arraylist
						arrapric.add(new Price(finn.getInt("price"),finn.getInt("cuantity"),finn.getInt("id"),finn.getString("descripcion")));
					}
					// Extraemos la descripción del MenuItem
					String desc;
					try
					{
						desc=fin.getString("details");
					}
					catch (Exception e)
					{
						desc="";
					}
					// Creamos el MenuItem y lo añadimos al arrayList
					// Se está creando con un código fijo
					arr.add(new MenuItem(name,15,arras,arrapric,desc));
				}
				// Creamos la Categoría
				Category aux1= new Category(nombre,arr,ROOT_DIR+thumb);
				// Creamos la instancia del almacen de categorías
				CategoryWrapper cat=CategoryWrapper.getInstance();
				// Añadimos la categoría al almacen
				cat.getCategories().add(aux1);
			}
			// Buscamos el Array de Meseros
			foo=json.getJSONArray("meseros");
			// Tamaño del arreglo
			cuenta = foo.length();
			// Recorremos el arreglo
			for(int j=0; j < cuenta;j++)
			{
				// Extraemos cada item
				JSONObject obi= foo.getJSONObject(j);
				//Extraemos los parámetros del mesero
				String name=obi.getString("nick");
				String thumb;
				try{
					thumb=obi.getString("photo");	
				}
				catch(Exception e)
				{
					thumb="/images/GenericMenu/waitresses/none.jpg";
				}
				String prec=obi.getString("id");
				// Creamos al mesero
				Waiter w= new Waiter(name,Integer.parseInt(prec),ROOT_DIR+thumb);
				//Abrimos la instancia del almacen de meseros
				WaiterWrapper wai= WaiterWrapper.getInstance();
				//añadimos al mesero creado al almacen
				wai.getWaiters().add(w);
			}
			// Buscamos el array de mesas
			foo=json.getJSONArray("mesas");
			// Tamaño del arreglo
			cuenta = foo.length();
			// Recorremos el arreglo
			for(int j=0; j < cuenta;j++)
			{
				// Extraemos cada objeto
				JSONObject obi= foo.getJSONObject(j);
				// Tomamos los parámetros de la mesa
				String code=obi.getString("number");
				String busy=obi.getString("busy");
				// Evaluamos si la mesa está ocupada
				boolean bus=false;
				if(busy.equals("1"))
					bus=true;
				// Creamos la mesa
				Table t=new Table(Integer.parseInt(code),bus);
				// Abrimos la instancia del almacen de mesas
				TableWrapper tab=TableWrapper.getInstance();
				// Añadimos al almacen
				tab.getTables().add(t);
			}


		} catch (JSONException e) {		    
			Log.e("Menu", "se totio", e);
		}
	}

	/**
	 * Conversión de InputStream a String, tomado de Panoramio
	 * @param is el InputStream a convertir
	 * @return el String convertido
	 */
	private String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8*1024);
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
}