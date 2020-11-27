/*
    The Phoenix Hospitality Management System
    Launcher App Source Code
    App List Code File
    Copyright (c) 2020 By Troy Marker Enterprises
    All Rights Under Copyright Reserved

    The code in this file was created for use with the Phoenix Hospitality Management System (PHMS).
    Use of this code outside the PHMS is strictly prohibited.
 */
package com.phoenixhosman.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.phoenixhosman.phoenixapi.ObjectApp;
import com.phoenixhosman.phoenixlib.ActivityPhoenixLib;
import com.phoenixhosman.phoenixlib.ProviderUser;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/**
 * This activity display links to the other apps in the Phoenix Hospitality
 * system, and use authentication to only show those that the user has
 * permission to use.
 * @author Troy L. Marker
 * @version 1.0.0
 * @since 0.5.0
 */
public class ActivityApp extends Activity implements View.OnClickListener {
    private final LinearLayoutManager ApLayoutManager = new LinearLayoutManager(this);
    final ActivityPhoenixLib Phoenix = new ActivityPhoenixLib();
    private static String grade = "";
    private static String gradename = "";
    private static String department = "";
    private static String departmentname = "";

    /**
     * This method will create the activity, read content to display, and show the main activity screen
     * Used when the activity is (re)created.
     * @param savedInstanceState current saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_apps);
        RecyclerView ApRecyclerView = findViewById(R.id.rvApps);
        RecyclerView.Adapter appAdapter = new AppAdapter(this);
        ApRecyclerView.setHasFixedSize(true);
        ApRecyclerView.setLayoutManager(ApLayoutManager);
        ApRecyclerView.setAdapter(appAdapter);
        TextView tvLoggedUsername = findViewById(R.id.tvLoggedUsername);
        TextView tvAccessGrade = findViewById(R.id.tvAccessGrade);
        TextView tvDepartment = findViewById(R.id.tvDepartment);
        Button btnLogout = findViewById(R.id.btLogoff);
        btnLogout.setOnClickListener(this);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        grade = intent.getStringExtra("grade");
        gradename = intent.getStringExtra("gradename");
        department = intent.getStringExtra("department");
        departmentname = intent.getStringExtra("departmentname");
        tvLoggedUsername.setText(getString(R.string.current_user, username));
        tvAccessGrade.setText(getString(R.string.current_grade, gradename));
        tvDepartment.setText(getString(R.string.current_department, departmentname));
    }

    /**
     * This is the adapter for the App recycler view.
     */
    public static class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
        private final ArrayList<ObjectApp> mAppList;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView AppTitle;
            public ImageView AppIcon;

            public ViewHolder(View itemView) {
                super(itemView);
                AppTitle = itemView.findViewById(R.id.AppTitle);
                AppIcon = itemView.findViewById(R.id.AppIcon);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick (View v) {
                int pos = getAdapterPosition();
                Context context = v.getContext();
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(mAppList.get(pos).getName().toString());
                assert launchIntent != null;
                launchIntent.putExtra("grade", grade);
                launchIntent.putExtra("gradename", gradename);
                launchIntent.putExtra("department", department);
                launchIntent.putExtra("departmentname", departmentname);
                context.startActivity(launchIntent);
            }
        }

        public AppAdapter(Context c) {
            PackageManager pm = c.getPackageManager();
            mAppList = new ArrayList<>();
            Intent i = new Intent(Intent.ACTION_MAIN, null);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
            for(ResolveInfo ri:allApps) {
                String temp = (String) ri.loadLabel(pm);
                if (temp.contains("Phoenix")) {
                    if (!temp.contains("Phoenix Launcher") && !temp.contains("Phoenix Install")) {
                        mAppList.add(new ObjectApp(
                                ri.loadLabel(pm),
                                ri.activityInfo.packageName,
                                ri.activityInfo.loadIcon(pm)));
                    }
                }
            }
        }

        @Override
        public void onBindViewHolder(AppAdapter.ViewHolder viewHolder, int i) {
            String appLabel = mAppList.get(i).getLabel().toString();
            Drawable appIcon = mAppList.get(i).getIcon();
            TextView textView = viewHolder.AppTitle;
            textView.setText(appLabel);
            ImageView imageView = viewHolder.AppIcon;
            imageView.setImageDrawable(appIcon);
        }

        @Override
        public int getItemCount() {
            return mAppList.size();
        }

        @Override
        public AppAdapter.@NotNull ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.list_app, parent, false);
            AtomicReference<ViewHolder> viewHolder;
            viewHolder = new AtomicReference<>(new ViewHolder(view));
            return viewHolder.get();
        }
    }

    /**
     * Override of the onBackPress method from the parent class
     * This disable th back button on the device.
     */
    @Override
    public void onBackPressed() {
        Phoenix.Error(ActivityApp.this, getString(R.string.disabled, getString(R.string.back)),false);
    }

    /**
     * This method will log the current user off and return to the home activity
     */
    private void Logoff() {
        getContentResolver().delete(ProviderUser.CONTENT_URI,null,null);
        Intent intent = new Intent(this.getApplicationContext(), ActivityHome.class);
        startActivity(intent);
    }

    /**
     * This method overrides the parents click listner.
     * @param view the view clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btLogoff) {
            Logoff();
        }
    }
}
