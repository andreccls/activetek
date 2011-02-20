package com.activetek.activemenu;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class lastActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.last);
		Gallery gal=(Gallery)findViewById(R.id.imagenes);
		LinearLayout lay=(LinearLayout)findViewById(R.id.options);
		Bundle extras=getIntent().getExtras();
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		int cat=extras.getInt("cat");
		int food= extras.getInt("food");
		final MenuItem it= CategoryWrapper.getInstance().getCategories().get(cat).getFoods().get(food);
		for(int i=0;i<it.getPrices().size();i++)
		{
			RadioButton rad=new RadioButton(this);
			rad.setText(it.getPrices().get(i).getUnits()+" Unidades, "+it.getPrices().get(i).getCost());
			rad.setTextColor(Color.BLACK);
			rad.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					SmartList.getInstance().add(it);
					setResult(1);
					finish();
				}
			});
			lay.addView(rad);
		}
		gal.setAdapter(new ImageAdapter(this,it));
	}
	
	public class ImageAdapter extends BaseAdapter {
	    int mGalleryItemBackground;
	    private Context mContext;
	    private MenuItem it;


	    public ImageAdapter(Context c, MenuItem ita) {
	        mContext = c;
	        TypedArray a = c.obtainStyledAttributes(R.styleable.HelloGallery);
			mGalleryItemBackground = a.getResourceId(
					R.styleable.HelloGallery_android_galleryItemBackground, 0);
			a.recycle();
			it=ita;
	    }

	    public int getCount() {
	        return it.getImages().size();
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView i = new ImageView(mContext);

	        i.setImageBitmap(it.getImages().get(position));
	        i.setLayoutParams(new Gallery.LayoutParams(200, 220));
	        i.setScaleType(ImageView.ScaleType.FIT_XY);
	        i.setBackgroundResource(mGalleryItemBackground);

	        return i;
	    }
	}

}
