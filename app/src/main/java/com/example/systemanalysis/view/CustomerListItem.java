package com.example.systemanalysis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.systemanalysis.R;

public class CustomerListItem extends FrameLayout {
    TextView tvName;
    TextView tvEmail;
    TextView tvPhoneNumber;

    public CustomerListItem(@NonNull Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public CustomerListItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
    }

    public CustomerListItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
    }

    public CustomerListItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
    }

    private void initInflate(){
        // Inflate Layout here
        inflate(getContext() , R.layout.customer_list , this);
    }

    private void initInstances(){
        // findViewByID here
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
    }

    public void setTvName(String name){
        tvName.setText(name);
    }
    public void setTvEmail(String mail){
        tvEmail.setText(mail);
    }
    public void setTvPhoneNumber(String phoneNumber){
        tvPhoneNumber.setText(phoneNumber);
    }
}
