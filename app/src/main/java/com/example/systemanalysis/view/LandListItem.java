package com.example.systemanalysis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.systemanalysis.R;

public class LandListItem extends FrameLayout {
    ImageView ivLandPhoto;
    TextView tvAddress;
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
        ivLandPhoto = findViewById(R.id.ivLandPhoto);
        tvAddress = findViewById(R.id.tvAddress);
        tvProvince = findViewById(R.id.tvProvince);
        tvName = findViewById(R.id.tvName);
    }

    public void setAddressText(String text){
        tvAddress.setText(text);
    }
    public void setProvinceText(String text){
        tvProvince.setText(text);
    }
    public void setNameText(String text){
        tvName.setText(text);
    }
    public void setImageUrl(String url){
        Glide.with(getContext())
                .load(url)
                .into(ivLandPhoto);
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * 2/3;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height , MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);

        setMeasuredDimension(width,height);
    }
}
