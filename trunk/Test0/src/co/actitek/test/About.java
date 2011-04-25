package co.actitek.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class About extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d( "daniel", "onCreate \t" + this.getClass( ));
        setContentView(R.layout.about);
    }
    public void onStart()
    {
        super.onStart( );
        Log.d( "daniel", "onStart \t" + this.getClass( ));
    }
    public void onResume()
    {
        super.onResume( );
        Log.d( "daniel", "onResume \t" + this.getClass( ));
    }
    public void onStop()
    {
        super.onStop( );
        Log.d( "daniel", "onResume \t" + this.getClass( ));
    }
    public void onRestart()
    {
        super.onRestart( );
        Log.d( "daniel", "onRestart \t"  + this.getClass( ) );
    }
    public void onDestroy()
    {
        super.onResume( );
        Log.d( "daniel", "onDestroy \t" + this.getClass( ) );
    }
    public void onRestoreInstanceState( Bundle savedInstanceState )
    {
        super.onRestoreInstanceState(savedInstanceState );
        Log.d( "daniel", "onRestoreInstanceState " +this.getClass( ) );
    }
}