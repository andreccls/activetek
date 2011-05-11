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
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

/**
 * Actividad de selección del MenuItem, según variedades
 * @author juan
 *
 */
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
		
		final int cat=extras.getInt("cat");
		final int food= extras.getInt("food");
		final int id= extras.getInt("id");
		final MenuItem it= CategoryWrapper.getInstance().getCategories().get(cat).getFoods().get(food);
		for(int i=0;i<it.getPrices().size();i++)
		{
			// Creamos varios RadioButtons para seleccionar las variedades
			// de cada menuitem
			RadioButton rad=new RadioButton(this);
			// Creamos cada RadioButton con la cantidad de unidades, precio y descripción
			rad.setText(it.getPrices().get(i).getUnits()+" Unidades, Precio: "+it.getPrices().get(i).getCost()+"\nDetalles: "+it.getPrices().get(i).getDescription());
			rad.setTextSize(16);
			rad.setTextColor(Color.BLACK);
			final int k=i;
			// Creamos el Listener para cuando se haga click en cualquier radiobutton
			rad.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// Añadimos la selección del usuario a la Lista Inteligente
					SmartList.getInstance().add(id+":"+CategoryWrapper.getInstance().getCategories().get(cat).getFoods().get(food).getPrices().get(k).getId());
					// Enviamos un mensaje al servidor con la selección del usuario
					Sender.getInstance().getWrite().println("ADD:"+id+":"+CategoryWrapper.getInstance().getCategories().get(cat).getFoods().get(food).getPrices().get(k).getId());
					// Fijamos el código de resultado
					setResult(1);
					// Terminamos la Actividad
					finish();
				}
			});
			// Añadimos el radioButton
			lay.addView(rad);
		}
		//Creación del adaptador sencillo
		gal.setAdapter(new ImageAdapter(this,it));
		// Botón de retroceso al menu anterior
		Button backButton=new Button(this);
		backButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				setResult(0);
				finish();
			}
			
		});
		backButton.setText("Volver");
		lay.addView(backButton);
	}
	
	/**
	 * Adapatador sencillo
	 * @author juan
	 * Utiliza un menuItem para construir las diferentes imágenes de la galería
	 */
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

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
}
