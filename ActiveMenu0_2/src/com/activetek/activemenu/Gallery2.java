package com.activetek.activemenu;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Gallery2 extends Activity implements OnClickListener{


	private int currentIndex;
	private int itemsInGallery;
	private Gallery gallery;
	private Button btnPrev;
	private Button btnNext;
	private Button btnBack;
	private ArrayList<MenuItem> items;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_gal);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		btnPrev=(Button)findViewById(R.id.btnPrev2);
		btnNext=(Button)findViewById(R.id.btnNext2);
		btnBack=(Button)findViewById(R.id.btnBack);
		btnPrev.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		TextView txt=(TextView)findViewById(R.id.Title);
		gallery=(Gallery)findViewById(R.id.gallery2);
		Bundle extras=getIntent().getExtras();
		int code=0;
		if (extras!=null)
			code=extras.getInt("cat");
		items=CategoryWrapper.getInstance().getCategories().get(code).getFoods();
		itemsInGallery=items.size();
		txt.setText(CategoryWrapper.getInstance().getCategories().get(code).getName());
		gallery.setAdapter(new Gallery2Adapter(this,items));
		gallery.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int index = gallery.getSelectedItemPosition();  
				SmartList.getInstance().add(items.get(index));
				setResult(1);
				finish();
			}
		});

	}


	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.btnNext2:
			//Increase the index
			currentIndex++;
			//if reached the end of the gallery, then start from the first item
			if(currentIndex>itemsInGallery-1)
				currentIndex=0;
			gallery.setSelection(currentIndex,true);
			break;
		case R.id.btnPrev2:
			//Decrease the index
			currentIndex=currentIndex-1;
			//If reached the first item, then return to the last item in the gallery
			if(currentIndex<0)
				currentIndex=itemsInGallery-1;
			gallery.setSelection(currentIndex,true);
			break;
		case R.id.btnBack:
			finish();
		}
	}

	private class Gallery2Adapter extends BaseAdapter{
		private int mGalleryItemBackground;
		private Context mContext;
		private ArrayList<MenuItem> wrap;

		public Gallery2Adapter(Context c, ArrayList<MenuItem> list) {
			mContext = c;
			TypedArray a = c.obtainStyledAttributes(R.styleable.HelloGallery);
			mGalleryItemBackground = a.getResourceId(
					R.styleable.HelloGallery_android_galleryItemBackground, 0);
			a.recycle();
			wrap=list;
		}

		public int getCount() {
			return wrap.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) mContext
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.food_item, null);
			TextView text=(TextView) layout.findViewById(R.id.food);
			TextView desc=(TextView) layout.findViewById(R.id.desc);
			TextView prec=(TextView) layout.findViewById(R.id.price);
			ImageView i = (ImageView)layout.findViewById(R.id.imag2);
			text.setText(wrap.get(position).getName());
			text.setGravity(Gravity.CENTER);
			text.setTextColor(Color.GRAY);
			desc.setText("Descripción: \n"+wrap.get(position).getDescription());
			desc.setGravity(Gravity.CENTER);
			desc.setTextColor(Color.GRAY);
			prec.setText("Precio: "+wrap.get(position).getPrice()+"");
			prec.setGravity(Gravity.CENTER);
			prec.setTextColor(Color.GRAY);
			i.setImageBitmap(wrap.get(position).getImage());
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			layout.setBackgroundResource(mGalleryItemBackground);
			layout.setLayoutParams(new Gallery.LayoutParams(300, 220));

			return layout;
		}

	}

}
