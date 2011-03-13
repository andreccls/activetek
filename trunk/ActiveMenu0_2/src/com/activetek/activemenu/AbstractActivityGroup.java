package com.activetek.activemenu;

import android.app.ActivityGroup;

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

}
