package com.thewind.areaselect.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.thewind.areaselect.R;
import com.thewind.areaselect.baseAdapter.BaseAdapterHelper;
import com.thewind.areaselect.baseAdapter.QuickAdapter;
import com.thewind.areaselect.bean.AreaInfo;
import com.thewind.areaselect.bean.City;
import com.thewind.areaselect.bean.County;
import com.thewind.areaselect.bean.Province;
import com.thewind.areaselect.bean.Town;
import com.thewind.areaselect.tools.AppDatabase;
import com.thewind.areaselect.tools.FinishListener;
import com.thewind.areaselect.tools.InputDB;
import com.thewind.areaselect.tools.MyUtils;

import java.util.List;

/**
 * Created by xic on 2017/9/12.
 */

public class AreaSelector {
    private QuickAdapter<Province> adapterProvince;
    private QuickAdapter<City> adapterCity;
    private QuickAdapter<County> adapterCountry;
    private QuickAdapter<Town> adapterTown;

    private LinearLayout layoutTitle;
    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvCountry;
    private TextView tvTown;
    private TextView tvVillage;
    private ListView listView;
    private Dialog dialog;

    private int provinceId;
    private int cityId;
    private int countryId;
    private int townId;

    /**
     * 默认 4 级
     */
    private int maxLevel;
    private Context context;
    private FinishListener finishListener;
    private View view;

    public AreaSelector(Context context, int maxLevel, FinishListener finishListener) {
        this.context = context;
        this.finishListener = finishListener;
        new InputDB(context, "geoinfo", R.raw.geoinfo, false);
        if (maxLevel <= 0 || maxLevel > 4)
            this.maxLevel = 4;
        else
            this.maxLevel = maxLevel;
        view = getView();
    }

    /**
     * 设置标题背景颜色
     * @param color
     * @return
     */
    public AreaSelector setTitleBgColor(int color){
        layoutTitle.setBackgroundColor(ContextCompat.getColor(context, color));
        return this;
    }

