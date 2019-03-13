package com.example.goodnight;

import android.os.Bundle;

/**Class of Tip objects for singleton class TipsList to create and store in its private arraylist <code>private ArrayList<Tip> tips</code> and inherit night object methods and values.
 * @author Katri Raisio
 * @author Kimmo Perälä
 * @author Toni Ruoranen
 * @version 1.0
 */

public class Tip {
    private final String NAME;
    private final String DESCRIPTION;
    /**
     * Constructor for tips to be inserted TipsList arraylist singleton.
     * @param name              String name to put in the ListView as titles for descriptions
     * @param description       String of detailed sleep related info
     * @see TipsList#TipsList()
     */

    public Tip (String name, String description) {
        this.NAME = name;
        this.DESCRIPTION = description;
    }
    /**
     * Method to get Tip name in TipDetailsActivity for setText.
     * @return string name of the tip.
     * @see TipDetailsActivity#onCreate(Bundle)
     */

    public String getName() {
        return this.NAME;
    }
    /**
     * Method to get Tip description in TipDetailsActivity for setText.
     * @return string description of the tip.
     * @see TipDetailsActivity#onCreate(Bundle)
     */

    public String getDescription() {
        return this.DESCRIPTION;
    }
    /**
     *Another way to get tip objects name.
     * @return string name of the tip.
     */

    @Override
    public String toString() {
        return this.NAME;
    }
}
