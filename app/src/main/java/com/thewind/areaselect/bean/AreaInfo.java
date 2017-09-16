package com.thewind.areaselect.bean;

import java.io.Serializable;

/**
 * Created by xic on 2017/9/13.
 */

public class AreaInfo implements Serializable {
    private int provinceID;
    private int cityID;
    private int countyID;
    private int townID;

    private String provinceName;
    private String cityName;
    private String countyName;
    private String townName;

    public String nameToString() {
        return provinceName + " " + cityName + " " + countyName + " " + townName;
    }

    public String idToString() {
        return provinceID + " " + cityID + " " + countyID + " " + townID;
    }

    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public int getCountyID() {
        return countyID;
    }

    public void setCountyID(int countyID) {
        this.countyID = countyID;
    }

    public int getTownID() {
        return townID;
    }

    public void setTownID(int townID) {
        this.townID = townID;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
