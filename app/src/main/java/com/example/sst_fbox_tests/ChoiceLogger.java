package com.example.sst_fbox_tests;
import android.content.Context;
import java.io.FileOutputStream;
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
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            fos.write((timestamp + ": " + choices + "\n").getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}