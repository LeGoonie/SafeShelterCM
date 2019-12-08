package com.example.safeshelter;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    private FirebaseAuth mAuth;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        mAuth = FirebaseAuth.getInstance();

        Preference logout_pref = findPreference("Log_Out");
        logout_pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), HomeActivity.class));
                return true;
            }
        });
    }
}