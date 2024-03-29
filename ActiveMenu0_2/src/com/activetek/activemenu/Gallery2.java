package com.activetek.activemenu;


import java.util.ArrayList;

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
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Actividad de la segunda galería, de MenuItems
 * @author juan
 *
 */
public class Gallery2 extends Activity implements OnClickListener{

	/**
	 * Numero de la categorìa desplegada
	 */
	private int code;
	/**
	 *  Indice actual de la galería
	 */
	private int currentIndex;
	/**
	 * Cantidad de Items en la galería
	 */
	private int itemsInGallery;
	private Gallery gallery;
	private Button btnPrev;
	private Button btnNext;
	private Button btnBack;
	/**
	 * ArrayList de MenuItems a Mostrar
	 */
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
		// Queremos obtener mensajes extras que nos dejó la actividad
		// que realizó la invocación de esta actividad
		Bundle extras=getIntent().getExtras();
		// Inicializamos el código de categoría
		code=0;
		if (extras!=null)
			//Obtenemos el código de la actividad anterior
			code=extras.getInt("cat");
		// Obtenemos el array de menuitems con el código de categoría
		items=CategoryWrapper.getInstance().getCategories().get(code).getFoods();
		itemsInGallery=items.size();
		// El título de la galería es el nombre de la categoría
		txt.setText(CategoryWrapper.getInstance().getCategories().get(code).getName());
		// Creamos un nuevo adaptador
		gallery.setAdapter(new Gallery2Adapter(this,items));
		gallery.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// Preparamos la siguiente Actividad
				Intent in= new Intent(Gallery2.this,lastActivity.class);
				// Pasamos los parámetros relevantes a la siguiente actividad
				in.putExtra("cat", code);
				in.putExtra("food", arg2);
				in.putExtra("id", Gallery2.this.getIntent().getExtras().getInt("id"));
				// Iniciamos la Actividad
				Gallery2.this.startActivityForResult(in, 1);
				
			}
		});

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
        	// Si el resultado es 1, el usuario seleccionó un Item
        	//Volvamos al menu principal
        	setResult(1);
        	finish();
        }
	
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

	public void setCode(int code) {
		this.code = code;
	}


	public int getCode() {
		return code;
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
			ImageView i = (ImageView)layout.findViewById(R.id.imag2);
			text.setText(wrap.get(position).getName());
			text.setGravity(Gravity.CENTER);
			text.setTextColor(Color.GRAY);
			text.setTextSize(14);
			// Introducimos la descripción del menuitem
			desc.setText("Descripción: \n"+wrap.get(position).getDescription());
			desc.setGravity(Gravity.CENTER);
			desc.setTextColor(Color.GRAY);
			desc.setTextSize(14);
			i.setImageBitmap(wrap.get(position).getImages().get(0));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			layout.setBackgroundResource(mGalleryItemBackground);
			layout.setLayoutParams(new Gallery.LayoutParams(500, 275));

			return layout;
		}

	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
}
