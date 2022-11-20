package com.aidand.musicmap.ui.settings;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aidand.musicmap.R;

public class AppSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new AppSettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // show back button
            actionBar.setDisplayHomeAsUpEnabled(true);
            // set the title of this activity
            actionBar.setTitle(R.string.title_settings);
        }
        // adds text to the seek bar
        // addTextToSeek(R.id.sensitivity_seek_bar, R.id.sensitivity_text_value);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void addTextToSeek(int seekBarId, int seekTextId){
        SeekBar sk = (SeekBar) findViewById(seekBarId);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView t=(TextView)findViewById(seekTextId);
                t.setText(String.valueOf(i/10f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
