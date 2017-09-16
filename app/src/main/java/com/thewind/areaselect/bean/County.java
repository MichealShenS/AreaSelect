package com.thewind.areaselect.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by xic on 2017/9/12.
 */
@Entity(tableName = "county")
public class County {
    @PrimaryKey
    private int ID;
    private String Name;
    private String UpLevelID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUpLevelID() {
        return UpLevelID;
    }

    public void setUpLevelID(String upLevelID) {
        UpLevelID = upLevelID;
    }
}
