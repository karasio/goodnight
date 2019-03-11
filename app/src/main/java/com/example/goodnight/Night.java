package com.example.goodnight;

public class Night {
    private final double time_ToSleep;
    private final double time_wakeUp;
    private final double time_slept;
    private final int mood;
    private final boolean cb_special;
    private final boolean cb_napping;
    private final boolean cb_exercise;

    public Night(double time_ToSleep, double time_wakeUp, double time_slept,
                 int mood, boolean cb_special, boolean cb_napping, boolean cb_exercise) {
        this.time_ToSleep = time_ToSleep;
        this.time_wakeUp = time_wakeUp;
        this.time_slept = time_slept;
        this.mood = mood;
        this.cb_special = cb_special;
        this.cb_napping = cb_napping;
        this.cb_exercise = cb_exercise;
    }

    public double getTime_slept() {
        return time_slept;
    }

    public int getMood() {
        return mood;
    }

    public boolean isCb_special() {
        return cb_special;
    }

    public boolean isCb_napping() {
        return cb_napping;
    }

    public boolean isCb_exercise() {
        return cb_exercise;
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
