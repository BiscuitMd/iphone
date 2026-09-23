package com.biscuit.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppDrawerActivity extends Activity {

    private GridView gridView;
    private final List<AppInfo> apps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);

        gridView = findViewById(R.id.appGrid);

        loadApps();
    }

    private void loadApps() {

        PackageManager pm = getPackageManager();

        Intent intent = new Intent(
                Intent.ACTION_MAIN,
                null
        );

        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ApplicationInfo> installed =
                pm.getInstalledApplications(
                        PackageManager.GET_META_DATA
                );

        for (ApplicationInfo info : installed) {

            if (pm.getLaunchIntentForPackage(
                    info.packageName) == null) {
                continue;
            }

            String label =
                    pm.getApplicationLabel(info)
                            .toString();

            apps.add(
                    new AppInfo(
                            label,
                            info.packageName,
                            pm.getApplicationIcon(info)
                    )
            );
        }

        Collections.sort(
                apps,
                new Comparator<AppInfo>() {
                    @Override
                    public int compare(
                            AppInfo a,
                            AppInfo b
                    ) {
                        return a.label.compareToIgnoreCase(
                                b.label
                        );
                    }
                }
        );

        gridView.setAdapter(
                new AppAdapter(this, apps)
        );
    }
}
