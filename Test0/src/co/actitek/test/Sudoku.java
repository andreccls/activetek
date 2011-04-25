package co.actitek.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Sudoku extends Activity implements OnClickListener
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        Log.d( "daniel", "onCreate \t" + this.getClass( ) );
        setContentView( R.layout.main );

        View continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this);
        
        View newButton = findViewById(R.id.new_button);
        newButton.setOnClickListener(this);
        
        View aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);

    }
    public void onStart( )
    {
        super.onStart( );
        Log.d( "daniel", "onStart \t" + this.getClass( ) );
    }
    public void onResume( )
    {
        super.onResume( );
        Log.d( "daniel", "onResume \t" + this.getClass( ) );
    }
    public void onStop( )
    {
        super.onStop( );
        Log.d( "daniel", "onResume \t" + this.getClass( ) );
    }
    public void onRestart( )
    {
        super.onRestart( );
        Log.d( "daniel", "onRestart \t" + this.getClass( ) );
    }
    public void onDestroy( )
    {
        super.onResume( );
        Log.d( "daniel", "onDestroy \t" + this.getClass( ) );
    }
    public void onRestoreInstanceState( Bundle savedInstanceState )
    {
        super.onRestoreInstanceState( savedInstanceState );
        Log.d( "daniel", "onRestoreInstanceState \t" + this.getClass( ) );
    }
    @Override
    public void onClick( View v )
    {     
        switch(v.getId( ))
        {
            case R.id.about_button:
                Intent i = new Intent(this, About.class);
                startActivity( i );
                break;
        }
    }
}