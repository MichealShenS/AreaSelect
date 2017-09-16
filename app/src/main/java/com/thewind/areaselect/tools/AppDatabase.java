package com.thewind.areaselect.tools;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.thewind.areaselect.bean.City;
import com.thewind.areaselect.bean.County;
import com.thewind.areaselect.bean.Province;
import com.thewind.areaselect.bean.Town;
import com.thewind.areaselect.dao.AreaDAO;

/**
 * Created by xic on 2017/9/12.
 */

@Database(entities = {Province.class, City.class, County.class, Town.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;
    private static Context context;
    public static AppDatabase getDatabase(Context c) {
        context = c;
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "geoinfo")
                     // 版本改变，清除数据
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sInstance;
    }

    public static void onDestroy() {
        sInstance = null;
    }

    public abstract AreaDAO getAreaDAO();
}
