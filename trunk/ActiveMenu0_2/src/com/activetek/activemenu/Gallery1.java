package com.activetek.activemenu;

import android.app.ActivityGroup;
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

/**
 * Actividad de la primera galería de categorías
 * @author juan
 * Implementa la clase ActivityGroup para crear nuevas actividades
 * Implementa OnClickListener para algunos botones
 */
public class Gallery1 extends ActivityGroup implements OnClickListener{

	/**
	 * Indice Actual de la galería
	 */
	private int currentIndex;
	/**
	 * Conteo de Items en la galería
	 */
	private int itemsInGallery;
	/**
	 * Widget de la galería
	 */
	private Gallery gallery;
	/**
	 * Botón de Retroceso
	 */
	private Button btnPrev;
	/**
	 * Botón de Avance
	 */
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
		//La cantidad de elementos en la galería está dada por el tamaño del
		// almacen de categorías
		itemsInGallery=CategoryWrapper.getInstance().getCategories().size();
		// Creamos un Adaptador de imágenes para el Widget
		gallery.setAdapter(new GalleryAdapter(this));
		// Definimos la acción a ser realizada si se hace click sobre un Item de la Galería
		gallery.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// Obtenemos el indice del objeto seleccionado
				int index = gallery.getSelectedItemPosition();  
				// Creamos un Mensaje con la categoría seleccionada
				Toast t= Toast.makeText(getBaseContext(), "Has Seleccionado "+CategoryWrapper.getInstance().getCategories().get(index).getName(), Toast.LENGTH_SHORT);
				// Mostramos el mensaje
				t.show();
				// Preparamos la siguiente actividad
				Intent in= new Intent(Gallery1.this,Gallery2.class);
				// Le enviamos a la siguiente actividad la Categoría seleccionada
				in.putExtra("cat", index);
				// Le enviamos a la siguiente actividad el ID del usuario
				in.putExtra("id", Gallery1.this.getIntent().getExtras().getInt("id"));
				// Iniciamos la Siguiente Actividad
				Gallery1.this.startActivityForResult(in, 1);
			}
		});

	}

	/**
	 * Este método implementa las acciones a ser realizadas
	 * con los eventos sobre los botones
	 */
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

	/**
	 * Esta clase implementa el adaptador de imágenes
	 * @author juan
	 * Basada en BaseAdapter, Adaptador básico de android
	 */
	private class GalleryAdapter extends BaseAdapter{
		/**
		 * Descriptor del fondo de la galería
		 */
		private int mGalleryItemBackground;
		/**
		 * Contexto (Base Gráfica) de la galería
		 */
	    private Context mContext;
	    /**
	     * Almacen de Categorías
	     */
	    private CategoryWrapper wrap;

	    /**
	     * Constructor del adaptador
	     * @param c Contexto de acción de la galería
	     */
	    public GalleryAdapter(Context c) {
	        mContext = c;
	        // Generador de estilo a partir de xml
	        TypedArray a = c.obtainStyledAttributes(R.styleable.HelloGallery);
	        // Creación del descriptor, enlace simbólico
	        mGalleryItemBackground = a.getResourceId(
	                R.styleable.HelloGallery_android_galleryItemBackground, 0);
	        a.recycle();
	        // Instanciación del almacen
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

	    /**
	     * Este método genera la vista de cada item de la galería
	     */
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	// Primero obtenemos el administrador gráfico que hará el dibujo
	    	LayoutInflater inflater = (LayoutInflater) mContext
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	// Obtenemos el layout para hacer el dibujo, del xml correspondiente
	    	View layout = inflater.inflate(R.layout.gallery_item, null);
	    	TextView text=(TextView) layout.findViewById(R.id.categ);
	    	ImageView i = (ImageView)layout.findViewById(R.id.imag);
	    	// Escribimos el nombre de la categoría
	    	text.setText(wrap.getCategories().get(position).getName());
	    	// Centramos el texto, la gravedad indica la referencia
	    	text.setGravity(Gravity.CENTER);
	    	text.setTextColor(Color.GRAY);
	    	text.setTextSize(20);
	    	// Fijamos la imagen de la categoría, Revisar!!
	    	i.setImageBitmap(wrap.getCategories().get(position).getImage());
	    	// Ajustamos automáticamente el tamaño de la imágen
	        i.setScaleType(ImageView.ScaleType.FIT_XY);
	        // Ajustamos el fondo de galería
	        layout.setBackgroundResource(mGalleryItemBackground);
	        // Definimos el tamaño del item de la galería
	        layout.setLayoutParams(new Gallery.LayoutParams(400, 320));
	        
	        return layout;
	    }
		
	}
	
	
}
