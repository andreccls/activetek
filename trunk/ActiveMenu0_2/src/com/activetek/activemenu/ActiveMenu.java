package com.activetek.activemenu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActiveMenu extends Activity implements OnClickListener, Runnable {

	private ProgressDialog dialog;
	private Process process;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Button addItem=(Button) findViewById(R.id.startButton);
		addItem.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		if (arg0 ==findViewById(R.id.startButton))
		{
			// prepare the dialog box
			dialog = new ProgressDialog(this);

			// make the progress bar cancelable
			dialog.setCancelable(true);

			// set a <b style="color:black;background-color:#99ff99">message</b> text
			dialog.setMessage("Cargando Información...");

			// show it
			dialog.show();
			Thread t= new Thread(this);
			t.start();
		}

	}

	@Override
	public void run() {
		try {
			process = Runtime.getRuntime().exec("sh");
			OutputStream os = process.getOutputStream();
			writeLine( os,"sleep 5 && exit");
			os.flush();
			process.waitFor();
			CreateContent();
		}
		catch ( IOException e ) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		handler.sendEmptyMessage(0);
	}

	public static void writeLine(OutputStream os, String value) throws IOException
	{
		String line = value + "\n";
		os.write( line.getBytes() );
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dialog.dismiss();
			Intent in= new Intent(ActiveMenu.this, WaiterActivity.class);
			ActiveMenu.this.startActivityForResult(in,1);
		}
	};
	@Override
	public void onBackPressed() {

		return;
	}

	public void CreateContent()
	{
		try {
			FileInputStream fis= new FileInputStream("/data/menu/json.txt");
			String str = convertStreamToString(fis);
			JSONObject json = new JSONObject(str);
			parse(json);
		} catch (Exception e) {
			Log.e("Menu", e.toString());
		}
	}

	public void parse(JSONObject json) {
		try {
			JSONArray foo=json.getJSONArray("categorias");
			int cuenta = foo.length();
			for(int j=0; j < cuenta;j++)
			{
				JSONObject obi= foo.getJSONObject(j);
				String nombre= obi.getString("name");
				JSONArray wow=obi.getJSONArray("menuitem");
				int alfa = wow.length();
				ArrayList<MenuItem> arr=new ArrayList<MenuItem>();
				for (int k=0; k<alfa;k++)
				{
					JSONObject fin=wow.getJSONObject(k);
					String name=fin.getString("name");
					String thumb=fin.getString("image1");
					String prec=fin.getString("price");
					String desc=fin.getString("details");
					arr.add(new MenuItem(name,15,"/data/menu"+thumb,Integer.parseInt(prec.replaceAll("\\D", "")),desc));
				}
				Category aux1= new Category(nombre,arr);
				CategoryWrapper cat=CategoryWrapper.getInstance();
				cat.getCategories().add(aux1);
			}
			foo=json.getJSONArray("meseros");
			cuenta = foo.length();
			for(int j=0; j < cuenta;j++)
			{
				JSONObject obi= foo.getJSONObject(j);
				String name=obi.getString("nick");
				String thumb=obi.getString("image1");
				String prec=obi.getString("id");
				Waiter w= new Waiter(name,Integer.parseInt(prec),"/data/menu"+thumb);
				WaiterWrapper wai= WaiterWrapper.getInstance();
				wai.getWaiters().add(w);
			}
			foo=json.getJSONArray("mesas");
			cuenta = foo.length();
			for(int j=0; j < cuenta;j++)
			{
				JSONObject obi= foo.getJSONObject(j);
				String code=obi.getString("code");
				String busy=obi.getString("busy");
				boolean bus=false;
				if(busy.equals("1"))
					bus=true;
				Table t=new Table(Integer.parseInt(code),bus);
				TableWrapper tab=TableWrapper.getInstance();
				tab.getTables().add(t);
			}


		} catch (JSONException e) {
			Log.e("Menu", e.toString());
		}
	}

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

}