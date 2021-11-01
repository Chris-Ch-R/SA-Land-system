package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.systemanalysis.dao.AdsItemDao;
import com.example.systemanalysis.manager.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAdsActivity extends AppCompatActivity {
    int landID;
    double latitude , longitude;
    String name;
    String deedNumber;
    String landNumber;
    String landAddress;
    String tombon;
    String amphure;
    String province;
    int landArea;

    EditText edtAdsDetail;
    EditText edtCost;
    EditText edtContract;
    EditText edtLatitude;
    EditText edtLongitude;
    Button btnSaveAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ads);
        Intent intent = getIntent();
        landID = intent.getIntExtra("land_id" , -1);
        latitude = intent.getDoubleExtra("latitude" , 13.736717);
        longitude = intent.getDoubleExtra("longitude" , 13.736717);
        name = intent.getStringExtra("name");
        deedNumber = intent.getStringExtra("deedNumber" );
        landNumber = intent.getStringExtra("landNumber" );
        landAddress = intent.getStringExtra("landAddress" );
        tombon = intent.getStringExtra("Tombon");
        amphure = intent.getStringExtra("amphure");
        province = intent.getStringExtra("province" );
        landArea = intent.getIntExtra("landArea" , 100);
        initInstance();
    }

    private void initInstance(){
        edtAdsDetail = findViewById(R.id.edtAdsDetail);
        edtCost = findViewById(R.id.edtCost);
        edtContract = findViewById(R.id.edtContract);
        edtLatitude = findViewById(R.id.edtAdsLatitude);
        edtLongitude = findViewById(R.id.edtAdsLongitude);
        btnSaveAds = findViewById(R.id.btnSaveAds);
        btnSaveAds.setOnClickListener(v -> saveAds());

    }

    private void saveAds(){
        String message = edtAdsDetail.getText().toString();
        double cost = Double.parseDouble(edtCost.getText().toString());
        String contact = edtContract.getText().toString();
        double adsLatitude = Double.parseDouble(edtLatitude.getText().toString());
        double adsLongitude = Double.parseDouble(edtLongitude.getText().toString());

        if (message.equals("") ||
                edtCost.getText().toString().equals("") ||
                contact.equals("") ||
                edtLatitude.getText().toString().equals("") ||
                edtLongitude.getText().toString().equals("")){
            AlertDialog alertDialog = new AlertDialog.Builder(AddAdsActivity.this).create();
            alertDialog.setTitle("เกิดข้อผิดพลาด");
            alertDialog.setMessage("กรุณาใส่ข้อมูลให้ครบถ้วนและถูกต้อง");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> {
                        dialog.dismiss();
                        return;
                    });
            alertDialog.show();

        }

        AdsItemDao ads = new AdsItemDao();
        ads.setLandId(landID);
        ads.setMessage(message);
        ads.setCost(cost);
        ads.setContact(contact);
        ads.setLatitude(adsLatitude);
        ads.setLongitude(adsLongitude);

        Call<AdsItemDao> call = HttpManager.getInstance().getService().addAds(ads);
        call.enqueue(new Callback<AdsItemDao>() {
            @Override
            public void onResponse(Call<AdsItemDao> call,
                                   Response<AdsItemDao> response) {
                if (response.isSuccessful() ){
                    Toast.makeText(getApplicationContext() , "success" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext() , MapsActivity.class);
                    intent.putExtra("landId" , landID);
                    intent.putExtra("latitude" ,latitude);
                    intent.putExtra("longitude" , longitude);
                    intent.putExtra("name" , name);
                    intent.putExtra("deedNumber" , deedNumber);
                    intent.putExtra("landNumber" , landNumber);
                    intent.putExtra("landAddress" , landAddress);
                    intent.putExtra("Tombon" , tombon);
                    intent.putExtra("amphure" , amphure);
                    intent.putExtra("province" , province);
                    intent.putExtra("landArea" , landArea);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext() , response.errorBody() + "" , Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<AdsItemDao> call, Throwable t) {
                Toast.makeText(getApplicationContext() , t.toString() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}