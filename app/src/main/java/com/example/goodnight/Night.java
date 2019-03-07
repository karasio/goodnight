package com.example.goodnight;

public class Night {
    private double time_ToSleep;
    private double time_wakeUp;
    private double time_slept;
    private int mood;
    private boolean cb_special;
    private boolean cb_napping;
    private boolean cb_exercise;
    private int howManyNights;
    private double howManyHours;
    private int moodSum;

    public Night(double time_ToSleep, double time_wakeUp, double time_slept,
                 int mood, boolean cb_special, boolean cb_napping, boolean cb_exercise, int howManyNights, double howManyHours, int moodSum) {
        this.time_ToSleep = time_ToSleep;
        this.time_wakeUp = time_wakeUp;
        this.time_slept = time_slept;
        this.mood = mood;
        this.cb_special = cb_special;
        this.cb_napping = cb_napping;
        this.cb_exercise = cb_exercise;
        this.howManyNights = howManyNights;
        this.howManyHours = howManyHours;
        this.moodSum = moodSum;
    }

    public double getTime_ToSleep() {
        return time_ToSleep;
    }

    public void setTime_ToSleep(double time_ToSleep) {
        this.time_ToSleep = time_ToSleep;
    }

    public double getTime_wakeUp() {
        return time_wakeUp;
    }

    public void setTime_wakeUp(double time_wakeUp) {
        this.time_wakeUp = time_wakeUp;
    }

    public double getTime_slept() {
        return time_slept;
    }

    public void setTime_slept(double time_slept) {
        this.time_slept = time_slept;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public boolean isCb_special() {
        return cb_special;
    }

    public void setCb_special(boolean cb_special) {
        this.cb_special = cb_special;
    }

    public boolean isCb_napping() {
        return cb_napping;
    }

    public void setCb_napping(boolean cb_napping) {
        this.cb_napping = cb_napping;
    }

    public boolean isCb_exercise() {
        return cb_exercise;
    }

    public void setCb_exercise(boolean cb_exercise) {
        this.cb_exercise = cb_exercise;
    }

    public int getHowManyNights() {
        return howManyNights;
    }

    public void setHowManyNights(int howManyNights) {
        this.howManyNights = howManyNights;
    }

    public double getHowManyHours() {
        return howManyHours;
    }

    public void setHowManyHours(double howManyHours) {
        this.howManyHours = howManyHours;
    }

    public int getMoodSum() {
        return moodSum;
    }

    public void setMoodSum(int moodSum) {
        this.moodSum = moodSum;
    }

    @Override
    public String toString() {
        return "Night{" +
                "time_ToSleep=" + time_ToSleep +
                ", time_wakeUp=" + time_wakeUp +
                ", time_slept=" + time_slept +
                ", mood=" + mood +
                ", cb_special=" + cb_special +
                ", cb_napping=" + cb_napping +
                ", cb_exercise=" + cb_exercise +
                '}';
    }
}
