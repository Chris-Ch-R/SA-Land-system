package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.systemanalysis.dao.CustomerItemDao;
import com.example.systemanalysis.dao.LandItemDao;
import com.example.systemanalysis.manager.HttpManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailActivity extends AppCompatActivity {

    int customerID, landID;
    TextView tvCustomerName;
    TextView tvCustomerEmail;
    TextView tvCustomerPhone;
    TextView tvCustomerStatus;
    TextView tvFileName;
    Button btnUpload;
    Button btnConfirm;
    Button btnPending;
    Button btnCancel;

    String filename = "";
    String filePath = "";

    private static final int REQUEST_PICK_FILE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        Intent intent = getIntent();
        customerID = intent.getIntExtra("customerID", -1);
        landID = intent.getIntExtra("landID", -1);
        initInstance();
    }

    private void initInstance() {
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerEmail = findViewById(R.id.tvCustomerEmail);
        tvCustomerPhone = findViewById(R.id.tvCustomerPhone);
        tvCustomerStatus = findViewById(R.id.tvCustomerStatus);
        tvFileName = findViewById(R.id.tvFileName);
        btnUpload = findViewById(R.id.btnUpload);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnPending = findViewById(R.id.btnPending);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpload.setOnClickListener(v -> {
            showChooser();
        });
        btnPending.setOnClickListener(v -> {
            changeStatus("CONFIRM");
        });
        btnConfirm.setOnClickListener(v -> {
            changeStatus("DONE");
        });
        btnCancel.setOnClickListener(v -> {
            changeStatus("CANCEL");
        });

        Call<CustomerItemDao> call = HttpManager.getInstance().getService().loadCustomerDetail(customerID);
        call.enqueue(new Callback<CustomerItemDao>() {
            @Override
            public void onResponse(Call<CustomerItemDao> call,
                                   Response<CustomerItemDao> response) {
                if (response.isSuccessful()) {
                    CustomerItemDao dao = response.body();
                    tvCustomerName.setText(dao.getName());
                    tvCustomerEmail.setText(dao.getEmail());
                    tvCustomerPhone.setText(dao.getPhoneNumber());

                    for (LandItemDao land : dao.getLand()) {
                        if (land.getId() == landID) {
                            if (land.getCustomersLand().getStatus().equals("INTERESTED")) {

                                tvCustomerStatus.setText("สนใจซื้อ");
                                tvFileName.setVisibility(View.VISIBLE);
                                btnUpload.setVisibility(View.VISIBLE);

                            }
                            if (land.getCustomersLand().getStatus().equals("PENDING")){
                                tvCustomerStatus.setText("รอประเมิน");
                                tvFileName.setVisibility(View.INVISIBLE);
                                btnUpload.setVisibility(View.INVISIBLE);
                                btnPending.setVisibility(View.VISIBLE);
                            }
                            if (land.getCustomersLand().getStatus().equals("CONFIRM")){
                                tvCustomerStatus.setText("รอการยืนยันการซื้อ");
                                tvFileName.setVisibility(View.INVISIBLE);
                                btnUpload.setVisibility(View.INVISIBLE);
                                btnPending.setVisibility(View.INVISIBLE);
                                btnConfirm.setVisibility(View.VISIBLE);
                            }
                            if (land.getCustomersLand().getStatus().equals("DONE")){
                                tvCustomerStatus.setText("ซื้อที่ดินสำเร็จ");
                                tvFileName.setVisibility(View.INVISIBLE);
                                btnUpload.setVisibility(View.INVISIBLE);
                                btnCancel.setVisibility(View.INVISIBLE);
                            }
                            if (land.getCustomersLand().getStatus().equals("CANCEL")){
                                tvCustomerStatus.setText("ยกเลิก");
                                tvFileName.setVisibility(View.INVISIBLE);
                                btnUpload.setVisibility(View.INVISIBLE);
                                btnCancel.setVisibility(View.INVISIBLE);

                            }


                        }

                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<CustomerItemDao> call,
                                  Throwable t) {


            }
        });

    }
    private void uploadFile(String path){

        File agreementFile = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), agreementFile);
        MultipartBody.Part file =
                MultipartBody.Part.createFormData("file", agreementFile.getName(), requestFile);
        RequestBody reqlandId =
                RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(landID));
        RequestBody reqCustomerID =
                RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(customerID));

        Call<ResponseBody> call = HttpManager.getInstance().getService().addCustomerAgreement(reqlandId, reqCustomerID, file);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    tvCustomerStatus.setText("รอประเมิน");
                    btnPending.setVisibility(View.VISIBLE);
                    tvFileName.setVisibility(View.INVISIBLE);
                    btnUpload.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
    private void changeStatus(String pStatus){
        RequestBody reqlandId =
                RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(landID));
        RequestBody reqCustomerID =
                RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(customerID));
        RequestBody reqStatus =
                RequestBody.create(MediaType.parse("multipart/form-data"), pStatus);

        Call<ResponseBody> call = HttpManager.getInstance().getService().changeStatus(reqlandId, reqCustomerID, reqStatus);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    if (pStatus.equals("INTERESTED")) {

                        tvCustomerStatus.setText("สนใจซื้อ");

                    }
                    if (pStatus.equals("PENDING")){
                        tvCustomerStatus.setText("รอประเมิน");
                        tvFileName.setVisibility(View.INVISIBLE);
                        btnUpload.setVisibility(View.INVISIBLE);

                        btnPending.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext() , "ประเมินสำเร็จ" , Toast.LENGTH_SHORT).show();


                    }
                    if (pStatus.equals("CONFIRM")){
                        tvCustomerStatus.setText("รอการยืนยันการซื้อ");
                        tvFileName.setVisibility(View.INVISIBLE);
                        btnUpload.setVisibility(View.INVISIBLE);
                        btnPending.setVisibility(View.INVISIBLE);
                        btnConfirm.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext() , "ยืนยันการซื้อสำเร็จ" , Toast.LENGTH_SHORT).show();

                    }
                    if (pStatus.equals("DONE")){
                        tvCustomerStatus.setText("ซื้อที่ดินสำเร็จ");
                        tvFileName.setVisibility(View.INVISIBLE);
                        btnUpload.setVisibility(View.INVISIBLE);
                        btnPending.setVisibility(View.INVISIBLE);
                        btnConfirm.setVisibility(View.INVISIBLE);
                        btnCancel.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext() , "ยืนยันการซื้อสำเร็จ" , Toast.LENGTH_SHORT).show();
                    }
                    if (pStatus.equals("CANCEL")){
                        tvCustomerStatus.setText("ยกเลิก");
                        tvFileName.setVisibility(View.INVISIBLE);
                        btnUpload.setVisibility(View.INVISIBLE);
                        btnPending.setVisibility(View.INVISIBLE);
                        btnConfirm.setVisibility(View.INVISIBLE);
                        btnCancel.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext() , "ยกเลิกการซื้อแล้ว" , Toast.LENGTH_SHORT).show();


                    }
                }else {
                    Toast.makeText(getApplicationContext() , "เปลี่ยนสถานะไม่สำเร็จ" , Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext() , t.toString() , Toast.LENGTH_LONG).show();


            }
        });
    }
    private void showChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), REQUEST_PICK_FILE);
        } catch (Exception ex) {
            System.out.println("browseClick :" + ex);//android.content.ActivityNotFoundException ex
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
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
                    tvFileName.setText(filename);
                    uploadFile(filePath);

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

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}