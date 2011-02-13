package com.activetek.activemenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Gallery1 extends Activity implements OnClickListener{

	private int currentIndex;
	private int itemsInGallery;
	private Gallery gallery;
	private Button btnPrev;
	private Button btnNext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_gal);
		btnPrev=(Button)findViewById(R.id.btnPrev);
		btnNext=(Button)findViewById(R.id.btnNext);
		btnPrev.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		gallery=(Gallery)findViewById(R.id.gallery);
		itemsInGallery=CategoryWrapper.getInstance().getCategories().size();
		gallery.setAdapter(new GalleryAdapter(this));
		gallery.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int index = gallery.getSelectedItemPosition();  
				Toast t= Toast.makeText(getBaseContext(), "Has Seleccionado "+CategoryWrapper.getInstance().getCategories().get(index).getName(), Toast.LENGTH_SHORT);
				t.show();
				Intent in= new Intent(Gallery1.this,Gallery2.class);
				in.putExtra("cat", index);
				Gallery1.this.startActivityForResult(in, 1);
				
			}
		});

	}


	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.btnNext:
			//Increase the index
			currentIndex++;
			//if reached the end of the gallery, then start from the first item
			if(currentIndex>itemsInGallery-1)
				currentIndex=0;
			gallery.setSelection(currentIndex,true);
			break;
		case R.id.btnPrev:
			//Decrease the index
			currentIndex=currentIndex-1;
			//If reached the first item, then return to the last item in the gallery
			if(currentIndex<0)
				currentIndex=itemsInGallery-1;
			gallery.setSelection(currentIndex,true);
			break;
		}
	}

	private class GalleryAdapter extends BaseAdapter{
		private int mGalleryItemBackground;
	    private Context mContext;
	    private CategoryWrapper wrap;

	    public GalleryAdapter(Context c) {
	        mContext = c;
	        TypedArray a = c.obtainStyledAttributes(R.styleable.HelloGallery);
	        mGalleryItemBackground = a.getResourceId(
	                R.styleable.HelloGallery_android_galleryItemBackground, 0);
	        a.recycle();
	        wrap=CategoryWrapper.getInstance();
	    }

	    public int getCount() {
	        return wrap.getCategories().size();
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
	    	View layout = inflater.inflate(R.layout.gallery_item, null);TextView text=(TextView) layout.findViewById(R.id.categ);
	    	ImageView i = (ImageView)layout.findViewById(R.id.imag);
	    	text.setText(wrap.getCategories().get(position).getName());
	    	text.setGravity(Gravity.CENTER);
	    	text.setTextColor(Color.GRAY);
	    	i.setImageResource(R.drawable.food_logo);
	        i.setScaleType(ImageView.ScaleType.FIT_XY);
	        layout.setBackgroundResource(mGalleryItemBackground);
	        layout.setLayoutParams(new Gallery.LayoutParams(300, 220));
	        
	        return layout;
	    }
		
	}
}
