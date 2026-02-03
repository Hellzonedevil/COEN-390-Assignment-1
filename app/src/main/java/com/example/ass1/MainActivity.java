package com.example.ass1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ass1.controller.SharedPreferenceHelper;
import com.example.ass1.model.Settings;

public class MainActivity extends AppCompatActivity {

    private SharedPreferenceHelper helper;

    private TextView txtTotal;
    private Button btn1, btn2, btn3, btnData, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new SharedPreferenceHelper(this);

        txtTotal = findViewById(R.id.txtTotal);
        btn1 = findViewById(R.id.btnEvent1);
        btn2 = findViewById(R.id.btnEvent2);
        btn3 = findViewById(R.id.btnEvent3);
        btnData = findViewById(R.id.btnData);
        btnSettings = findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        btnData.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, DataActivity.class)));

        btn1.setOnClickListener(v -> handleEventPress(1));
        btn2.setOnClickListener(v -> handleEventPress(2));
        btn3.setOnClickListener(v -> handleEventPress(3));
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!helper.hasSettings()) {
            startActivity(new Intent(this, SettingsActivity.class));
            return;
        }

        Settings s = helper.loadSettings();
        btn1.setText(s.getButton1Name());
        btn2.setText(s.getButton2Name());
        btn3.setText(s.getButton3Name());

        txtTotal.setText("Total: " + helper.getTotal());
    }

    private void handleEventPress(int whichButton) {
        if (!helper.hasSettings()) {
            startActivity(new Intent(this, SettingsActivity.class));
            return;
        }

        Settings s = helper.loadSettings();
        int max = s.getMaxEvents();

        int total = helper.getTotal();
        if (total >= max) {
            Toast.makeText(this, "Max events reached (" + max + ")", Toast.LENGTH_SHORT).show();
            return;
        }

        int c1 = helper.getCount1();
        int c2 = helper.getCount2();
        int c3 = helper.getCount3();

        if (whichButton == 1) c1++;
        else if (whichButton == 2) c2++;
        else c3++;

        total++;

        helper.saveCounts(c1, c2, c3, total);

        String csv = helper.getHistoryCsv();
        csv = csv.isEmpty() ? String.valueOf(whichButton) : (csv + "," + whichButton);
        helper.saveHistoryCsv(csv);

        txtTotal.setText("Total: " + total);
    }
}
