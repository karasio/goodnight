package com.example.goodnight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**Class for <code>Tips</code> from <code>Tipslist</code> presented by their <code>NAME</code> as titles in listView to use only one activity to show one of the three tips in new activity on click.
 *
 * @author Kimmo Perälä
 *
 * @version 1.0
 */

public class TipsActivity extends AppCompatActivity {
    public static final String EXTRA="tipIndex";
    /**
     * Method to set the arraylist <code>tips</code> from <code>TipsList</code> into ListView View.
     * Method sets onClick listener into ListViews items inside this method that starts anohter activity with the clicked tips NAME and <CODE>DESCRIPTION</CODE>.
     * @param savedInstanceState if the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState. Otherwise it is null.
     * @see TipDetailsActivity#onCreate(Bundle)
     * @see TipsList#getTips()
     */

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        ListView lv = findViewById(R.id.lista);

        lv.setAdapter(new ArrayAdapter<Tip>(
                this,
                R.layout.activity_listview,
                TipsList.getInstance().getTips())
        );

        // opening the activity with descriptions

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("appi", "onItemClick(" + i + ")");
                Intent nextActivity = new Intent(TipsActivity.this, TipDetailsActivity.class);
                nextActivity.putExtra(EXTRA, i);
                startActivity(nextActivity);
            }
        });

    }
}
