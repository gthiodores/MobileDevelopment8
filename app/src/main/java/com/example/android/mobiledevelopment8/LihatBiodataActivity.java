package com.example.android.mobiledevelopment8;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LihatBiodataActivity extends AppCompatActivity {
    private String nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_biodata);

        if (getIntent().hasExtra("nama"))
            nama = getIntent().getStringExtra("nama");

        TextView mTextNomor = findViewById(R.id.text_nomor_2);
        TextView mTextNama = findViewById(R.id.text_nama_2);
        TextView mTextTanggal = findViewById(R.id.text_tanggal_2);
        TextView mTextGender = findViewById(R.id.text_gender_2);
        TextView mTextAlamat = findViewById(R.id.text_alamat_2);
        Button mButtonReturn = findViewById(R.id.button_return);

        SQLHelper mHelper = new SQLHelper(this);

        Cursor mCursor = mHelper.fetchRowData(nama);

        if (mCursor.getCount() > 0) {
            mCursor.moveToPosition(0);
            mTextNomor.setText(mCursor.getString(0));
            mTextNama.setText(mCursor.getString(1));
            mTextTanggal.setText(mCursor.getString(2));
            mTextGender.setText(mCursor.getString(3));
            mTextAlamat.setText(mCursor.getString(4));
        }

        mButtonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
