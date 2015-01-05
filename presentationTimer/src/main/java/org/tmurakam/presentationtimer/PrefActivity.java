
package org.tmurakam.presentationtimer;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

/**
 * 設定Activity
 */
public class PrefActivity extends PreferenceActivity {
    private Prefs mPrefs;

    private static final String AD_UNIT_ID = "ca-app-pub-4621925249922081/5594984304";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref);

        mPrefs = new Prefs(this);

        PreferenceScreen ps;
        Intent intent;

        for (int i = 1; i <= 3; i++) {
            ps = (PreferenceScreen) findPreference("_" + i + "bell");
            assert (ps != null);
            intent = new Intent(this, TimeSetActivity.class);
            intent.putExtra("kind", i);
            ps.setIntent(intent);
        }

        updateUi();

        addAds();
    }

    private void addAds() {
        // add AdMob
        AdView adView = new AdView(this);
        adView.setAdUnitId(AD_UNIT_ID);
        adView.setAdSize(AdSize.SMART_BANNER);

        LinearLayout.LayoutParams adLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        adView.setLayoutParams(adLayoutParams);

        // 広告表示位置は画面下部
        LinearLayout layout = new LinearLayout(this);
        layout.addView(adView);
        layout.setGravity(Gravity.BOTTOM);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(layout, layoutParams);

        // load ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    private void updateUi() {
        PreferenceScreen ps;

        for (int i = 1; i <= 3; i++) {
            ps = (PreferenceScreen)findPreference("_" + i + "bell");
            assert (ps != null);

            int time = mPrefs.getBellTime(i);
            int hour = time / 3600;
            int min = (time / 60) % 60;
            
            String s = "";
            if (hour > 0) {
                s += hour;
                s += getResources().getString(R.string.hours);
                s += " ";
            }
            s += min;
            s += getResources().getString(R.string.minutes);

            if (i == mPrefs.getCountDownTarget()) {
                s += ", ";
                s += getResources().getString(R.string.end_time);
            }
            ps.setSummary(s);
        }
    }

    // --- Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pref_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.menu_help:
                intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
