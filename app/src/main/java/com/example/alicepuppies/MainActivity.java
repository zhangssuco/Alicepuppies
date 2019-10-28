package com.example.alicepuppies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//https://android.jlelse.eu/the-complete-android-splash-screen-guide-c7db82bce565
// use launch theme
public class MainActivity extends ListActivity {

    String everything="nothing yet";

    void loadfromtextfile()
    {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("phonebook.txt"))
            );

            // do reading, usually loop until end of file reading, if comma dilimited, usually split.
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                String[] items = mLine.split(",");
                for(int pos=0; pos<items.length; pos++)
                    everything=everything+"|" + items[pos];

            }
            Log.v("textfile", "file has been processed successfully!");

        } catch (IOException e) {
            //log the exception
            Log.v("textfile", "file can't be opened");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

        loadfromtextfile();

        Toast toast = Toast.makeText(getApplicationContext(),
                everything,
                Toast.LENGTH_LONG);

        toast.show();


        // storing string resources into Array
        String[] puppies = getResources().getStringArray(R.array.puppies);
        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, puppies));
        ListView lv = getListView();

        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
                String product = ((TextView) view).getText().toString();

                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), SingleListItem.class);
                // sending data to new activity
                i.putExtra("product", product);
                startActivity(i);

            }
        });
    }


}