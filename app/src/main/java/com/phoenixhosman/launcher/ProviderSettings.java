/*
    The Phoenix Hospitality Management System
    Launcher App Source Code
    Settings Content Provider Code File
    Copyright (c) 2020 By Troy Marker Enterprises
    All Rights Under Copyright Reserved

    The code in this file was created for use with the Phoenix Hospitality Management System (PHMS).
    Use of this code outside the PHMS is strictly prohibited.
 */
package com.phoenixhosman.launcher;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.HashMap;
import java.util.Objects;

/**
 * The Settings Content Provider.
 */
public class ProviderSettings extends ContentProvider {

    private static final String PROVIDER_NAME ="com.phoenixhosman.installer.ProviderSettings";
    private static final String URL = "content://" + PROVIDER_NAME +"/settings";
    static final Uri CONTENT_URI = Uri.parse(URL);
    private static final String id = "id";
    static final String coname = "coname";
    static final String coaddress = "coaddress";
    static final String cocity = "cocity";
    static final String costate = "costate";
    static final String cozip = "cozip";
    static final String apiurl = "apiurl";
    static final String lockpass = "lockpass";
    static final String apikey = "apikey";
    private static final int uriCode = 1;
    private static final UriMatcher uriMatcher;
    private static final HashMap<String, String> values = null;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "settings", uriCode);
        uriMatcher.addURI(PROVIDER_NAME, "settings/*", uriCode);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        qb.setStrict(true);
        qb.setProjectionMap(null);
        if (uriMatcher.match(uri) == uriCode) qb.setProjectionMap(values);
        else throw new IllegalArgumentException("Unknown URI " + uri);
        if (sortOrder == null || Objects.equals(sortOrder, "")) sortOrder = id;
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if (uriMatcher.match(uri) == uriCode) return "vnd.android.cursor/settings";
        throw new IllegalArgumentException("Unsupported URI:" + uri);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowID = db.insert(TABLE_NAME,"", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLiteException("Failed to add record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count;
        if (uriMatcher.match(uri) == uriCode) {
            count = db.delete(TABLE_NAME,null, null);
        } else {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "Phoenix";
    private static final String TABLE_NAME = "Settings";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_DB_TABLE = " CREATE TABLE " + TABLE_NAME
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " coname TEXT NOT NULL, "
            + " coaddress TEXT NOT NULL, "
            + " cocity TEXT NOT NULL,"
            + " costate TEXT NOT NULL,"
            + " cozip TEXT NOT NULL,"
            + " apiurl TEXT NOT NULL,"
            + " lockpass TEXT NOT NULL,"
            + " apikey TEXT NOT NULL)";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
