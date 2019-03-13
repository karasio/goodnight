package com.example.goodnight;

import android.os.Bundle;

/**Class of <code>Tip</code> objects for singleton class <code>TipsList</code> to create and store in its private arraylist <code>private ArrayList<Tip> tips</code> and inherit night object methods and values.
 *
 * @author Kimmo Perälä
 *
 * @version 1.0
 */

public class Tip {
    private final String NAME;
    private final String DESCRIPTION;
    /**
     * Constructor for <code>Tip</code> object to be inserted <code>TipsList</code> arraylist singleton.
     * @param name              String name to put in the ListView as titles for descriptions
     * @param description       String of detailed sleep related info
     * @see TipsList#TipsList()
     */

    public Tip (String name, String description) {
        this.NAME = name;
        this.DESCRIPTION = description;
    }
    /**
     * Method to get <code>Tip</code> <CODE>NAME</CODE> in <code>TipDetailsActivity</code> for setText.
     * @return string NAME of the <code>Tip</code>.
     * @see TipDetailsActivity#onCreate(Bundle)
     */

    public String getName() {
        return this.NAME;
    }
    /**
     * Method to get  <code>Tip description</code> in <code>TipDetailsActivity</code> for setText.
     * @return string DESCRIPTION of the <code>tip</code>.
     * @see TipDetailsActivity#onCreate(Bundle)
     */

    public String getDescription() {
        return this.DESCRIPTION;
    }
    /**
     *Another way to get <code>Tip</code> objects name.
     * @return string NAME of the <code>Tip</code>.
     */

    @Override
    public String toString() {
        return this.NAME;
    }
}
