package unila.rizka.pakarlada.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import unila.rizka.pakarlada.R;
import unila.rizka.pakarlada.model.Penyakit;
import unila.rizka.pakarlada.model.PohonKeputusan;
import unila.rizka.pakarlada.util.FitXyTransformation;

public class KonsultasiActivity extends AppCompatActivity {

    int layoutWidth, layoutHeight, phkIndex, phkSize;
    int penyakitId = 0;
    boolean topLevel = true;
    List<PohonKeputusan> mPohonKeputusanDefault;
    List<PohonKeputusan> mPohonKeputusan;
    PohonKeputusan currentPhk;

    @BindView(R.id.tvPertanyaan) TextView tvPertanyaan;
    @BindView(R.id.btYa) TextView btYa;
    @BindView(R.id.btTidak) TextView btTidak;
    @BindView(R.id.ivMaskot) ImageView ivMaskot;
    @BindView(R.id.llContainer) LinearLayout llContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsultasi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Konsultasi");

        ButterKnife.bind(this);

        String pohonKeputusanJson = loadJSONFromAsset();
        Gson gson = new Gson();
        Type list = new TypeToken<List<PohonKeputusan>>(){}.getType();
        mPohonKeputusanDefault = gson.fromJson(pohonKeputusanJson, list);
        mPohonKeputusan = mPohonKeputusanDefault;

        phkSize = mPohonKeputusan.size();
        phkIndex = 0;
        currentPhk = mPohonKeputusan.get(phkIndex);
        tvPertanyaan.setText(currentPhk.gejala + " (" +currentPhk.kode + ")");

        ViewTreeObserver vto = llContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                layoutWidth = llContainer.getWidth();
                layoutHeight = llContainer.getHeight();

                Picasso.with(KonsultasiActivity.this)
                        .load(R.drawable.ic_diagnosa)
                        .transform(new FitXyTransformation(layoutWidth / 2, false))
                        //.resize(100, 100)
                        //.onlyScaleDown() // the image will only be resized if it's bigger than 6000x2000 pixels.
                        //.placeholder(R.drawable.loading) // can also be a drawable
                        .into(ivMaskot);

                btYa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(penyakitId != 0){
                            showDialogResult();
                        }else{
                            proccessYa();
                        }
                    }
                });

                btTidak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(penyakitId != 0 && !topLevel){
                            showDialogResult();
                        }else{
                            proccessTidak();
                        }
                    }
                });
            }
        });
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("pohon_keputusan.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void proccessYa(){
        List<PohonKeputusan> pohonKeputusen = currentPhk.child;
        int pohonKeputusenSize = pohonKeputusen.size();

        if(pohonKeputusenSize > 0){
            topLevel = false;
            mPohonKeputusan = pohonKeputusen;
            phkSize = pohonKeputusen.size();
            phkIndex = 0;
            currentPhk = mPohonKeputusan.get(phkIndex);
            tvPertanyaan.setText(currentPhk.gejala + " (" +currentPhk.kode + ")");
            penyakitId = currentPhk.penyakit_id;
        }else{
            Log.e("CHLD0", currentPhk.kode);
            phkIndex = phkIndex + 1;
            currentPhk = mPohonKeputusan.get(phkIndex);
            tvPertanyaan.setText(currentPhk.gejala + " (" +currentPhk.kode + ")");
            penyakitId = currentPhk.penyakit_id;
        }
    }

    private void proccessTidak(){
        phkIndex ++;
        if(phkIndex < phkSize){
            currentPhk = mPohonKeputusan.get(phkIndex);
            tvPertanyaan.setText(currentPhk.gejala + " (" +currentPhk.kode + ")");
            penyakitId = currentPhk.penyakit_id;
        }else{
            resetPhk();
        }
    }

    private void resetPhk(){
        topLevel = true;
        penyakitId = 0;
        phkIndex = 0;
        mPohonKeputusan = mPohonKeputusanDefault;
        phkSize = mPohonKeputusan.size();
        currentPhk = mPohonKeputusan.get(phkIndex);
        tvPertanyaan.setText(currentPhk.gejala + " (" +currentPhk.kode + ")");
    }

    private void showDialogResult(){
        final Penyakit penyakit = Penyakit.findById(Penyakit.class, penyakitId);

        AlertDialog.Builder builder = new AlertDialog.Builder(KonsultasiActivity.this);
        builder.setTitle("Hasil Analisa!");
        builder.setMessage("Tanaman lada anda terserang " + penyakit.nama + " !! (" + penyakit.kode + ")");
        builder.setPositiveButton("Lihat penjelasan penyakit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();

                        Intent intent = new Intent(KonsultasiActivity.this, DetailPenyakitActivity.class);
                        intent.putExtra(DetailPenyakitActivity.PENYAKIT_PARCEL, Parcels.wrap(penyakit));
                        startActivity(intent);

                        resetPhk();
                    }
                });
        builder.setNegativeButton("Kembali ke konsultasi",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                        resetPhk();
                    }
                });
        builder.show();
    }
}
