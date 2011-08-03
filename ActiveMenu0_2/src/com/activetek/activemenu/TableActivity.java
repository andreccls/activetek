package com.activetek.activemenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

public class TableActivity extends AbstractActivityGroup
{

	private Sender send;
	private Receiver rec;
	private int slaveIndex;

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.tables );

		this.getWindow( ).setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		// /**
		// try {
			// sock=new Socket("192.168.0.102",9999);
		// read= new BufferedReader(new InputStreamReader(sock.getInputStream()));
		// write= new PrintWriter(sock.getOutputStream(),true);
		// } catch (UnknownHostException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// */
		slaveIndex = 0;
		GridView grid = ( GridView )findViewById( R.id.gridmesa );
		grid.setAdapter( new TextAdapter( this ) );
		grid.setOnItemClickListener( new OnItemClickListener( )
		{
			Toast toast = Toast.makeText( getBaseContext( ), "aa", Toast.LENGTH_SHORT );
			public void onItemClick( AdapterView<?> parent, View v, int position, long id )
			{
				// /**
				// write.println("MESA:"+TableWrapper.getInstance().getTables().get(position).getNumber());
				// String beta="";
				// try {
				// beta= read.readLine();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// */
				// System.out.println(beta);
				toast.setDuration( Toast.LENGTH_SHORT );
				String alfa = "Libre";
				if( TableWrapper.getInstance( ).getTables( ).get( position ).isBusy( ) )
					alfa = "Ocupada";
				String text = "La mesa " + TableWrapper.getInstance( ).getTables( ).get( position ).getNumber( ) + " está " + alfa;
				toast.setText( text );
				toast.show( );
				if( TableWrapper.getInstance( ).getTables( ).get( position ).isBusy( ) )
				{
					// prepare the alert box
					AlertDialog.Builder alertbox = new AlertDialog.Builder( TableActivity.this );

					// set the message to display
					alertbox.setMessage( "La Mesa está Ocupada, ¿Desea Continuar?" );
					final int index = position;
					// add a neutral button to the alert box and assign a click listener
					alertbox.setPositiveButton( "Continuar", new DialogInterface.OnClickListener( )
					{

						// click listener on the alert box
						public void onClick( DialogInterface arg0, int arg1 )
						{
							// the button was clicked
							send.getWrite( ).println( "MESA:" + TableWrapper.getInstance( ).getTables( ).get( index ).getNumber( ) );
							Toast.makeText( getApplicationContext( ), "Haz seleccionado la mesa " + TableWrapper.getInstance( ).getTables( ).get( index ).getNumber( ), Toast.LENGTH_LONG ).show( );
							Intent in = new Intent( TableActivity.this, WaiterActivity.class );
							in.putExtra( "SLI", slaveIndex );
							TableActivity.this.startActivityForResult( in, 1 );
							finish( );
						}
					} );
					alertbox.setNegativeButton( "Seleccionar Otra Mesa", new DialogInterface.OnClickListener( )
					{

						// click listener on the alert box
						public void onClick( DialogInterface arg0, int arg1 )
						{
							// the button was clicked
							Toast.makeText( getApplicationContext( ), "Selecciona Otra mesa por favor", Toast.LENGTH_LONG ).show( );
						}
					} );

					// show it
					alertbox.show( );
				}
				else
				{

					// if(beta.equals("MAESTRO"))
					// {
					// Intent in=new Intent(TableActivity.this,WaiterActivity.class);
					// TableActivity.this.startActivityForResult(in,1);
					// }
					// else
					// {
					// Intent in=new Intent(TableActivity.this,MenuActivity.class);
					// TableActivity.this.startActivityForResult(in,1);
					//
					// }

					send.getWrite( ).println( "MESA:" + TableWrapper.getInstance( ).getTables( ).get( position ).getNumber( ) );
					Toast.makeText( getApplicationContext( ), "Haz seleccionado la mesa " + TableWrapper.getInstance( ).getTables( ).get( position ).getNumber( ), Toast.LENGTH_LONG ).show( );
					Toast.makeText( getApplicationContext( ), "Haz seleccionado la mesa " + TableWrapper.getInstance( ).getTables( ).get( position ).getNumber( ), Toast.LENGTH_LONG ).show( );
					Intent in= new Intent( TableActivity.this, WaiterActivity.class );
					Intent ini = new Intent( TableActivity.this, MenuActivity.class );
					
					if(slaveIndex==0)
					{
						in.putExtra( "SLI", slaveIndex );
						TableActivity.this.startActivityForResult( in, 1 );
					}
					else
					{
						ini.putExtra( "SLI", slaveIndex );
						TableActivity.this.startActivityForResult( ini, 1 );
					}
					finish( );
				}
			}
		} );
		rec = Receiver.getInstance( );
		rec.setOwner( this );
		send = Sender.getInstance( );
	}

	@Override
	public void onBackPressed( )
	{

		return;
	}

	public class TextAdapter extends BaseAdapter
	{
		private Context mContext;
		private TableWrapper wrap;
		private int mGalleryItemBackground;
		public TextAdapter( Context c )
		{
			mContext = c;
			wrap = TableWrapper.getInstance( );
			TypedArray a = c.obtainStyledAttributes( R.styleable.HelloGallery );
			mGalleryItemBackground = a.getResourceId( R.styleable.HelloGallery_android_galleryItemBackground, 0 );
			a.recycle( );
		}

		public int getCount( )
		{
			return wrap.getTables( ).size( );
		}

		public Object getItem( int position )
		{
			return null;
		}

		public long getItemId( int position )
		{
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		public View getView( int position, View convertView, ViewGroup parent )
		{
			TextView textView;
			if( convertView == null )
			{ // if it's not recycled, initialize some attributes
				textView = new TextView( mContext );
				textView.setLayoutParams( new GridView.LayoutParams( 85, 85 ) );
				textView.setPadding( 8, 8, 8, 8 );
			}
			else
			{
				textView = ( TextView )convertView;
			}

			textView.setBackgroundResource( mGalleryItemBackground );
			textView.setText( "" + wrap.getTables( ).get( position ).getNumber( ) );
			if( wrap.getTables( ).get( position ).isBusy( ) )
				textView.setBackgroundColor( Color.RED );
			else
				textView.setBackgroundColor( Color.GREEN );
			textView.setGravity( Gravity.CENTER );
			textView.setTextColor( Color.BLACK );
			return textView;
		}

	}

	@Override
	public void notifier( String message )
	{
		// TODO Auto-generated method stub
		Log.d( "TableActivity", message );
		if( !message.equals( "MAESTRO:" ) )
		{
			slaveIndex = Integer.parseInt(message.split(":")[1]) ;
			Log.d("Linea", message);
		}
	}
	@Override
	public void onDestroy( )
	{
		super.onDestroy( );
	}

	@Override
	public int count( )
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
