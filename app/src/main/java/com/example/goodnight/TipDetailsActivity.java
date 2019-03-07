package com.example.goodnight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TipDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_details);

        Bundle b = getIntent().getExtras();
        int i = b.getInt("tipIndex", 0);

        ((TextView) findViewById(R.id.textView1)).setText(TipsList.getInstance().getTip(i).getName());

        ((TextView) findViewById(R.id.textView2)).setText(TipsList.getInstance().getTip(i).getDescription());
    }
}
