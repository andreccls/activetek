package com.activetek.activemenu;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

/**
 * Actividad Principal del Menú
 * @author juan
 *
 */
public class MenuActivity extends AbstractActivityGroup{

	/**
	 * Instancia de la lista inteligente
	 */
	private SmartList smartList1;
	/**
	 * Instancia de envío de mensajes
	 */
	private Sender send;

	/**
	 * Instancia de recepción de mensajes
	 */
	private Receiver rec;

	/**
	 * Identificaciòn de usuario
	 */
	int id;

	/**
	 * Conteo de Usuarios 
	 */
	int count;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		LinearLayout listLayout=(LinearLayout) findViewById(R.id.listsmart);
		// Creamos la vista de la lista de pedido
		ListView list=new ListView(this);

		smartList1 = SmartList.getInstance();
		//La cuenta de usuarios corresponde siempre al id de usuario
		// mas uno
		count=getIntent().getExtras().getInt("SLI")+1;
		// Obtenemos el id de usuario de la actividad anterior
		id=getIntent().getExtras().getInt("SLI");
		send=Sender.getInstance();
		rec=Receiver.getInstance();
		// Indicamos al receptor que esta actividad manejará los mensajes del servidor
		rec.setOwner(this);

		// Creamos un adaptador para lista inteligente
		SmartListAdapter smartAdapter = new SmartListAdapter(this,id);		
		// Añadimos el tìtulo de la lista
		TextView title=new TextView(this);
		title.setBackgroundColor(Color.TRANSPARENT);
		title.setText("Su Pedido");
		title.setTextSize(22);
		title.setTypeface(null,Typeface.BOLD);
		title.setTextColor(Color.BLACK);
		title.setGravity(Gravity.CENTER);
		// Disponemos el título como encabezado de la lista
		list.addHeaderView(title);
		list.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		list.setCacheColorHint(0);
		list.setAdapter(smartAdapter);
		listLayout.addView(list);

		// añademos el botòn de ordenar pedido
		Button orderButton=new Button(this);
		orderButton.setText("Ordenar Pedido");
		orderButton.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT,6));
		orderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Creamos un mensaje con el contenido del smartlist
				Toast.makeText(getBaseContext(), 
						"Tu orden es : " + smartList1.toString(), 
						Toast.LENGTH_SHORT).show(); 
				// enviamos un mensaje al servidor indicando el fin del pedido
				send.getWrite().println("end:"+id);
				if(id==0)
				{
					// Preparar la siguiente actividad
					Intent in= new Intent(MenuActivity.this, SelectionsActivity.class);
					in.putExtra("count", count);
					// Iniciar actividad y ceder el runtime a ella
					MenuActivity.this.startActivityForResult(in,1);
				}
			}

		});
		listLayout.addView(orderButton);

		// Creamos la Primera Galería sobre el mismo Layout
		LinearLayout lay=(LinearLayout)findViewById(R.id.galleryspace);
		Intent in= new Intent().setClass(this, Gallery1.class);
		in.putExtra("id", id);
		LocalActivityManager m = getLocalActivityManager();
		Window w = m.startActivity("tratat", in);
		View v = w.getDecorView();
		lay.addView(v);
	}

	/**
	 * Este método verifica el resultado de las actividades generadas
	 */
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode==1)
        {
        	rec.setOwner(this);
        }
	
	}
	
	

	/**
	 * Adaptador de Lista inteligente
	 * @author juan
	 *
	 */
	private class SmartListAdapter extends BaseAdapter implements Observer {
		private Context mContext;
		private SmartList smartList2;
		private int id;

		public SmartListAdapter(Context context, int mid) {
			mContext = context;            		
			smartList2 = SmartList.getInstance();
			smartList2.addObserver(this);
			id=mid;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView == null) {
				// Make up a new view
				LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.lista_item, null);
			} else {
				// Use convertView if it is available
				view = convertView;
			}
			TextView text=(TextView) view.findViewById(R.id.title);
			// Vamos a utilizar exclusivamente parte de la lista inteligente
			final ArrayList<String> arr=subList(id);
			// Extraemos la categoría seleccionada
			String sub=arr.get(position).substring(arr.get(position).indexOf(":")+1);
			int cat=Integer.parseInt(sub.substring(0,sub.indexOf(":")));
			// Extraemos la Comida seleccionada
			String subsub=sub.substring(sub.indexOf(":")+1);
			int food=Integer.parseInt(subsub.substring(0,subsub.indexOf(":")));
			// Extraemos la Variedad seleccionada
			String subsubsub=subsub.substring(sub.indexOf(":")+1);
			int variety=Integer.parseInt(subsubsub.substring(0));
			// Obtenemos el menuitem que seleccionamos
			MenuItem men=CategoryWrapper.getInstance().getCategories().get(cat).getFoods().get(food);
			// Escribimos en el listview el nombre del menuitem seleccionado, y la descripciòn de la variedad
			text.setText(men.getName()+" , "+men.getPrices().get(variety).getDescription());
			text.setGravity(Gravity.CENTER);
			Button elim=(Button) view.findViewById(R.id.eliminator);
			elim.setTypeface(Typeface.SERIF,Typeface.BOLD);
			// Creamos un Botón para borrar el elemento
			elim.setText("-");
			elim.setBackgroundColor(Color.TRANSPARENT);
			elim.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// Envio la remociòn al servidor
					Sender.getInstance().getWrite().println("REMOVE:"+arr.get(position));
					// Elimino la entrada de la lista inteligente
					smartList2.remove(arr.get(position));
				}

			});

			return view;
		}        

		/**
		 * Este método retorna un arraylist con los pedidos locales
		 * @param id identificaciòn del usuario
		 * @return arraylist con los pedidos de este usuario
		 */
		public ArrayList<String> subList(int id)
		{
			ArrayList<String> temp=new ArrayList<String>();
			for(int i=0;i<smartList2.size();i++)
			{
				String item=smartList2.get(i);
				int itid=Integer.parseInt(item.substring(0,item.indexOf(":")));
				if(itid==id)
					// Solo añado algo si el id del item en la lista es igual al id de usuario
					temp.add(item);
			}
			return temp;
		}

		public int getCount() {
			return subList(id).size();
		}

		public Object getItem(int position) {
			return smartList2.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		/**
		 *  Actualizaciòn de la lista observable (Inteligente)
		 */
		@Override
		public void update(Observable arg0, Object arg1) {
			smartList2 = SmartList.getInstance();
			notifyDataSetChanged();
			Log.v("SmartListAdapter", "Count is :"+smartList2.size());
		}
	}



	/**
	 * Manejo de mensajes del servidor
	 */
	@Override
	public void notifier(String message) {
		if(message.equals("arrive"))
			//Si hay un arribo a la mesa, incremento el numero de usuarios
			count++;
		else
		{
			// Revisamos que comando nos envían
			int sep=message.indexOf(":");
			String cmd=message.substring(0,sep);
			String chain=message.substring(sep +1);
			if(cmd.equals("ADD"))
			{
				// Buscamos el ID del mensaje
				int mid=Integer.parseInt(chain.substring(0,chain.indexOf(":")));
				if(mid!=id)
					// Si el mensaje es de este usuario, no debe añadirlo a la lista inteligente
					SmartList.getInstance().add(chain);
			}
			if(cmd.equals("REMOVE"))
			{
				int mid=Integer.parseInt(chain.substring(0,chain.indexOf(":")));
				if(mid!=id)
					SmartList.getInstance().remove(chain);
			}
		}
	}

}
