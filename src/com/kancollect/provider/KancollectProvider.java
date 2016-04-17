package com.kancollect.provider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;


import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.media.MediaMuxer.OutputFormat;
import android.net.Uri;
import android.util.Log;

public class KancollectProvider extends ContentProvider {

    public static final String DATABASE_NAME = "kancollect.db";
    public static final String DATABASE_FOLDER = "databases";
    public static final int DATABASE_VERSION = 1;
    private DatabaseHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mOpenHelper = getDatabaseHelper(context);
        return true;
    }

    private DatabaseHelper getDatabaseHelper(Context context) {
        synchronized (this) {
            if (mOpenHelper == null) {
                mOpenHelper = new DatabaseHelper(context);
            }
            return mOpenHelper;
        }
    }

    final class DatabaseHelper extends SQLiteOpenHelper {

        @SuppressLint("NewApi")
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            setWriteAheadLoggingEnabled(true);
            checkDabasExsits();
        }

        @Override
        public SQLiteDatabase getReadableDatabase() {
            checkDabasExsits();
            return super.getReadableDatabase();
        }

        @Override
        public SQLiteDatabase getWritableDatabase() {
            checkDabasExsits();
            return super.getWritableDatabase();
        }

        private void checkDabasExsits(){
            File dbFile = getContext().getDatabasePath(DATABASE_NAME);
            if (!dbFile.exists()){
                try {
                    copyDatabaseFile(dbFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables("shiplines");
        String limit = null;
        String groupBy = null;
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, groupBy,
                null, sortOrder, limit);
        return cursor;
    }

    public void copyDatabaseFile(File file) throws IOException {
        File parFile = file.getParentFile();
        if (!parFile.exists()){
            parFile.mkdirs();
        }
        String[] files = getContext().getAssets().list(DATABASE_FOLDER);
        InputStream inputStream;
        OutputStream out = new FileOutputStream(file);
        for (int i = 0; i < files.length; i++) {
            inputStream = getContext().getAssets().open(DATABASE_FOLDER + File.separator + files[i]);
            byte[] buffer = new byte[1024];
            while(inputStream.read(buffer)>0){
                out.write(buffer);
            }
            out.flush();
            inputStream.close();
        }
        out.close();
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

}
