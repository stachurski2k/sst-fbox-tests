package com.example.sst_fbox_tests;
import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileWriter;
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
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!downloadsDir.exists()) downloadsDir.mkdirs();
            File file = new File(downloadsDir, fileName);

            FileWriter writer = new FileWriter(file, true);
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            writer.append(timestamp).append(";").append(choices).append("\n");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}