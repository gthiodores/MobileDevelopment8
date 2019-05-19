package com.example.android.mobiledevelopment8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BuatBiodataActivity extends AppCompatActivity {
    private SQLHelper mHelper;
    private EditText mEditNomor, mEditNama, mEditTanggal, mEditGender, mEditAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_biodata);

        mHelper = new SQLHelper(this);
        mEditNomor = findViewById(R.id.edit_nomor);
        mEditNama = findViewById(R.id.edit_nama);
        mEditTanggal = findViewById(R.id.edit_tgl);
        mEditGender = findViewById(R.id.edit_gender);
        mEditAlamat = findViewById(R.id.edit_alamat);

        Button mButtonSimpan = findViewById(R.id.button_simpan);
        Button mButtonKembali = findViewById(R.id.button_kembali);

        mButtonSimpan.setOnClickListener(new View.OnClickListener() {
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
                    if (mHelper.insertData(nomor, nama, tanggal, gender, alamat)) {
                        Toast.makeText(getApplicationContext(), "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
                        MainActivity.mMainActivity.RefreshList();
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), "Data gagal ditambahkan", Toast.LENGTH_LONG).show();
                }
            }
        });

        mButtonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
