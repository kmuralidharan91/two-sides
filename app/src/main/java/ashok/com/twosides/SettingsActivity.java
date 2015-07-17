package ashok.com.twosides;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;


public class SettingsActivity extends Activity {

    SharedPreferences vibration,music,music_SwitchValue,vibration_SwitchValue;
    public static String appName = "Two-Sides";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        vibration = this.getSharedPreferences("TS",0);
        music = this.getSharedPreferences("TS",0);
        music_SwitchValue = this.getSharedPreferences("TS",0);
        vibration_SwitchValue = this.getSharedPreferences("TS",0);

        Switch sw_Music = (Switch)findViewById(R.id.sw_Music);
        if(music_SwitchValue.getBoolean("SwitchValue", true)){
        sw_Music.setChecked(true);
        }
        sw_Music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    editor = music.edit();
                    editor.putBoolean("Music",true);
                    editor.apply();

                    editor = music_SwitchValue.edit();
                    editor.putBoolean("SwitchValue",true);
                    editor.apply();


                } else {
                    // The toggle is disabled
                    editor = music.edit();
                    editor.putBoolean("Music",false);
                    editor.apply();
                    editor = music_SwitchValue.edit();
                    editor.putBoolean("SwitchValue",false);
                    editor.apply();
                }
            }
        });

        Switch sw_Vibration = (Switch)findViewById(R.id.sw_Vibration);
        if(vibration_SwitchValue.getBoolean("SwitchValue", true)) {
            sw_Vibration.setChecked(true);
        }
        sw_Vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    editor = vibration.edit();
                    editor.putBoolean("Music",true);
                    editor.apply();

                    editor = vibration_SwitchValue.edit();
                    editor.putBoolean("SwitchValue",true);
                    editor.apply();


                } else {
                    // The toggle is disabled
                    editor = vibration.edit();
                    editor.putBoolean("Music",false);
                    editor.apply();

                    editor = vibration_SwitchValue.edit();
                    editor.putBoolean("SwitchValue",false);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
