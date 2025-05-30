package com.example.sst_fbox_tests;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StatsActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> filePickerLauncher;
    class WallInfo{
        int wallIdx;
        int ballmissing=0;
        int ballmoved=0;
        int ballok=0;
        public WallInfo(int idx){
            wallIdx=idx;
        }
        public int Total(){
            return ballmoved+ballok+ballmissing;
        }
        public float GetAccMissing(){
            if(Total()==0)return 0;
            return (1.0f-(float)ballmissing/Total())*100f;
        }
        public float GetAccMissingMoved(){
            if(Total()==0)return 0;
            return (1.0f-(float)(ballmissing+ballmoved)/Total())*100.0f;
        }
        public void AddLog(String option) {
            if(option.equals("o")){
                ballok+=1;
            }
            if(option.equals("b")){
                ballmissing+=1;
            }
            if(option.equals("p")){
                ballmoved+=1;
            }
        }
        public String PrintInfo(){
            return String.format("Sciana %d \n\t\t %d - Suma\n\t\t %.2f%% - Braki\n\t\t %.2f%% - Przesuniecia\n",wallIdx,Total(),GetAccMissing(),GetAccMissingMoved());
        }
    };
    class ThrowerInfo{
        String throwerName;
        public int noShotCount=0;
        public int awakingCount=0;
        public int errorCount=0;
        public ThrowerInfo(String name){
            throwerName=name;
        }
        public String PrintInfo(){
            return String.format("Wyrzutnik %s \n\t\t %d - Wzbudzenia\n\t\t %d - Braki wystrzału\n\t\t %d - Inne błędy\n",throwerName,awakingCount,noShotCount,errorCount);
        }
        public void AddLog(String log){
            if(log.equals("0")){
                awakingCount+=1;
            }
            if(log.equals("1")){
                noShotCount+=1;
            }
            if(log.equals("2")){
                errorCount+=1;
            }
        }
    }
    void ProcessFile(String file){
        WallInfo[] walls = new WallInfo[4];
        Map<String,ThrowerInfo> throwers=new HashMap<>();
        throwers.put("a",new ThrowerInfo("Dolny A"));
        throwers.put("b",new ThrowerInfo("Dolny B"));
        throwers.put("c",new ThrowerInfo("Dolny C"));
        throwers.put("d",new ThrowerInfo("Dolny D"));
        throwers.put("bg",new ThrowerInfo("Górny B"));
        throwers.put("cg",new ThrowerInfo("Górny C"));

        for(int i =0;i<4;i++){
            walls[i]=new WallInfo(i+1);
        }

        String[] lines = file.split("\\r?\\n");
        for (String line : lines) {
            String[] info =line.split(";");
            if(info.length<3){continue;}
            try{
                int wallIdx= Integer.parseInt(info[1])-1;
                walls[wallIdx].AddLog(info[2]);
            }catch(Exception e){
                if(throwers.containsKey(info[1])){
                    throwers.get(info[1]).AddLog(info[2]);
                }
            }

        }
        TextView statsTextView = findViewById(R.id.statsText);
        String stats="";
        for(WallInfo w:walls){
            stats+=w.PrintInfo();
        }
        for(ThrowerInfo i:throwers.values()){
            stats+=i.PrintInfo();
        }
        statsTextView.setText(stats.toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats  );
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri uri = data.getData();
                            String content = readTextFromUri(uri);
                            ProcessFile(content);
                        }
                    }
                }
        );
        openFilePicker();
        Button returnButton = findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(v->{
            proceed();
        });
    }
    private String readTextFromUri(Uri uri) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        filePickerLauncher.launch(intent);
    }
    private void proceed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
