package unila.rizka.pakarlada.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import unila.rizka.pakarlada.R;
import unila.rizka.pakarlada.model.Gejala;
import unila.rizka.pakarlada.model.Penyakit;
import unila.rizka.pakarlada.model.PenyakitGejala;

public class DetailPenyakitActivity extends AppCompatActivity {
    public static final String PENYAKIT_PARCEL = "penyakit_parcel";

    private Penyakit mPenyakit;

    @BindView(R.id.RlContainer) RelativeLayout RlContainer;
    @BindView(R.id.tvNamaPenyakit) TextView tvNamaPenyakit;
    @BindView(R.id.tvDeskripsi) TextView tvDeskripsi;
    @BindView(R.id.tvPmek) TextView tvPmek;
    @BindView(R.id.tvPkim) TextView tvPkim;
    @BindView(R.id.tvPbud) TextView tvPbud;
    @BindView(R.id.llGejala) LinearLayout llGejala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyakit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Penjelasan Penyakit");

        ButterKnife.bind(this);

        mPenyakit = Parcels.unwrap(getIntent().getParcelableExtra(PENYAKIT_PARCEL));

        getSupportActionBar().setSubtitle(mPenyakit.nama);

        tvNamaPenyakit.setText(mPenyakit.nama);
        tvDeskripsi.setText(mPenyakit.deskripsi);
        tvPmek.setText(mPenyakit.penangananmekanis);
        tvPkim.setText(mPenyakit.penanganankimiawi);
        tvPbud.setText(mPenyakit.penangananbudidaya);

        for(PenyakitGejala pg : mPenyakit.getPenyakitGejala()){
            Gejala gejala = pg.getGejala();

            TextView gejalaTv = new TextView(DetailPenyakitActivity.this);
            gejalaTv.setText(gejala.gejala);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 10);

            gejalaTv.setLayoutParams(params);
            llGejala.addView(gejalaTv);
        }
    }

}
