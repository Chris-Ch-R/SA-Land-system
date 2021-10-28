package com.example.systemanalysis.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.systemanalysis.dao.LandItemCollection;
import com.example.systemanalysis.dao.LandItemDao;
import com.example.systemanalysis.manager.LandListManager;
import com.example.systemanalysis.view.LandListItem;

public class LandListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        if (LandListManager.getInstance().getDao() == null)
            return 0;
        return LandListManager.getInstance().getDao().size();
    }

    @Override
    public Object getItem(int position) {
        return LandListManager.getInstance().getDao().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LandListItem item;
        if (convertView != null)
            item = (LandListItem) convertView;
        else
            item = new LandListItem(parent.getContext());

        LandItemDao dao = (LandItemDao) getItem(position);
        //TODO getAddress
        item.setAddressText(dao.getLandAddress());
//        item.setDepartmentText("แขวง : " + dao.get.getDepartment());
//        item.setDistrictText("เขต : " +dao.getAddress().getDistrict());
//        item.setProvinceText("จังหวัด : " +dao.getAddress().getProvince());
        item.setNameText(dao.getOwner().getName());


        return item;
    }
}
