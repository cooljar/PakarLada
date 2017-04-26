package unila.rizka.pakarlada.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import unila.rizka.pakarlada.R;

public class TentangActivity extends AppCompatActivity {

    @BindView(R.id.email) ImageButton ibEmail;
    @BindView(R.id.facebook) ImageButton ibFb;
    @BindView(R.id.pStore) ImageButton ibPstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Tentang");

        ButterKnife.bind(this);

        ibEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        ibFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/zuck"));
                startActivity(intent);
            }
        });

        ibPstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                //Copy App URL from Google Play Store.
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=unila.rizka.pakarlada"));
                startActivity(intent);
            }
        });
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"rizkaeb@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Komentar Aplikasi Pakar Lada");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Isi komentar anda");

        try {
            startActivity(Intent.createChooser(emailIntent, "Mengirim email..."));
            //finish();
            Log.i("Finished sending email", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Log.i("Finished sending email", "");
        }
    }
}
