package com.example.sst_fbox_tests;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChoiceLogger {
    private static String fileName;
    private static Uri fileUri; // for API >= 29

    public static void startNewSession(Context context) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        fileName = "choices_" + timestamp + ".txt";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
                values.put(MediaStore.Downloads.MIME_TYPE, "text/plain");
                values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                ContentResolver resolver = context.getContentResolver();
                Uri uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri != null) {
                    fileUri = uri;

                    // Create empty file
                    OutputStream os = resolver.openOutputStream(uri, "w");
                    if (os != null) {
                        os.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // On older versions, we can just create the file
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                FileOutputStream fos = new FileOutputStream(file, false);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void appendChoices(Context context, String choices) {
        if (fileName == null) return;

        try {
            OutputStream outputStream;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (fileUri == null) return; // file must be created first in startNewSession
                outputStream = context.getContentResolver().openOutputStream(fileUri, "wa");
            } else {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                outputStream = new FileOutputStream(file, true);
            }

            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            outputStream.write((timestamp + ": " + choices + "\n").getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}