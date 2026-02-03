package com.example.ass1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ass1.controller.SharedPreferenceHelper;
import com.example.ass1.model.Settings;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {

    private SharedPreferenceHelper helper;

    private TextView txtC1, txtC2, txtC3, txtTotal;
    private ListView listHistory;

    private boolean showNames = true; // default mode: event names

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        helper = new SharedPreferenceHelper(this);

        txtC1 = findViewById(R.id.txtCount1);
        txtC2 = findViewById(R.id.txtCount2);
        txtC3 = findViewById(R.id.txtCount3);
        txtTotal = findViewById(R.id.txtTotalData);
        listHistory = findViewById(R.id.listHistory);

        refreshUI();
    }

    private void refreshUI() {
        Settings s = helper.loadSettings();

        int c1 = helper.getCount1();
        int c2 = helper.getCount2();
        int c3 = helper.getCount3();
        int total = helper.getTotal();

        // Labels depend on mode
        String label1 = showNames ? s.getButton1Name() : "1";
        String label2 = showNames ? s.getButton2Name() : "2";
        String label3 = showNames ? s.getButton3Name() : "3";

        txtC1.setText(label1 + ": " + c1);
        txtC2.setText(label2 + ": " + c2);
        txtC3.setText(label3 + ": " + c3);
        txtTotal.setText("Total: " + total);

        List<String> historyLines = historyAsLines(showNames, s);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, historyLines
        );
        listHistory.setAdapter(adapter);
    }

    private List<String> historyAsLines(boolean namesMode, Settings s) {
        String csv = helper.getHistoryCsv();
        List<String> out = new ArrayList<>();
        if (csv == null || csv.isBlank()) return out;

        String[] parts = csv.split(",");
        for (String p : parts) {
            p = p.trim();
            if (p.equals("1")) out.add(namesMode ? s.getButton1Name() : "1");
            else if (p.equals("2")) out.add(namesMode ? s.getButton2Name() : "2");
            else if (p.equals("3")) out.add(namesMode ? s.getButton3Name() : "3");
        }
        return out;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_toggle_mode) {
            showNames = !showNames;
            refreshUI();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
