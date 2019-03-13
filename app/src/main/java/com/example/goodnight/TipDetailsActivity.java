package com.example.goodnight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
/**Class for TipsActivity to open into and display guide for better sleep based on which tip was clicked.
 * @author Katri Raisio
 * @author Kimmo Perälä
 * @author Toni Ruoranen
 * @version 1.0
 */

public class TipDetailsActivity extends AppCompatActivity {
    /**
     *Method to set the layout when activity is first created and reading from intent which tip name and description taken from tips arraylist by index number is set into TextViews.
     * @param savedInstanceState if the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState. Otherwise it is null.
     * @see TipsList#getTip(int)
     * @see Tip#getName()
     * @see Tip#getDescription()
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_details);

        //retrieving index of the tip; retrieving the name and the description

        Bundle b = getIntent().getExtras();
        int i = b.getInt("tipIndex", 0);

        ((TextView) findViewById(R.id.textView1)).setText(TipsList.getInstance().getTip(i).getName());

        ((TextView) findViewById(R.id.textView2)).setText(TipsList.getInstance().getTip(i).getDescription());
    }
}
