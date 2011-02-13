package com.activetek.activemenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WaiterActivity extends Activity{
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
				toast.setText(text);
				toast.show();
				Intent in= new Intent(WaiterActivity.this,TableActivity.class);
				WaiterActivity.this.startActivity(in);
			}
		});
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
			if (convertView == null) {  // if it's not recycled, initialize some attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(10, 10, 10, 10);
			} else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageBitmap(wrap.getWaiters().get(position).getImage());
			return imageView;
		}

	}
}
