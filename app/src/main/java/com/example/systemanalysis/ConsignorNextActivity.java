package com.example.systemanalysis;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.example.systemanalysis.dao.AmphureDao;
import com.example.systemanalysis.dao.ConsignmentItemDao;
import com.example.systemanalysis.dao.LandFileDao;
import com.example.systemanalysis.dao.ProvinceDao;
import com.example.systemanalysis.dao.TombonDao;
import com.example.systemanalysis.manager.HttpManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsignorNextActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ImageView imageView;
    Button pickImage;
    Button uploadFile;
    Button btnSaveLand;
    Button btnDate;
    TextView tvAgreement;
    TextView tvDate;
    Spinner spProvince;
    Spinner spTombon;
    Spinner spAmphure;
    Spinner spColorType;
    EditText edtLandNumber;
    EditText edtDeedNumber;
    EditText edtLandAddress;
    EditText edtLandAreaReal;
    EditText edtLandAreaDeed;
    EditText edtPricePerUnit;
    EditText edtCommission;
    EditText edtLatitude;
    EditText edtLongtitude;

    String filePath;
    String filename;

    String landNumber = "";
    String deedNumber = "";
    String address = "";
    String colorType = "";
    String date = "";
    double landAreaReal = 0;
    double landAreaDeed = 0;
    int tombonId;

    int consignmentId;

    int realArea;
    int deedArea;
    double pricePerUnit;
    double commission;
    double latitude;
    double longtitude;

    List<ConsignmentItemDao> consignmentItemDaoList;
    List<ProvinceDao> province;
    List<AmphureDao> amphure;
    List<TombonDao> tombon;
    List<String> colorTypeList;

    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_PICK_FILE = 3;

    private static final String TAG = "tag";

    private String mediaPath;

    ProgressDialog pDialog;
    private String postPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignor_next);
        Intent intent = getIntent();
        consignmentId = intent.getIntExtra("consignment_id", -1);

        initInstance();
    }

    private void initInstance() {
        tvAgreement = findViewById(R.id.tvAgreement);
        tvDate = findViewById(R.id.tvDate);

        uploadFile = findViewById(R.id.uploadFile);
        uploadFile.setOnClickListener(v -> {
            showChooser();
        });

        spProvince = findViewById(R.id.spProvince);
        spAmphure = findViewById(R.id.spAmphure);
        spTombon = findViewById(R.id.spTombon);
        spColorType = findViewById(R.id.spColorType);

        btnDate = findViewById(R.id.btnDate);
        btnDate.setOnClickListener(v -> showDatePickerDialog());

        edtLandNumber = findViewById(R.id.edtLandNumber);
        edtDeedNumber = findViewById(R.id.edtDeedNumber);
        edtLandAddress = findViewById(R.id.edtLandAddress);
        edtLandAreaReal = findViewById(R.id.edtLandAreaReal);
        edtLandAreaDeed = findViewById(R.id.edtLandAreaDeed);
        edtPricePerUnit = findViewById(R.id.edtPricePerUnit);
        edtCommission = findViewById(R.id.edtCommission);
        edtLatitude = findViewById(R.id.edtLatitude);
        edtLongtitude = findViewById(R.id.edtLongtitude);

        btnSaveLand = findViewById(R.id.btnSaveLand);
        btnSaveLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLandData();
            }
        });

        imageView = findViewById(R.id.preview);
        pickImage = findViewById(R.id.pickImage);

        fetchProvince();
        fetchColorType();


        pickImage.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
        });

        initDialog();


        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<AmphureDao> provinceAmphure = province.get(position).getAmphure();


                amphure = new ArrayList<>(provinceAmphure);
                ArrayAdapter<AmphureDao> amphureAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, amphure);
                spAmphure.setAdapter(amphureAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spAmphure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String amphureID = String.valueOf(amphure.get(position).getId());
                fetchAmphureTombon(amphureID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//
        spTombon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tombonId = tombon.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spColorType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorType = colorTypeList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void showChooser() {
//        Intent target = FileUtils.createGetContentIntent();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //intent.putExtra("browseCoa", itemToBrowse);
        //Intent chooser = Intent.createChooser(intent, "Select a File to Upload");
        try {
            //startActivityForResult(chooser, FILE_SELECT_CODE);
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 3);
        } catch (Exception ex) {
            System.out.println("browseClick :" + ex);//android.content.ActivityNotFoundException ex
        }
//        startActivityForResult(intent, 3);
//

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {

                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    Log.i(TAG, "onActivityResult: " + mediaPath);

                    // Set the Image in ImageView for Previewing the Media
                    imageView.setImageURI(selectedImage);
                    cursor.close();


                    postPath = mediaPath;
                }


            }
            if (requestCode == REQUEST_PICK_FILE) {
                if (data == null) {
                    return;
                }
                try {
                    Uri uri = data.getData();

                    String mimeType = getContentResolver().getType(uri);
                    if (mimeType == null) {
                        String path = getPath(this, uri);
                        if (path == null) {
                            filename = FilenameUtils.getName(uri.toString());
                        } else {
                            File file = new File(path);
                            filename = file.getName();
                        }

                        Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_SHORT).show();

                    } else {
                        Uri returnUri = data.getData();
                        Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
                        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                        returnCursor.moveToFirst();
                        filename = returnCursor.getString(nameIndex);
                        String size = Long.toString(returnCursor.getLong(sizeIndex));
                    }
                    File fileSave = getExternalFilesDir(null);
                    String sourcePath = getExternalFilesDir(null).toString();
                    try {
                        copyFileStream(new File(sourcePath + "/" + filename), uri, this);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    filePath = sourcePath + "/" + filename;
                    tvAgreement.setText(filename);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }
//        if (requestCode == requestCode && resultCode == Activity.RESULT_OK) {
//
//
//        }
    }

    public static String getPath(Context context, Uri uri) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // DocumentProvider
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    private void copyFileStream(File dest, Uri uri, Context context)
            throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
            os.close();
        }
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private void fetchProvince() {
        Call<List<ProvinceDao>> call = HttpManager.getInstance().getService().loadProvince2();
        call.enqueue(new Callback<List<ProvinceDao>>() {
            @Override
            public void onResponse(Call<List<ProvinceDao>> call, Response<List<ProvinceDao>> response) {
                if (response.isSuccessful()) {
                    List<ProvinceDao> dao = response.body();
                    province = new ArrayList<>(dao);


                    ArrayAdapter<ProvinceDao> provinceAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, dao);
                    spProvince.setAdapter(provinceAdapter);


                } else {
                    Toast.makeText(getApplicationContext(), "Cannot load province data, please try again", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<List<ProvinceDao>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                tvAgreement.setText(t.toString());
            }
        });

    }

    private void fetchAmphureTombon(String amphureId) {
        Call<List<TombonDao>> call = HttpManager.getInstance().getService().loadTombon(amphureId);
        call.enqueue(new Callback<List<TombonDao>>() {
            @Override
            public void onResponse(Call<List<TombonDao>> call, Response<List<TombonDao>> response) {
                if (response.isSuccessful()) {
                    List<TombonDao> amphuresTombon = response.body();
                    tombon = new ArrayList<>(amphuresTombon);
                    ArrayAdapter<TombonDao> tombonAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, tombon);
                    spTombon.setAdapter(tombonAdapter);
                    Log.i(TAG, "onResponse: " + amphuresTombon);
                } else {
                    Log.i(TAG, "onResponse: fail");
                }
            }

            @Override
            public void onFailure(Call<List<TombonDao>> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());

            }
        });
    }

    private void fetchColorType() {
        colorTypeList = new ArrayList<>();
        colorTypeList.add("YELLOW");
        colorTypeList.add("ORANGE");
        colorTypeList.add("BROWN");
        colorTypeList.add("RED");
        colorTypeList.add("PURPLE");
        colorTypeList.add("PINK");
        colorTypeList.add("GREEN");
        colorTypeList.add("JADE");
        colorTypeList.add("BLOND");
        colorTypeList.add("BLUE");
        ArrayAdapter<String> colorTypeAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, colorTypeList);
        spColorType.setAdapter(colorTypeAdapter);
    }

    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading..");
        pDialog.setCancelable(true);
    }


    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

    private void saveLandData() {

        if (postPath == null || postPath.equals("") ||
                edtLandNumber.getText().toString().equals("") ||
                edtDeedNumber.getText().toString().equals("") ||
                edtLandAddress.getText().toString().equals("") ||
                edtLandAreaReal.getText().toString().equals("") ||
                edtLandAreaDeed.getText().toString().equals("") ||
                edtPricePerUnit.getText().toString().equals("") ||
                edtLatitude.getText().toString().equals("") ||
                edtLongtitude.getText().toString().equals("") ||
                edtCommission.getText().toString().equals("") ||
                filePath == null ||
                filePath.equals("")
        ) {
//            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show();
            AlertDialog alertDialog = new AlertDialog.Builder(ConsignorNextActivity.this).create();
            alertDialog.setTitle("เกิดข้อผิดพลาด");
            alertDialog.setMessage("กรุณาใส่ข้อมูลให้ครบถ้วนและถูกต้อง");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> {
                        dialog.dismiss();
                        return;
                    });
            alertDialog.show();
            return;
        } else {
            showpDialog();
            // get Data from editText


            if (!edtLandNumber.getText().toString().equals(""))
                landNumber = edtLandNumber.getText().toString();
            if (!edtDeedNumber.getText().toString().equals(""))
                deedNumber = edtDeedNumber.getText().toString();
            if (!edtLandAddress.getText().toString().equals(""))
                address = edtLandAddress.getText().toString();
            if (!edtLandAreaReal.getText().toString().equals(""))
                landAreaReal = Double.parseDouble(edtLandAreaReal.getText().toString());
            if (!edtLandAreaDeed.getText().toString().equals(""))
                landAreaDeed = Double.parseDouble(edtLandAreaDeed.getText().toString());
            if (!edtPricePerUnit.getText().toString().equals(""))
                pricePerUnit = Double.parseDouble(edtPricePerUnit.getText().toString());
            if (!edtLatitude.getText().toString().equals(""))
                latitude = Double.parseDouble(edtLatitude.getText().toString());
            if (!edtLongtitude.getText().toString().equals(""))
                longtitude = Double.parseDouble(edtLongtitude.getText().toString());
            if (!edtCommission.getText().toString().equals(""))
                commission = Double.parseDouble(edtCommission.getText().toString());
            //
            File imageFile = new File(postPath);
            File agreementFile = new File(filePath);

            Log.i(TAG, "saveLandData: " + landNumber + "deedNumber " + deedNumber + "address: " + address);
            // Parsing any Media type file

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            MultipartBody.Part image =
                    MultipartBody.Part.createFormData("images[0]", imageFile.getName(), requestFile);

            RequestBody reqConsignmentId =
                    RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(consignmentId));
            RequestBody reqTombonId =
                    RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(tombonId));
            RequestBody reqLandNumber =
                    RequestBody.create(MediaType.parse("multipart/form-data"), landNumber);
            RequestBody reqDeedNumber =
                    RequestBody.create(MediaType.parse("multipart/form-data"), deedNumber);
            RequestBody reqLandAddres =
                    RequestBody.create(MediaType.parse("multipart/form-data"), address);
            RequestBody reqLandAreaReal =
                    RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(landAreaReal));
            RequestBody reqLandAreaDeed =
                    RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(landAreaDeed));
            RequestBody reqPricePerUnit =
                    RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(pricePerUnit));
            RequestBody reqTypeByColor =
                    RequestBody.create(MediaType.parse("multipart/form-data"), colorType);
            RequestBody reqCommission =
                    RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(commission));
            RequestBody reqLatitude =
                    RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(latitude));
            RequestBody reqLongitude =
                    RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(longtitude));
            RequestBody reqDeadline =
                    RequestBody.create(MediaType.parse("multipart/form-data"), date);
//
            RequestBody requestAgreementFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), agreementFile);
            MultipartBody.Part agreement =
                    MultipartBody.Part.createFormData("file", agreementFile.getName(), requestAgreementFile);


            Call<ResponseBody> call = HttpManager.getInstance().getService().addLand(
                    reqConsignmentId,
                    reqTombonId,
                    reqLandNumber,
                    reqDeedNumber,
                    reqLandAddres,
                    reqLandAreaReal,
                    reqLandAreaDeed,
                    reqPricePerUnit,
                    reqTypeByColor,
                    reqCommission,
                    reqLatitude,
                    reqLongitude,
                    image,
                    reqDeadline,
                    agreement
            );
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            hidepDialog();
                            Toast.makeText(getApplicationContext(), "บันทึกข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext() , HomeActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        hidepDialog();
                        Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาดในการบันทึกข้อมูล", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    hidepDialog();
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = year + "-" + (month + 1) + "-" + dayOfMonth;
        tvDate.setText(date);
    }
}