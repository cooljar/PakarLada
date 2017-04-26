package unila.rizka.pakarlada;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import unila.rizka.pakarlada.activity.MainActivity;
import unila.rizka.pakarlada.helper.DataBaseHelper;
import unila.rizka.pakarlada.model.Gejala;
import unila.rizka.pakarlada.model.Penyakit;
import unila.rizka.pakarlada.model.PenyakitGejala;
import unila.rizka.pakarlada.util.FitXyTransformation;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private DataBaseHelper myDB;
    private CountDownTimer ctdn;
    private ProgressBar mProgressBar;

    int layoutWidth, layoutHeight;

    @BindView(R.id.rlContainer) RelativeLayout rlContainer;
    @BindView(R.id.imageView) ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        ViewTreeObserver vto = rlContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rlContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                layoutWidth = rlContainer.getWidth();
                layoutHeight = rlContainer.getHeight();

                Picasso.with(SplashActivity.this)
                        .load(R.mipmap.ic_launcher)
                        .transform(new FitXyTransformation(layoutWidth / 2, false))
                        .into(imageView);

                myDB = new DataBaseHelper(getApplicationContext());

                try {
                    myDB.createDataBase();
                }
                catch(IOException e)
                {
                    // do nothing
                }

                mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

                ctdn = new CountDownTimer(5*1000, 1000) {
                    int i=0;
                    public void onTick(long millisUntilFinished) {
                        i++;
                        mProgressBar.setProgress(i);

                    }
                    public void onFinish() {
                        i++;
                        mProgressBar.setProgress(i);

                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);

                        SplashActivity.this.finish();
                    }
                }.start();
            }
        });

        /*Gejala gejala = Gejala.findById(Gejala.class, 1);
        Log.e("KODE_GEJ", gejala.kode);
        Log.e("ID_GEJ", String.valueOf(gejala.getId()));

        Penyakit penyakit = Penyakit.findById(Penyakit.class, 1);
        Log.e("KODE_PEN", penyakit.kode);
        Log.e("ID_PEN", String.valueOf(penyakit.getId()));

        PenyakitGejala penyakitGejala = PenyakitGejala.findById(PenyakitGejala.class, 1);
        Log.e("ID_PG", String.valueOf(penyakitGejala.getId()));*/

        /*List<Gejala> gejalas = Gejala.listAll(Gejala.class);
        for(Gejala gejala: gejalas){
            Log.e("KODE", gejala.kode);
            Log.e("GEJALA", gejala.gejala);
            Log.e("ID", String.valueOf(gejala.getId()));
        }*/
    }
}
