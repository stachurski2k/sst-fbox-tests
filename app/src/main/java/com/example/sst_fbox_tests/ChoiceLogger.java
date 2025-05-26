package com.example.sst_fbox_tests;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChoiceLogger {
    private static String fileName;

    public static void startNewSession(Context context) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        fileName = "choices_" + timestamp + ".txt";
    }

    public static void appendChoices(Context context, String choices) {
        if (fileName == null) return;
        try {
            OutputStream outputStream;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
                values.put(MediaStore.Downloads.MIME_TYPE, "text/plain");
                values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                outputStream = context.getContentResolver()
                        .openOutputStream(
                                context.getContentResolver()
                                        .insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values),
                                "wa" // "wa" to append mode
                        );
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
