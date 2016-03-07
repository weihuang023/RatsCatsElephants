package com.example.huang.ratscatselephants;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

// Setting Menu Preference add Preferences from Resource
public class Prefs extends PreferenceActivity {

    private static final String OPT_MUSIC = "music";
    private static final boolean OPT_MUSIC_DEF = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    public static boolean getMusic(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(OPT_MUSIC,OPT_MUSIC_DEF);
    }
}
