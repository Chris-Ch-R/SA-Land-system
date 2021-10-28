package com.example.systemanalysis.manager.file;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;

public class FileUtils {
    private FileUtils() {
    }

    static final String TAG = "FileUtils";
    private static final boolean DEBUG = false;

    public static final String MIME_TYPE_AUDIO = "audio/*";
    public static final String MIME_TYPE_TEXT = "text/*";
    public static final String MIME_TYPE_IMAGE = "image/*";
    public static final String MIME_TYPE_VIDEO = "video/*";
    public static final String MIME_TYPE_APP = "application/*";

    public static final String HIDDEN_PREFIX = ",";

    public static String getExtension(String uri) {
        if (uri == null) {
            return null;
        }

        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot);
        } else {
            return "";
        }
    }

    public static boolean isLocal(String url) {
        if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
            return true;
        }
        return false;

    }

    public static boolean isMediaUri(Uri uri) {
        return "media".equalsIgnoreCase(uri.getAuthority());
    }

    public static Uri getUri(File file) {
        if (file != null) {
            return Uri.fromFile(file);
        }
        return null;

    }

    public static File getPathWithoutFilename(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                // nofile to be split off. Return everything
                return file;
            } else {
                String filename = file.getName();
                String filepath = file.getAbsolutePath();

                // Construct path without file name.
                String pathwithoutname = filepath.substring(0,
                        filepath.length() - filename.length());
                if (pathwithoutname.endsWith("/")) {
                    pathwithoutname = pathwithoutname.substring(0,
                            pathwithoutname.length() - 1);
                }
                return new File(pathwithoutname);
            }
        }
        return null;
    }

    public static String getMimeType(File file){

        String extension = getExtension(file.getName());

        if (extension.length() > 0)
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));

        return "application/octet-stream";
    }

//    public static String getMimeType(Context context , Uri uri){
//        File file = new File(getPath(context , uri));
//        return getMimeType(file);
//    }

//    public static String getPath(final Context context , final  Uri uri){
//        if (DEBUG)
//            Log.d(TAG + "File -",
//                    "Authority: " + uri.getAuthority() +
//                    ", Frament: " + uri.getFragment() +
//                    ", Port" + uri.getPort() +
//                    ", Query: " + uri.getQuery() +
//                    ", Scheme: " + uri.getScheme() +
//                    ", Host: " + uri.getHost() +
//                    ", Segments: " + uri.getPathSegments().toString()
//            );
//        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//
//        if (isKitKat && DocumentsContract.isDocumentUri(context , uri)){
//
//            // Local Storage Provider
////            if (isLocalStorageDocument(uri)){
////                return DocumentsContract.getDocumentId(uri);
////            }
////            //ExternalStorageProvder
////            else if (isExternalStorageDocument(uri)){
////                final String docId = DocumentsContract.getDocumentId(uri);
////                final String[] split = docId.split(":");
////                final String type = split[0];
////            }
//
//        }
//
//    }


}
