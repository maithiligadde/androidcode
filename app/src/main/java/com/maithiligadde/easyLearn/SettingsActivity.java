package com.maithiligadde.easyLearn;



import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.maithiligadde.easyLearn.R;

import java.util.HashMap;


public class SettingsActivity extends ActionBarActivity {

    String[] defaultLanguagePairs = {"English - Espanol", "English - French" ,"English - Portuguese" };
    HashMap<String,String> srcLanguageMap;
    HashMap<String,String> tgtLanguageMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        srcLanguageMap=new HashMap<>();
        tgtLanguageMap=new HashMap<>();
        srcLanguageMap.put("English - Espanol","eses");
        srcLanguageMap.put("English - French","frfr");
        srcLanguageMap.put("English - Portuguese","ptbr");
        tgtLanguageMap.put("English - Espanol","enus");
        tgtLanguageMap.put("English - French","enus");
        tgtLanguageMap.put("English - Portuguese","enus");

        ListView listView = (ListView) findViewById(R.id.settingList);
        listView.setAdapter(new SettingAdapter(this, defaultLanguagePairs));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();  // or // Intent i = getIntent()
                String k=defaultLanguagePairs[position];
                i.putExtra("src",srcLanguageMap.get(k));
                i.putExtra("tgt",tgtLanguageMap.get(k));
                setResult(RESULT_OK , i);
                System.out.println("language "+position +" "  +defaultLanguagePairs[position]);
                finish();
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

    public class SettingAdapter extends ArrayAdapter<String> {

        public SettingAdapter(Context context, String[] objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String setting = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.settings_item, parent, false);
            }

            TextView settingsItemText = (TextView) convertView.findViewById(R.id.settingsItemText);
            settingsItemText.setText(setting);

            return convertView;
        }
    }
}