package com.activetek.activemenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ExtrasActivity extends AbstractActivityGroup{

	private TextView label;
	private Receiver rec;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.extras);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		label=(TextView) findViewById(R.id.message);
		GridView grid=(GridView) findViewById(R.id.grid);
		label.setText("Su pedido está siendo preparado");
		grid.setAdapter(new ImageAdapter(this));
		grid.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id)
			{
				Intent in;
				switch(position)
				{
				case 0:
					/**
					in= new Intent(ExtrasActivity.this,MenuActivity.class);
					ExtrasActivity.this.startActivity(in);
					finish();
					*/
					break;
				case 1:
					/**
					in= new Intent(ExtrasActivity.this,MenuActivity.class);
					ExtrasActivity.this.startActivity(in);
					finish();
					*/
					break;
				case 2:
					in= new Intent(ExtrasActivity.this,WebActivity.class);
					in.putExtra("URL", "http://www.eltiempo.com");
					ExtrasActivity.this.startActivity(in);
					finish();
					break;
				case 3:
					in= new Intent(ExtrasActivity.this,WebActivity.class);
					in.putExtra("URL", "http://www.activetek.co");
					ExtrasActivity.this.startActivity(in);
					finish();
					break;
				default:
				}
			}
		});
		rec=Receiver.getInstance();
		rec.setOwner(this);
	}

	@Override
	public void onBackPressed() {

		return;
	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;
		public final String[] wrap={"Raging Thunder","Cake Mania","El Tiempo","ActiveTek"};

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return wrap.length;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView == null) {
				// Make up a new view
				LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.grid_item, null);
			} else {
				// Use convertView if it is available
				view = convertView;
			}
			TextView text= (TextView)view.findViewById(R.id.exdesc);
			text.setText(wrap[position]);
			ImageView imag= (ImageView)view.findViewById(R.id.eximag);
			imag.setImageResource(R.drawable.icon2);
			return view;
		}

	}

	@Override
	public void notifier(String message) {
		// TODO Auto-generated method stub
		if(message.equals("SERVED"))
			label.setText("Su Orden ya está Lista");
	}
}
