package unila.rizka.pakarlada.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import unila.rizka.pakarlada.R;
import unila.rizka.pakarlada.helper.OnItemClickListener;
import unila.rizka.pakarlada.model.Penyakit;

/**
 * Created by japra_awok on 20/04/2017.
 */

public class PenyakitAdapter extends RecyclerView.Adapter<PenyakitAdapter.ViewHolder>{

    private Context context;
    private List<Penyakit> mPenyakit;
    private OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public PenyakitAdapter(Context context, List<Penyakit> penyakits) {
        this.context = context;
        this.mPenyakit = penyakits;
    }


    @Override
    public PenyakitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.penyakit_list_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PenyakitAdapter.ViewHolder holder, int position) {
        Penyakit penyakit = mPenyakit.get(position);
        holder.tvNamaPenyakit.setText(penyakit.nama);
    }

    @Override
    public int getItemCount() {
        return mPenyakit == null ? 0 : mPenyakit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tvNamaPenyakit) TextView tvNamaPenyakit;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
}
