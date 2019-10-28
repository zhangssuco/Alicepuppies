package com.example.alicepuppies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

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

        setsp();


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.puppy_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.setsp:
                setsp();
                return true;
            case R.id.getsp:
                getsp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void getsp()
    {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

        Date myDate = new Date(prefs.getLong("time", 0));
        Toast toast = Toast.makeText(getApplicationContext(),
                myDate.toString(),
                Toast.LENGTH_LONG);
        toast.show();

    }
    void setsp()
    {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = prefs.edit();

        //getting the current time in milliseconds, and creating a Date object from it:
        Date date = new Date(System.currentTimeMillis()); //or simply new Date();

        //converting it back to a milliseconds representation:
        long millis = date.getTime();
        prefs.edit().putLong("time", date.getTime()).apply();


        editor.commit(); // commit changes

    }

    void readremotetextfile()
    {
        String whole="Text is: ";

        try {
            // Create a URL for the desired page
            URL url = new URL("http://www.gutenberg.org/cache/epub/14606/pg14606.txt");
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;

            while ((str = in.readLine()) != null) {
                // str is one line of text; readLine() strips the newline character(s)
                whole+=str+"\n";
            }
            in.close();
        } catch (MalformedURLException e) {
            Log.d("readtext","malformedurl");
        } catch (IOException e) {
        }

        Toast toast = Toast.makeText(getApplicationContext(),
                whole,
                Toast.LENGTH_LONG);
        toast.show();



    }

}