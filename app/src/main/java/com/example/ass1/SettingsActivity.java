package com.example.ass1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ass1.controller.SharedPreferenceHelper;
import com.example.ass1.model.Settings;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferenceHelper helper;

    private EditText edt1, edt2, edt3, edtMax;
    private Button btnSave;

    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        helper = new SharedPreferenceHelper(this);

        edt1 = findViewById(R.id.edtName1);
        edt2 = findViewById(R.id.edtName2);
        edt3 = findViewById(R.id.edtName3);
        edtMax = findViewById(R.id.edtMaxEvents);
        btnSave = findViewById(R.id.btnSaveSettings);

        btnSave.setOnClickListener(v -> saveClicked());

        loadIntoFields();

        // If no settings exist yet, force edit mode
        if (!helper.hasSettings()) {
            setEditMode(true);
        } else {
            setEditMode(false);
        }
    }

    private void loadIntoFields() {
        Settings s = helper.loadSettings();
        edt1.setText(s.getButton1Name());
        edt2.setText(s.getButton2Name());
        edt3.setText(s.getButton3Name());
        edtMax.setText(s.getMaxEvents() == 0 ? "" : String.valueOf(s.getMaxEvents()));
    }

    private void setEditMode(boolean on) {
        editMode = on;

        edt1.setEnabled(on);
        edt2.setEnabled(on);
        edt3.setEnabled(on);
        edtMax.setEnabled(on);

        btnSave.setVisibility(on ? View.VISIBLE : View.GONE);
        invalidateOptionsMenu(); // refresh action bar menu
    }

    private void saveClicked() {
        String n1 = edt1.getText().toString().trim();
        String n2 = edt2.getText().toString().trim();
        String n3 = edt3.getText().toString().trim();
        String maxStr = edtMax.getText().toString().trim();

        if (!isValidName(n1) || !isValidName(n2) || !isValidName(n3)) {
            Toast.makeText(this, "Names: letters/spaces only, max 20 chars.", Toast.LENGTH_SHORT).show();
            return;
        }

        int max;
        try {
            max = Integer.parseInt(maxStr);
        } catch (Exception e) {
            Toast.makeText(this, "Max events must be a number (5 to 200).", Toast.LENGTH_SHORT).show();
            return;
        }

        if (max < 5 || max > 200) {
            Toast.makeText(this, "Max events must be between 5 and 200.", Toast.LENGTH_SHORT).show();
            return;
        }

        Settings s = new Settings(n1, n2, n3, max);
        helper.saveSettings(s);

        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        setEditMode(false);
    }

    private boolean isValidName(String s) {
        if (s == null) return false;
        if (s.length() == 0 || s.length() > 20) return false;
        return s.matches("[A-Za-z ]+");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Hide "Edit" while already in edit mode
        MenuItem editItem = menu.findItem(R.id.action_edit);
        if (editItem != null) {
            editItem.setVisible(!editMode);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            setEditMode(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
