package co.actitek.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        View continueButton = findViewById( R.id.continue_button );
        continueButton.setOnClickListener( this );

        View newButton = findViewById( R.id.new_button );
        newButton.setOnClickListener( this );

        View aboutButton = findViewById( R.id.about_button );
        aboutButton.setOnClickListener( this );

        View exitButton = findViewById( R.id.exit_button );
        exitButton.setOnClickListener( this );

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
        super.onDestroy( );
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
        switch( v.getId( ) )
        {
            case R.id.about_button:
                Intent i = new Intent( this, About.class );
                startActivity( i );
                break;
            case R.id.new_button:                
                OpenNewGameDialog( );
                break;
            case R.id.exit_button:
                finish( );
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        super.onCreateOptionsMenu( menu );
        Log.d( "daniel", "onCreateOptionsMenu \t" + this.getClass( ) );
        MenuInflater inFlater = getMenuInflater( );
        inFlater.inflate( R.menu.menu, menu );
        return true;
    }
    public boolean onOptionsItemSelected( MenuItem item )
    {
        switch( item.getItemId( ) )
        {
            case R.id.settings:
                startActivity( new Intent( this, Prefs.class ) );
                break;
            default:
                break;
        }
        return false;
    }
    private void OpenNewGameDialog( )
    {
        new AlertDialog.Builder( this ).setTitle( R.string.new_game_title ).setItems( R.array.difficulty, new DialogInterface.OnClickListener( )
        {

            @Override
            public void onClick( DialogInterface dialog, int which )
            {
                startGame( which );
            }
        } ).show( );

    }
    private void startGame( int which )
    {
        Log.d( "daniel", "clicked on " + which );
    }
}