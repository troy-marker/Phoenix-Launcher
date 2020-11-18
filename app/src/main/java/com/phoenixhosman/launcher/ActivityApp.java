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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phoenixhosman.phoenixapi.ObjectApp;
import com.phoenixhosman.phoenixlib.ActivityPhoenixLib;
import com.phoenixhosman.phoenixlib.ProviderUser;
import java.util.ArrayList;
import java.util.List;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * This activity display links to the other apps in the Phoenix Hospitality
 * system, and use authentication to only show those that the user has
 * permission to use.
 * @author Troy L. Marker
 * @version 1.0.0
 * @since 0.5.0
 */
public class ActivityApp extends Activity implements View.OnClickListener {
    private PackageManager manager;
    private RecyclerView.Adapter ApAdapter;
    private final LinearLayoutManager ApLayoutManager = new LinearLayoutManager(this);
    private final ArrayList<ObjectApp> ApList = new ArrayList<>();
    private int count = 0;
    private String grade;
    private String department;
    final ActivityPhoenixLib Phoenix = new ActivityPhoenixLib();

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
        ApAdapter = new ApAdapter(ApList);
        ApRecyclerView.setHasFixedSize(true);
        ApRecyclerView.setLayoutManager(ApLayoutManager);
        ApRecyclerView.setAdapter(ApAdapter);
        TextView tvLoggedUsername = findViewById(R.id.tvLoggedUsername);
        TextView tvAccessGrade = findViewById(R.id.tvAccessGrade);
        TextView tvDepartment = findViewById(R.id.tvDepartment);
        Button btnLogout = findViewById(R.id.btLogoff);
        btnLogout.setOnClickListener(this);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        grade = intent.getStringExtra("grade");
        String gradename = intent.getStringExtra("gradename");
        department = intent.getStringExtra("department");
        String departmentname = intent.getStringExtra("departmentname");
        tvLoggedUsername.setText(getString(R.string.current_user, username));
        tvAccessGrade.setText(getString(R.string.current_grade, gradename));
        tvDepartment.setText(getString(R.string.current_department, departmentname));
        loadApps();

    }

    /**
     * This method reads all the apps on the device, and displays only those
     * apps that are part of the Phoenix Hospitality System, with the
     * exception of Phoenix Install and Phoenix Launcher.
     */
    private void loadApps(){
        manager = getPackageManager();
        ApList.clear();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for(ResolveInfo ri:availableActivities){
            String temp = (String) ri.loadLabel(manager);
            if (temp.contains("Phoenix")) {
                if (!temp.contains("Phoenix Launcher") && !temp.contains("Phoenix Install")) {
                    ApList.add(new ObjectApp(
                            ri.loadLabel(manager),
                            ri.activityInfo.packageName
                    ));
                }
            }
        }
        ApAdapter.notifyDataSetChanged();
    }

    /**
     * This is the adapter for the App recycler view.
     */
    public class ApAdapter extends RecyclerView.Adapter<ApAdapter.ApViewHolder> {
        private final ArrayList<ObjectApp> mApList;

        class ApViewHolder extends ViewHolder {

            final Button mApBtn;

            ApViewHolder(View ApView) {
                super(ApView);
                mApBtn = ApView.findViewById(R.id.ApBtn);
            }
        }

        ApAdapter(ArrayList<ObjectApp> ApList) {
            mApList = ApList;
        }
        @NonNull
        @Override
        public ApViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_app, parent, false);
            return new ApViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ApViewHolder holder, final int pos) {
            ObjectApp currentAp = mApList.get(pos);
            holder.mApBtn.setText(currentAp.getLabel());
            switch (count) {
                case 0:
                    holder.mApBtn.setBackgroundResource(R.drawable.x_green_button);
                    count++;
                    break;
                case 1:
                    holder.mApBtn.setBackgroundResource(R.drawable.x_yellow_button);
                    count++;
                    break;
                case 2:
                    holder.mApBtn.setBackgroundResource(R.drawable.x_red_button);
                    count++;
                    break;
                case 3:
                    holder.mApBtn.setBackgroundResource(R.drawable.x_blue_button);
                    count = 0;
                    break;
                default:
            }
            holder.mApBtn.setOnClickListener(view -> {
                Intent intent = manager.getLaunchIntentForPackage(ApList.get(pos).getName().toString());
                assert intent != null;
                intent.putExtra("grade", grade);
                intent.putExtra("department", department);
                startActivity(intent);
            });
        }
        @Override
        public int getItemCount() {
            return mApList.size();
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
