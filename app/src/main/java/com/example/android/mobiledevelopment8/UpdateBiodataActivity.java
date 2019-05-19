package com.example.android.mobiledevelopment8;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBiodataActivity extends AppCompatActivity {
    EditText mEditNomor, mEditNama, mEditAlamat, mEditGender, mEditTanggal;
    private String nama;
    private SQLHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_biodata);

        Button buttonUpdate, buttonKembali;

        mEditNama = findViewById(R.id.edit_nama_update);
        mEditNomor = findViewById(R.id.edit_nomor_update);
        mEditGender = findViewById(R.id.edit_gender_update);
        mEditAlamat = findViewById(R.id.edit_alamat_update);
        mEditTanggal = findViewById(R.id.edit_tgl_update);

        buttonUpdate = findViewById(R.id.button_update);
        buttonKembali = findViewById(R.id.button_kembali_update);

        if (getIntent().hasExtra("nama"))
            nama = getIntent().getStringExtra("nama");

        mHelper = new SQLHelper(this);
        Cursor mCursor = mHelper.fetchRowData(nama);

        if (mCursor.getCount() > 0) {
            mCursor.moveToPosition(0);
            mEditNomor.setText(mCursor.getString(0));
            mEditNama.setText(mCursor.getString(1));
            mEditTanggal.setText(mCursor.getString(2));
            mEditGender.setText(mCursor.getString(3));
            mEditAlamat.setText(mCursor.getString(4));
        }

        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomor, nama, tanggal, gender, alamat;

                nomor = mEditNomor.getText().toString();
                nama = mEditNama.getText().toString();
                tanggal = mEditTanggal.getText().toString();
                gender = mEditGender.getText().toString();
                alamat = mEditAlamat.getText().toString();

                if (nomor.equals("") || nama.equals("") || tanggal.equals("") || gender.equals("") || alamat.equals(""))
                    Toast.makeText(getApplicationContext(), "Masih ada field yang kosong", Toast.LENGTH_LONG).show();
                else {
                    boolean isUpdated = mHelper.updateData(nomor, nama, tanggal, gender, alamat);

                    if (isUpdated)
                        Toast.makeText(getApplicationContext(), "Data berhasil diupdate", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Data gagal diupdate", Toast.LENGTH_LONG).show();

                    MainActivity.mMainActivity.RefreshList();
                    finish();
                }
            }
        });
    }
}
