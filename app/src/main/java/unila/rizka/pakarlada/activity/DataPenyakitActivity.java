package unila.rizka.pakarlada.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.orm.query.Condition;
import com.orm.query.Select;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import unila.rizka.pakarlada.R;
import unila.rizka.pakarlada.adapter.PenyakitAdapter;
import unila.rizka.pakarlada.helper.OnItemClickListener;
import unila.rizka.pakarlada.model.Penyakit;

public class DataPenyakitActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private LinearLayoutManager mLinearLayoutManager;
    private PenyakitAdapter mAdapter;
    private List<Penyakit> mPenyakitList = new ArrayList<>();
    private List<Penyakit> mPenyakitListFiltered = new ArrayList<>();

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_penyakit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Penyakit");

        ButterKnife.bind(this);

        mPenyakitList.addAll(Penyakit.listAll(Penyakit.class));
        mPenyakitListFiltered.addAll(mPenyakitList);

        mAdapter = new PenyakitAdapter(this, mPenyakitListFiltered);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Penyakit penyakit = mPenyakitListFiltered.get(position);

                Intent intent = new Intent(DataPenyakitActivity.this, DetailPenyakitActivity.class);
                intent.putExtra(DetailPenyakitActivity.PENYAKIT_PARCEL, Parcels.wrap(penyakit));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mPenyakitListFiltered.clear();
                mPenyakitListFiltered.addAll(mPenyakitList);
                mAdapter.notifyDataSetChanged();

                return false;
            }
        });

        searchView.setQueryHint("Cari nana penyakit...");
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (TextUtils.isEmpty(query)) {
            return false;
        }else{
            mPenyakitListFiltered.clear();

            List<Penyakit> pny = Select.from(Penyakit.class)
                    .where(Condition.prop("nama").like("%" + query + "%"))
                    .list();

            Log.e("SIZE", String.valueOf(pny.size()));

            if(pny.size() > 0){
                mPenyakitListFiltered.addAll(pny);
            }

            mAdapter.notifyDataSetChanged();
        }

        return true;
    }
}
