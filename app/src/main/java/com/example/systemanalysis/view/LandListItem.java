package com.example.systemanalysis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.systemanalysis.R;

public class LandListItem extends FrameLayout {
    TextView tvAddress;
    TextView tvDepartment;
    TextView tvDistrict;
    TextView tvProvince;
    TextView tvName;

    public LandListItem(@NonNull Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public LandListItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
    }

    public LandListItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
    }

    public LandListItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
    }

    private void initInflate(){
        // Inflate Layout here
        inflate(getContext() , R.layout.land_list , this);
    }

    private void initInstances(){
        // findViewByID here
        tvAddress = findViewById(R.id.tvAddress);
        tvDepartment = findViewById(R.id.tvDepartment);
        tvDistrict = findViewById(R.id.tvDistrict);
        tvProvince = findViewById(R.id.tvProvince);
        tvName = findViewById(R.id.tvName);
    }

    public void setAddressText(String text){
        tvAddress.setText(text);
    }
    public void setDepartmentText(String text){
        tvDepartment.setText(text);
    }
    public void setDistrictText(String text){
        tvDistrict.setText(text);
    }
    public void setProvinceText(String text){
        tvProvince.setText(text);
    }
    public void setNameText(String text){
        tvName.setText(text);
    }

}
