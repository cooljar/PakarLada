package unila.rizka.pakarlada.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
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
    @BindView(R.id.llPenangananBudidaya) LinearLayout llPenangananBudidaya;

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

        // get our html content
        Spanned htmlAsSpanned = fromHtml(mPenyakit.deskripsi);
        tvDeskripsi.setText(htmlAsSpanned);

        tvPmek.setText(mPenyakit.penangananmekanis);
        tvPkim.setText(mPenyakit.penanganankimiawi);

        if(mPenyakit.penangananbudidaya.equals("-")){
            llPenangananBudidaya.setVisibility(View.GONE);
        }else{
            tvPbud.setText(mPenyakit.penangananbudidaya);
        }

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

    @SuppressWarnings("deprecation")
    private Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
