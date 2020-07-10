/*
    The Phoenix Hospitality Management System
    Launcher App Source Code
    Main Activity Code File
    Copyright (c) 2020 By Troy Marker Enterprises
    All Rights Under Copyright Reserved

    The code in this file was created for use with the Phoenix Hospitality Management System (PHMS).
    Use of this code outside the PHMS is strictly prohibited.
 */
package com.phoenixhosman.launcher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.atomic.AtomicInteger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.view.View.inflate;

/**
 * This activity display links to the other apps in the Phoenix Hospitality
 * system, and use authentication to only show those that the user has
 * permission to use.
 * @author Troy L. Marker
 * @version 1.0.0
 * @since 0.5.0
 */
public class ActivityHome extends Activity implements View.OnClickListener
{
    private String strCoName;
    private String strApiUrl;
    private String strLockPass;
    private EditText etUsername;
    private EditText etPassword;
    final private static int REQUEST_CODE_1 = 1;

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
        setContentView(R.layout.activity_home);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        TextView tvWarning = findViewById(R.id.tvWarning);
        Button btnLogon = findViewById(R.id.btnLogon);
        Button btnLockPass = findViewById(R.id.btnLockPass);
        btnLogon.setOnClickListener(this);
        btnLockPass.setOnClickListener(this);
        @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(Uri.parse("content://com.phoenixhosman.installer.ProviderSettings/settings"), null, null, null, null, null);
        assert cursor != null;
        if(cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                strCoName = cursor.getString(cursor.getColumnIndex("coname"));
                strApiUrl = cursor.getString(cursor.getColumnIndex("apiurl"));
                strLockPass = cursor.getString(cursor.getColumnIndex("lockpass"));
                cursor.moveToNext();
            }
            new ManagerSecurityApi(strApiUrl);
            tvWarning.setText(getString(R.string.warning, rtrim(strCoName)));
        } else {
            Error("\nRequired setting missing.\nPlease (re)run Phoenix Install App");
        }

    }

    /**
     * Override of the onBackPress method from the parent class
     * This disables the back button on the device.
     */
    @Override
    public void onBackPressed() {
        Error("\nFunction Disbled.\nThe back button has been disabled.");
    }

    /**
     * The error display method
     * This method displays a dialog box with an error message and a close button.
     * @param strError the error message to display
     */
    @SuppressWarnings ("SameParameterValue")
    private void Error(String strError) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View view = inflate(this, R.layout.dialog_error, null);
        Button btnExit = view.findViewById(R.id.buttonExitButton);
        Button btnError = view.findViewById(R.id.btnErrorMessage);
        btnError.setText(getString(R.string.error, strError ));
        mBuilder.setView(view);
        AlertDialog dialog = mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        btnExit.setOnClickListener(v -> dialog.dismiss());

    }

    /**
     * Method to display the lock password dialog
     * @param strPrompt - the dialog prompt
     */
    @SuppressWarnings ("SameParameterValue")
    private void InputDialog(String strPrompt) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View view = inflate(this, R.layout.dialog_input, null);
        TextView txtPrompt = view.findViewById(R.id.txtPrompt);
        EditText edtLockPass = view.findViewById(R.id.edtLockPass);
        Button btnEnter = view.findViewById(R.id.btnEnter);
        txtPrompt.setText(getString(R.string.input_prompt, strPrompt));
        mBuilder.setView(view);
        AlertDialog dialog = mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        btnEnter.setOnClickListener(v -> {
            if (edtLockPass.getText().toString().equals(strLockPass)) {
                dialog.dismiss();
                finishAffinity();
            } else {
                dialog.dismiss();
            }
        });
    }

    /**
     * This method overrides the parents click listner.
     * @param view the view clicked.
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnLogon:
                if (etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                    Error("\nBoth the username and password are required.");
                } else {
                    Call<String> call = ManagerSecurityApi.getInstance().getApi().login(etUsername.getText().toString(), etPassword.getText().toString());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            String body = response.body();
                                try {
                                    assert body != null;
                                    JSONObject obj = new JSONObject(body);
                                    if(obj.optString("success").equals("1")) {
                                        etUsername.setText("");
                                        etPassword.setText("");
                                        etUsername.requestFocus();
                                        getContentResolver().delete(ProviderUser.CONTENT_URI, null, null);
                                        ContentValues values = new ContentValues();
                                        values.put(ProviderUser.name, obj.optString("username"));
                                        values.put(ProviderUser.grade, obj.optInt("grade"));
                                        values.put(ProviderUser.gradename, obj.optString("gradename"));
                                        values.put(ProviderUser.department, obj.optInt("department"));
                                        values.put(ProviderUser.departmentname, obj.optString("departmentname"));
                                        getContentResolver().insert(ProviderUser.CONTENT_URI, values);
                                        Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_LONG).show();
                                        showApps(obj.optString("username"), obj.optInt("grade"), obj.optString("gradename"), obj.optInt("department"), obj.optString("departmentname"));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "User not authorized.", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                        }
                    });
                }
                break;
            case R.id.btnLockPass:
                InputDialog("Enter Lock Password:");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    /**
     * This method will call the App Display Activity
     */
    private void showApps(String username, Integer grade, String gradename, Integer department, String departmentname){
        Intent intent = new Intent(ActivityHome.this.getApplicationContext(), ActivityApp.class);
        intent.putExtra("username", username);
        intent.putExtra("grade", String.valueOf(grade));
        intent.putExtra("gradename", gradename);
        intent.putExtra("department", String.valueOf(department));
        intent.putExtra("departmentname", departmentname);
        startActivityForResult(intent, REQUEST_CODE_1);
    }

    /**
     * Function to trim whitespace from the end of a string.
     * @param s the string to trim
     * @return the trimmed string
     */
    public static String rtrim(String s) {
        AtomicInteger i;
        i = new AtomicInteger(s.length() - 1);
        while (i.get() >= 0 && Character.isWhitespace(s.charAt(i.get()))) {
            i.getAndDecrement();
        }
        return s.substring(0, i.get() +1);
    }
}