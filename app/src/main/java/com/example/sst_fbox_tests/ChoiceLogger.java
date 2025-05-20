package com.example.sst_fbox_tests;
import android.content.Context;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChoiceLogger {
    private static final String FILE_NAME = "choices_data.txt";

    public static void startNewSession(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND);
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            String line = "\n--- Nowa sesja: " + timestamp + " ---\n";
            fos.write(line.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void appendChoices(Context context, String choices) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND);
            fos.write((choices + "\n").getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
