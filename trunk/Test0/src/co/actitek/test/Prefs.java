package co.actitek.test;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

public class Prefs extends PreferenceActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d( "daniel", "onCreate \t" + this.getClass( ));
        addPreferencesFromResource( R.xml.settings );
    }
}
