package com.example.goodnight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TipsActivity extends AppCompatActivity {
    public static final String EXTRA="tipIndex";

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
