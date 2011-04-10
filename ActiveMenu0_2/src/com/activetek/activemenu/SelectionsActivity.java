package com.activetek.activemenu;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;


public class SelectionsActivity extends AbstractActivityGroup {

	/**
	 * Instancia de envío de mensajes
	 */
	private Sender send;

	/**
	 * Instancia de recepción de mensajes
	 */
	private Receiver rec;

	private boolean ready;

	/**
	 * Conteo de Usuarios 
	 */
	private int count;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selections);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//La cuenta de usuarios corresponde siempre al id de usuario
		// mas uno
		count=getIntent().getExtras().getInt("count");
		// Obtenemos el id de usuario de la actividad anterior
		send=Sender.getInstance();
		ready=false;
		rec=Receiver.getInstance();
		// Indicamos al receptor que esta actividad manejará los mensajes del servidor
		rec.setOwner(this);

		LinearLayout lay=(LinearLayout)findViewById(R.id.tabcontainer);
		lay.addView(_createTABForm());

		Button confirm=(Button) findViewById(R.id.btnConfirm);
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Creamos un mensaje con el contenido del smartlist
				Toast.makeText(getBaseContext(), 
						"Pedido Confirmado", 
						Toast.LENGTH_SHORT).show(); 
				// enviamos un mensaje al servidor indicando el fin del pedido
				send.getWrite().println("confirm");
				Intent in= new Intent(SelectionsActivity.this, ExtrasActivity.class);
				startActivity(in);
				finish();
			}

		});
		if(ready)
			confirm.setEnabled(false);
		Button cancel=(Button) findViewById(R.id.btnCancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setResult(1);
				finish();
				// enviamos un mensaje al servidor indicando el fin del pedido
				//send.getWrite().println("end:"+id);
			}

		});

	}
	private ViewGroup _createTABForm() {
		// construct the TAB Host
		TabHost tabHost = new TabHost(this);
		tabHost.setLayoutParams(
				new LinearLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		// the tabhost needs a tabwidget, that is a container for the visible tabs
		TabWidget tabWidget = new TabWidget(this);
		tabWidget.setId(android.R.id.tabs);
		tabHost.addView(tabWidget, new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT)); 

		// the tabhost needs a frame layout for the views associated with each visible tab
		FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setId(android.R.id.tabcontent);
		frameLayout.setPadding(0, 65, 0, 0);
		tabHost.addView(frameLayout, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)); 

		// setup must be called if you are not initialising the tabhost from XML
		tabHost.setup(); 

		// create the tabs
		for(int i=0; i<count;i++)
		{
			TabSpec ts2 = tabHost.newTabSpec("TAB_TAG_2"+i);
			ts2.setIndicator("Cliente "+(i+1));
			final int k=i;
			ts2.setContent(new TabHost.TabContentFactory(){
				public View createTabContent(String tag)
				{
					// -- this tab contains a single control - the listview -- //
					ListView ls1 = new ListView(SelectionsActivity.this);
					ls1.setAdapter(new SmartListAdapter(SelectionsActivity.this,k));
					return ls1;
				}         
			});       
			tabHost.addTab(ts2);
		}
		// -- set the image for tab3, can be used after tab has been created too -- //
		//ImageView iv = (ImageView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.icon);
		//if (iv != null) iv.setImageDrawable(getResources().getDrawable(R.drawable.icon));

		return tabHost;
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {

		return;
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
				view = inflater.inflate(R.layout.list_item, null);
			} else {
				// Use convertView if it is available
				view = convertView;
			}
			TextView text=(TextView) view.findViewById(R.id.titl);
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


	@Override
	public void notifier(String message) {

		if (message.equals("READY"))
			ready=true;


	}

}
