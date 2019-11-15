package com.sgs.usmansh.schmngsys;

/**
 * Created by Usman Sh on 3/28/2018.
 */

public class Fine {

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }

    public String getUniform() {
        return uniform;
    }

    public void setUniform(String uniform) {
        this.uniform = uniform;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    String late;
    String uniform;
    String absent;
}
