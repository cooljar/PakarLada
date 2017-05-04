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

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import unila.rizka.pakarlada.R;
import unila.rizka.pakarlada.helper.DataBaseHelper;
import unila.rizka.pakarlada.model.Gejala;
import unila.rizka.pakarlada.model.Penyakit;
import unila.rizka.pakarlada.model.PenyakitGejala;
import unila.rizka.pakarlada.util.FitXyTransformation;

public class MainActivity extends AppCompatActivity {

    int layoutWidth, layoutHeight;

    @BindView(R.id.llContainer) LinearLayout llContainer;
    @BindView(R.id.llDataPenyakit) LinearLayout llDataPenyakit;
    @BindView(R.id.llBantuan) LinearLayout llBantuan;
    @BindView(R.id.llKonsultasi) LinearLayout llKonsultasi;
    @BindView(R.id.llTentang) LinearLayout llTentang;

    @BindView(R.id.ivDataPenyakit) ImageView ivDataPenyakit;
    @BindView(R.id.ivBantuan) ImageView ivBantuan;
    @BindView(R.id.ivKonsultasi) ImageView ivKonsultasi;
    @BindView(R.id.ivTentang) ImageView ivTentang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ViewTreeObserver vto = llContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                layoutWidth = llContainer.getWidth();
                layoutHeight = llContainer.getHeight();

                int ivWidth = layoutWidth / 3;

                Picasso.with(MainActivity.this)
                        .load(R.mipmap.ic_data_penyakit)
                        .transform(new FitXyTransformation(ivWidth, false))
                        .into(ivDataPenyakit);

                Picasso.with(MainActivity.this)
                        .load(R.mipmap.ic_bantuan)
                        .transform(new FitXyTransformation(ivWidth, false))
                        .into(ivBantuan);

                Picasso.with(MainActivity.this)
                        .load(R.mipmap.ic_konsultasi)
                        .transform(new FitXyTransformation(ivWidth, false))
                        .into(ivKonsultasi);

                Picasso.with(MainActivity.this)
                        .load(R.mipmap.ic_tentang)
                        .transform(new FitXyTransformation(ivWidth, false))
                        .into(ivTentang);
            }
        });
    }

    @OnClick(R.id.llDataPenyakit)
    public void openDataPenyakit() {
        Intent intent = new Intent(MainActivity.this, DataPenyakitActivity.class);
        intent.putExtra(DataPenyakitActivity.LAYOUT_WIDTH_PARAM, layoutWidth);
        startActivity(intent);
    }

    @OnClick(R.id.llBantuan)
    public void openBantuan() {
        Intent intent = new Intent(MainActivity.this, BantuanActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.llKonsultasi)
    public void openKonsultasi() {
        Intent intent = new Intent(MainActivity.this, KonsultasiActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.llTentang)
    public void openTentang() {
        Intent intent = new Intent(MainActivity.this, TentangActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Konfirmasi");
        builder.setMessage("Aplikasi akan ditutup. Lanjutkan?");

        builder.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                        MainActivity.this.finish();
                    }
                }
        );

        builder.setNegativeButton(
                "Batal",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                }
        );

        builder.show();
    }
}
