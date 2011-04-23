package com.activetek.activemenu;

import android.app.ActivityGroup;
import android.app.ProgressDialog;
import android.os.Handler;

/**
 * Clase abstracta de donde se implementan algunas actividades
 * del programa
 * @author juan
 *
 */

public abstract class AbstractActivityGroup extends ActivityGroup{

	/**
	 * Método que realiza acciones de acuerdo con el mensaje
	 * que envía el servidor
	 * @param message Mensaje enviado por el servidor
	 */
	public abstract void notifier(String message);
	
	public void link()
	{
		
	}
	Handler toastHandler = new Handler();
	ProgressDialog d;
	Runnable toastRunnable = new Runnable() {
		public void run()
		{
			//Toast.makeText(AbstractActivityGroup.this, "bbbbb",Toast.LENGTH_LONG).show();
			d= new ProgressDialog(AbstractActivityGroup.this);
			d.setMessage("Se ha perdido la Conexion con el servidor, por favor espere...");
			d.show();
			//Toast.makeText(AbstractActivityGroup.this, "aaaaa",Toast.LENGTH_LONG).show();
		}
	};
	Runnable toastRunnable2 = new Runnable() {
		public void run()
		{
			//Toast.makeText(AbstractActivityGroup.this, "bbbbb",Toast.LENGTH_LONG).show();
			d.dismiss();
			//Toast.makeText(AbstractActivityGroup.this, "aaaaa",Toast.LENGTH_LONG).show();
		}
	};

}
