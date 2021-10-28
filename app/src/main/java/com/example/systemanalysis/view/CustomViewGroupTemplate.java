package com.example.systemanalysis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.systemanalysis.R;

public class CustomViewGroupTemplate extends FrameLayout {

    public CustomViewGroupTemplate(@NonNull Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public CustomViewGroupTemplate(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
    }

    public CustomViewGroupTemplate(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
    }

    public CustomViewGroupTemplate(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
    }
}
