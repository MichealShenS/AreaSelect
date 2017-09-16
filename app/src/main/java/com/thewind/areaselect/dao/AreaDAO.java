package com.thewind.areaselect.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.thewind.areaselect.bean.City;
import com.thewind.areaselect.bean.County;
import com.thewind.areaselect.bean.Province;
import com.thewind.areaselect.bean.Town;

import java.util.List;

/**
 * Created by xic on 2017/9/12.
 */

@Dao
public interface AreaDAO {
    @Query("select * FROM province")
    List<Province> getProvinceList();

    @Query("select * FROM city where UpLevelID = :UpLevelID")
    List<City> getCityList(int UpLevelID);

    @Query("select * FROM county where UpLevelID = :UpLevelID")
    List<County> getCountyList(int UpLevelID);

    @Query("select * FROM town where UpLevelID = :UpLevelID")
    List<Town> getTownList(int UpLevelID);
}
