package com.activetek.activemenu;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WaiterActivity extends AbstractActivityGroup{

	private Sender send;
	private Receiver rec;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiter);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		GridView grid= (GridView) findViewById(R.id.gridview);
		grid.setAdapter(new ImageAdapter(this));
		grid.setOnItemClickListener(new OnItemClickListener()
		{
			Toast toast= Toast.makeText(getBaseContext(), "aa", Toast.LENGTH_SHORT);
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id)
			{
				toast.setDuration(Toast.LENGTH_SHORT);

				String text = WaiterWrapper.getInstance().getWaiters().get(position).getName()+" "+WaiterWrapper.getInstance().getWaiters().get(position).getCode()+" ";
				send.getWrite().println("MESERO:"+WaiterWrapper.getInstance().getWaiters().get(position).getCode());
				toast.setText(text);
				toast.show();
				Intent in= new Intent(WaiterActivity.this,MenuActivity.class);
				in.putExtra("SLI", WaiterActivity.this.getIntent().getExtras().getInt("SLI"));
				WaiterActivity.this.startActivity(in);
				finish();
			}
		});
		rec=Receiver.getInstance();
		rec.setOwner(this);
		send=Sender.getInstance();
	}



	@Override
	public void onBackPressed() {

		return;
	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;
		private WaiterWrapper wrap;

		public ImageAdapter(Context c) {
			mContext = c;
			wrap=WaiterWrapper.getInstance();
		}

		public int getCount() {
			return wrap.getWaiters().size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			TextView t;
			LinearLayout l;
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(10, 10, 10, 10);
			TypedArray a = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
			int mGalleryItemBackground = a.getResourceId(
					R.styleable.HelloGallery_android_galleryItemBackground, 0);
			imageView.setBackgroundResource(mGalleryItemBackground);

			imageView.setImageBitmap(wrap.getWaiters().get(position).getImage());
			t=new TextView(mContext);
			t.setText(wrap.getWaiters().get(position).getName());
			t.setTextColor(Color.BLACK);
			t.setGravity(Gravity.CENTER);
			l=new LinearLayout(mContext);
			l.setOrientation(LinearLayout.VERTICAL);
			l.addView(imageView);
			l.addView(t);
			return l;
		}

	}

	@Override
	public void notifier(String message) {
		Log.d("WaiterActivity", message);
		//La actividad de meseros No debe recibir mensajes, si recibe, no hacemos nada con ellos
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}



	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
