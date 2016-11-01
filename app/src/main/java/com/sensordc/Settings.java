package com.sensordc;

import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;
import java.util.Locale;

public class Settings {
    private final SharedPreferences storedSettings;
    private Setting t1ambient = new Setting("t1ambient", 25.0f);
    private Setting t2ambient = new Setting("t2ambient", 45.0f);
    private Setting v1ambient = new Setting("v1ambient", 596.0f);
    private Setting v2ambient = new Setting("v2ambient", 636.0f);
    private Setting t1battery = new Setting("t1battery", 25.0f);
    private Setting t2battery = new Setting("t2battery", 45.0f);
    private Setting v1battery = new Setting("v1battery", 596.0f);
    private Setting v2battery = new Setting("v2battery", 636.0f);
    private ArrayList<Setting> allSettings = populateAllSettings();

    Settings(SharedPreferences storedSettings) {
        this.storedSettings = storedSettings;
        load();
    }

    // Needed for data binding to view
    @SuppressWarnings("WeakerAccess")
    public Setting getT1ambient() {
        return this.t1ambient;
    }

    // Needed for data binding to view
    @SuppressWarnings("WeakerAccess")
    public Setting getT2ambient() {
        return this.t2ambient;
    }

    // Needed for data binding to view
    @SuppressWarnings("WeakerAccess")
    public Setting getV1ambient() {
        return this.v1ambient;
    }

    // Needed for data binding to view
    @SuppressWarnings("WeakerAccess")
    public Setting getV2ambient() {
        return this.v2ambient;
    }

    // Needed for data binding to view
    @SuppressWarnings("WeakerAccess")
    public Setting getT1battery() {
        return this.t1battery;
    }

    // Needed for data binding to view
    @SuppressWarnings("WeakerAccess")
    public Setting getT2battery() {
        return this.t2battery;
    }

    // Needed for data binding to view
    @SuppressWarnings("WeakerAccess")
    public Setting getV1battery() {
        return this.v1battery;
    }

    // Needed for data binding to view
    @SuppressWarnings("WeakerAccess")
    public Setting getV2battery() {
        return this.v2battery;
    }

    private ArrayList<Setting> populateAllSettings() {
        ArrayList<Setting> allSets = new ArrayList<>();

        allSets.add(this.t1ambient);
        allSets.add(this.t2ambient);
        allSets.add(this.v1ambient);
        allSets.add(this.v2ambient);

        allSets.add(this.t1battery);
        allSets.add(this.t2battery);
        allSets.add(this.v1battery);
        allSets.add(this.v2battery);

        return allSets;
    }

    private void load() {
        for (Setting setting : this.allSettings) {
            setting.load(this.storedSettings);
        }
    }

    void save() {
        SharedPreferences.Editor editor = this.storedSettings.edit();
        for (Setting setting : this.allSettings) {
            setting.save(editor);
        }
        editor.apply();
    }

    // Needed for data binding to view
    @SuppressWarnings("WeakerAccess")
    public class Setting extends BaseObservable {
        private final String settingName;
        private final float defaultValue;
        private float value;

        private Setting(String settingName, float defaultValue) {
            this.settingName = settingName;
            this.defaultValue = defaultValue;
        }

        private void load(SharedPreferences storedSettings) {
            this.setValue(storedSettings.getFloat(this.settingName, this.defaultValue));
        }

        private void save(SharedPreferences.Editor editor) {
            editor.putFloat(this.settingName, getValue());
        }

        float getValue() {
            return this.value;
        }

        private void setValue(float value) {
            this.value = value;
        }

        @Bindable
        // Needed for data binding to view
        public String getValueString() {
            return String.format(Locale.getDefault(), "%.2f", this.value);
        }

        @Bindable
        // Needed for data binding to view
        public void setValueString(String value) {
            this.value = Float.valueOf(value);
        }
    }
}
