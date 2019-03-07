package com.example.goodnight;

import java.util.ArrayList;

public class TipsList {
    private ArrayList<Tip> tips;
    private static final TipsList ourInstance = new TipsList();

    public static TipsList getInstance() {
        return ourInstance;
    }

    private TipsList() {
        tips = new ArrayList<Tip>();
        tips.add(new Tip("Need of sleep", "Adult needs 7 to 9 hours of sleep every day. Also, it may be appropiate to sleep between 6 to 10 hours. It is not recommended to sleep less than 6 or more than 10 hours per day. For people over 65 years old, recommendation is 7 to 8 hours per night"));
        tips.add(new Tip("Better sleep times", "Sleep long enough. Don't eat too late or too heavy. Exercise but not too late. If it's winter, use light therapy."));
        tips.add(new Tip("Increase the quality","Go to bed only when you're tired. If you can't fall asleep in 15 minutes, get up for a while. Get up at the same time every morning. Don't take naps."));
    }

    public ArrayList<Tip> getTips() {
        return tips;
    }
    public Tip getTip (int index) {
        return tips.get(index);
    }
}
