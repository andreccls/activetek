package com.activetek.activemenu;

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

public class MenuActivity extends AbstractActivityGroup{

	private SmartList smartList1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		LinearLayout listLayout=(LinearLayout) findViewById(R.id.listsmart);
		ListView list=new ListView(this);

		smartList1 = SmartList.getInstance();


		SmartListAdapter smartAdapter = new SmartListAdapter(this);		
		//list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"item1","item2","item3","item4","item5","item6","item7"}));
		TextView title=new TextView(this);
		title.setBackgroundColor(Color.TRANSPARENT);
		title.setText("Su Pedido");
		title.setTextSize(22);
		title.setTypeface(null,Typeface.BOLD);
		title.setTextColor(Color.BLACK);
		title.setGravity(Gravity.CENTER);
		list.addHeaderView(title);
		list.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
		list.setCacheColorHint(0);
		list.setAdapter(smartAdapter);
		listLayout.addView(list);

		Button orderButton=new Button(this);
		orderButton.setText("Ordenar Pedido");
		orderButton.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.FILL_PARENT,6));
		orderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getBaseContext(), 
						"Tu orden es : " + smartList1.toString(), 
						Toast.LENGTH_SHORT).show(); 
			}

		});
		listLayout.addView(orderButton);

		LinearLayout lay=(LinearLayout)findViewById(R.id.galleryspace);
		Intent in= new Intent().setClass(this, Gallery1.class);
		LocalActivityManager m = getLocalActivityManager();
		Window w = m.startActivity("tratat", in);
		View v = w.getDecorView();
		lay.addView(v);

	}


	

	private class SmartListAdapter extends BaseAdapter implements Observer {
		private Context mContext;
		private SmartList smartList2;

		public SmartListAdapter(Context context) {
			mContext = context;            		
			smartList2 = SmartList.getInstance();
			smartList2.addObserver(this);
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
			text.setText(smartList2.get(position).getName());
			text.setGravity(Gravity.CENTER);
			Button elim=(Button) view.findViewById(R.id.eliminator);
			elim.setTypeface(Typeface.SERIF,Typeface.BOLD);
			elim.setText("-");
			elim.setBackgroundColor(Color.TRANSPARENT);
			elim.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					smartList2.remove(position);
				}

			});
			return view;
		}        

		public int getCount() {
			return smartList2.size();
		}

		public Object getItem(int position) {
			return smartList2.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@Override
		public void update(Observable arg0, Object arg1) {
			smartList2 = SmartList.getInstance();
			notifyDataSetChanged();
			Log.v("SmartListAdapter", "Count is :"+smartList2.size());
		}
	}




	@Override
	public void notifier(String message) {
		// TODO Auto-generated method stub
		
	}

}
