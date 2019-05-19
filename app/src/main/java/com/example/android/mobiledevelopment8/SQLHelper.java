package com.example.android.mobiledevelopment8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "biodata.db";
    private static final int DB_VER = 1;
    private static final String LOG_TAG = SQLHelper.class.getSimpleName();

    SQLHelper (Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE biodata (no integer primary key, nama text null, " +
                     "tgl text null, jk text null, alamat text null);";

        Log.d(LOG_TAG, "onCreate : " + sql);

        db.execSQL(sql);

        sql = "INSERT INTO biodata (no, nama, tgl, jk, alamat) VALUES ('1', 'Ahyar', '1994-04-23', 'LAKI', 'Makassar')";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    Cursor fetchRowData(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM biodata WHERE nama = '" + name +  "'" , null);
    }

    boolean insertData(String nomor, String nama, String tanggal, String gender, String alamat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("no", nomor);
        contentValues.put("nama", nama);
        contentValues.put("tgl", tanggal);
        contentValues.put("jk", gender);
        contentValues.put("alamat", alamat);

        long result = db.insert("biodata", null, contentValues);

        return result != -1;
    }

    boolean updateData(String nomor, String nama, String tanggal, String gender, String alamat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("tgl", tanggal);
        contentValues.put("jk", gender);
        contentValues.put("alamat", alamat);

        long result = db.update("biodata", contentValues, "no = ?", new String[] {nomor});
        return result != -1;
    }

    boolean deleteData(String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("biodata", "nama = ?", new String[] {nama});
        return result != -1;
    }
}