    /**
     * 显示
     */
    public void show() {
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = MyUtils.getScreenWidth(context);
        lp.height = MyUtils.getScreenHeight(context) / 2;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private View getView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_area_select, null);
        findViewAndSetListener(view);
        setAdapter();
        return view;
    }

    private void findViewAndSetListener(View view) {
        layoutTitle = (LinearLayout) view.findViewById(R.id.layoutTitle);
        tvProvince = (TextView) view.findViewById(R.id.tvProvince);
        tvCity = (TextView) view.findViewById(R.id.tvCity);
        tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        tvTown = (TextView) view.findViewById(R.id.tvTown);
        tvVillage = (TextView) view.findViewById(R.id.tvVillage);
        listView = (ListView) view.findViewById(R.id.listView);

        tvProvince.setOnClickListener(clickListener);
        tvCity.setOnClickListener(clickListener);
        tvCountry.setOnClickListener(clickListener);
        tvTown.setOnClickListener(clickListener);
        tvVillage.setOnClickListener(clickListener);
        listView.setOnItemClickListener(itemClickListener);
    }

    private void setAdapter() {
        adapterProvince = new QuickAdapter<Province>(context, R.layout.public_text) {
            @Override
            protected void convert(BaseAdapterHelper helper, Province subName) {
                helper.setText(R.id.textView, subName.getName());
            }
        };

        adapterCity = new QuickAdapter<City>(context, R.layout.public_text) {
            @Override
            protected void convert(BaseAdapterHelper helper, City subName) {
                helper.setText(R.id.textView, subName.getName());
            }
        };

        adapterCountry = new QuickAdapter<County>(context, R.layout.public_text) {
            @Override
            protected void convert(BaseAdapterHelper helper, County subName) {
                helper.setText(R.id.textView, subName.getName());
            }
        };

        adapterTown = new QuickAdapter<Town>(context, R.layout.public_text) {
            @Override
            protected void convert(BaseAdapterHelper helper, Town subName) {
                helper.setText(R.id.textView, subName.getName());
            }
        };
        new GetProvince().execute();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tvProvince) {
                if (tvProvince.getText().toString().equals("请选择")) return;
                level = 1;
                tvCity.setText("请选择");
                tvCity.setVisibility(View.VISIBLE);
                tvCountry.setVisibility(View.GONE);
                tvTown.setVisibility(View.GONE);
                tvVillage.setVisibility(View.GONE);
                listView.setAdapter(adapterProvince);
            }
            if (v.getId() == R.id.tvCity) {
                if (tvCity.getText().toString().equals("请选择")) return;
                level = 2;
                tvCountry.setText("请选择");
                tvCountry.setVisibility(View.VISIBLE);
                tvTown.setVisibility(View.GONE);
                tvVillage.setVisibility(View.GONE);
                listView.setAdapter(adapterCity);
            }
            if (v.getId() == R.id.tvCountry) {
                if (tvCountry.getText().toString().equals("请选择")) return;
                level = 3;
                tvTown.setText("请选择");
                tvTown.setVisibility(View.VISIBLE);
                tvVillage.setVisibility(View.GONE);
                listView.setAdapter(adapterCountry);
            }
            if (v.getId() == R.id.tvTown) {
                if (tvTown.getText().toString().equals("请选择")) return;
                level = 4;
                listView.setAdapter(adapterTown);
            }
        }
    };

    private void setFinishAddress(AreaInfo areaInfo) {
        dialog.dismiss();
        finishListener.finish(areaInfo);
    }

    /**
     * 记录当前点击到第几级
     */
    private int level = 1;
    /**
     * 记录选中的信息
     */
    private AreaInfo areaInfo;
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (areaInfo == null) areaInfo = new AreaInfo();
            switch (level) {
                case 1:
                    tvProvince.setText(adapterProvince.getItem(position).getName());
                    provinceId = adapterProvince.getItem(position).getID();

                    areaInfo.setProvinceID(provinceId);
                    areaInfo.setProvinceName(tvProvince.getText().toString().trim());

                    if (maxLevel == level) {
                        setFinishAddress(areaInfo);
                        return;
                    }
                    tvCity.setVisibility(View.VISIBLE);
                    new GetCity().execute();
                    break;
                case 2:
                    tvCity.setText(adapterCity.getItem(position).getName());
                    cityId = adapterCity.getItem(position).getID();

                    areaInfo.setCityID(cityId);
                    areaInfo.setCityName(tvCity.getText().toString().trim());

                    if (maxLevel == level) {
                        setFinishAddress(areaInfo);
                        return;
                    }
                    tvCountry.setVisibility(View.VISIBLE);
                    new GetCountry().execute();
                    break;
                case 3:
                    tvCountry.setText(adapterCountry.getItem(position).getName());
                    countryId = adapterCountry.getItem(position).getID();

                    areaInfo.setCountyID(countryId);
                    areaInfo.setCountyName(tvCountry.getText().toString().trim());

                    if (maxLevel == level) {
                        setFinishAddress(areaInfo);
                        return;
                    }
                    tvTown.setVisibility(View.VISIBLE);
                    new GetTown().execute();
                    break;
                case 4:
                    tvTown.setText(adapterTown.getItem(position).getName());
                    townId = adapterTown.getItem(position).getID();

                    areaInfo.setTownID(townId);
                    areaInfo.setTownName(tvTown.getText().toString().trim());

                    if (maxLevel == level) {
                        setFinishAddress(areaInfo);
                        return;
                    }
                    tvVillage.setVisibility(View.VISIBLE);
                    break;
            }

            if (level < maxLevel)
                ++level;
        }
    };

    /**
     * 从数据库查找数据：省份
     */
    private class GetProvince extends AsyncTask<Void, Integer, Void> {
        List<Province> list;

        @Override
        protected Void doInBackground(Void... params) {
            list = AppDatabase.getDatabase(context).getAreaDAO().getProvinceList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterProvince.clear();
            adapterProvince.addAll(list);
            listView.setAdapter(adapterProvince);
        }
    }

    /**
     * 从数据库查找数据：城市
     */
    private class GetCity extends AsyncTask<Void, Integer, Void> {
        List<City> list;

        @Override
        protected Void doInBackground(Void... params) {
            list = AppDatabase.getDatabase(context).getAreaDAO().getCityList(provinceId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterCity.clear();
            adapterCity.addAll(list);
            listView.setAdapter(adapterCity);
        }
    }

    /**
     * 从数据库查找数据：区
     */
    private class GetCountry extends AsyncTask<Void, Integer, Void> {
        List<County> list;

        @Override
        protected Void doInBackground(Void... params) {
            list = AppDatabase.getDatabase(context).getAreaDAO().getCountyList(cityId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterCountry.clear();
            adapterCountry.addAll(list);
            listView.setAdapter(adapterCountry);
        }
    }

    /**
     * 从数据库查找数据：镇/乡
     */
    private class GetTown extends AsyncTask<Void, Integer, Void> {
        List<Town> list;

        @Override
        protected Void doInBackground(Void... params) {
            list = AppDatabase.getDatabase(context).getAreaDAO().getTownList(countryId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapterTown.clear();
            adapterTown.addAll(list);
            listView.setAdapter(adapterTown);
        }
    }
}
