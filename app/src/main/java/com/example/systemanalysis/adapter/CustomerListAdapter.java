package com.example.systemanalysis.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.systemanalysis.dao.CustomerItemDao;
import com.example.systemanalysis.manager.Contextor;
import com.example.systemanalysis.manager.CustomerListManager;
import com.example.systemanalysis.manager.LandListManager;
import com.example.systemanalysis.view.CustomerListItem;

public class CustomerListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        if (CustomerListManager.getInstance().getDao() == null)
            return 0;
        return CustomerListManager.getInstance().getDao().size();
    }

    @Override
    public Object getItem(int position) {
        return CustomerListManager.getInstance().getDao().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomerListItem item;
        if (convertView != null)
            item = (CustomerListItem) convertView;
        else
            item = new CustomerListItem(parent.getContext());

        CustomerItemDao dao = (CustomerItemDao) getItem(position);
        item.setTvName(dao.getName());
        item.setTvEmail(dao.getEmail());
        item.setTvPhoneNumber(dao.getPhoneNumber());

        return item;
    }
}
