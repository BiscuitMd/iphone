package com.biscuit.launcher;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppAdapter extends BaseAdapter {

    private final Context context;
    private final List<AppInfo> apps;

    public AppAdapter(
            Context context,
            List<AppInfo> apps
    ) {
        this.context = context;
        this.apps = apps;
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public Object getItem(int position) {
        return apps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent
    ) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_app, parent, false);
        }

        ImageView icon =
                convertView.findViewById(R.id.appIcon);

        TextView name =
                convertView.findViewById(R.id.appName);

        final AppInfo app = apps.get(position);

        icon.setImageDrawable(app.icon);
        name.setText(app.label);

        convertView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent launch =
                                context.getPackageManager()
                                        .getLaunchIntentForPackage(
                                                app.packageName
                                        );

                        if (launch != null) {
                            launch.addFlags(
                                    Intent.FLAG_ACTIVITY_NEW_TASK
                            );

                            context.startActivity(launch);
                        }
                    }
                }
        );

        return convertView;
    }
}
