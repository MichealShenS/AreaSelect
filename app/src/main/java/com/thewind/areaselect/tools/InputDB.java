package com.thewind.areaselect.tools;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 导入外部数据库到手机上，使用时，只需调用构造函数
 */
public class InputDB {

    private Context context;
    private final int BUFFER_SIZE = 400000;

    /**
     * 构造函数
     *
     * @param context
     * @param DB_NAME 保存数据库文件名，不用带后缀名
     * @param DB_ID   要导入的数据库ID，一般放在raw中
     */
    public InputDB(Context context, String DB_NAME, int DB_ID, boolean isOverWrite) {
        this.context = context;
        Log.i(context.getClass().getSimpleName(), "准备导入数据库");
        inputDatabase("/data/data/" + context.getPackageName() + "/databases/" + DB_NAME, DB_ID, isOverWrite);
    }

    /**
     * 导入数据库
     *
     * @param dbfile 数据库完整路径
     * @param id     数据库资源文件ID
     */
    private void inputDatabase(String dbfile, int id, boolean isOverWrite) {
        try {
            Log.i(context.getClass().getSimpleName(), "数据库位置：" + dbfile);

            File destDir = new File("/data/data/" + context.getPackageName() + "/databases/");
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            // 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
            if (!(new File(dbfile).exists()) || isOverWrite) {
                InputStream is = this.context.getResources().openRawResource(id);
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 
