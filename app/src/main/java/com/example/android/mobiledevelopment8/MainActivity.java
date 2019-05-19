package com.example.android.mobiledevelopment8;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button mButonTambah;
    ListView mListView;
    String[] mDaftar;
    SQLHelper mHelper;
    public static MainActivity mMainActivity;
    protected Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButonTambah = findViewById(R.id.button_tambah);
        mButonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BuatBiodataActivity.class);
                startActivity(intent);
            }
        });

        mMainActivity = this;
        mHelper = new SQLHelper(this);
        RefreshList();
    }

    public void RefreshList() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        mCursor = db.rawQuery("SELECT * FROM biodata", null);
        mDaftar = new String[mCursor.getCount()];

        mCursor.moveToFirst();
        for (int i = 0; i < mCursor.getCount(); i++) {
            mCursor.moveToPosition(i);
            mDaftar[i] = mCursor.getString(1);
        }

        mListView = findViewById(R.id.list_view);
        mListView.setAdapter(new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, mDaftar));
        mListView.setSelected(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection = mDaftar[position];
                final CharSequence[] dialogItem = {"Lihat Biodata", "Update Biodata", "Hapus Biodata"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intentLihat = new Intent (getApplicationContext(), LihatBiodataActivity.class);
                                intentLihat.putExtra("nama", selection);
                                startActivity(intentLihat);
                                break;
                            case 1:
                                Intent intentUpdate = new Intent (getApplicationContext(), UpdateBiodataActivity.class);
                                intentUpdate.putExtra("nama", selection);
                                startActivity(intentUpdate);
                                break;
                            case 2:
                                if (mHelper.deleteData(selection)) {
                                    Toast.makeText(MainActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                    RefreshList();
                                } else
                                    Toast.makeText(MainActivity.this, "Data gagal dihapus", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)mListView.getAdapter()).notifyDataSetInvalidated();
    }
}
