package com.example.goodnight;

public class Night {
    private final double TIME_TOSLEEP;
    private final double TIME_WAKEUP;
    private final double TIME_SLEPT;
    private final int MOOD;
    private final boolean CB_SPECIAL;
    private final boolean CB_NAPPING;
    private final boolean CB_EXERCISE;

    /**
     * Constructor for Class Night
     * @param time_toSleep
     * @param time_wakeUp
     * @param time_slept
     * @param mood
     * @param cb_special
     * @param cb_napping
     * @param cb_exercise
     */
    public Night(double time_toSleep, double time_wakeUp, double time_slept,
                 int mood, boolean cb_special, boolean cb_napping, boolean cb_exercise) {
        this.TIME_TOSLEEP = time_toSleep;
        this.TIME_WAKEUP = time_wakeUp;
        this.TIME_SLEPT = time_slept;
        this.MOOD = mood;
        this.CB_SPECIAL = cb_special;
        this.CB_NAPPING = cb_napping;
        this.CB_EXERCISE = cb_exercise;
    }

    public double getTime_slept() {
        return TIME_SLEPT;
    }

    public int getMood() {
        return MOOD;
    }

    public boolean isCb_special() {
        return CB_SPECIAL;
    }

    public boolean isCb_napping() {
        return CB_NAPPING;
    }

    public boolean isCb_exercise() {
        return CB_EXERCISE;
    }

    @Override
    public String toString() {
        return "Night{" +
                "TIME_TOSLEEP=" + TIME_TOSLEEP +
                ", TIME_WAKEUP=" + TIME_WAKEUP +
                ", TIME_SLEPT=" + TIME_SLEPT +
                ", MOOD=" + MOOD +
                ", CB_SPECIAL=" + CB_SPECIAL +
                ", CB_NAPPING=" + CB_NAPPING +
                ", CB_EXERCISE=" + CB_EXERCISE +
                '}';
    }
}
