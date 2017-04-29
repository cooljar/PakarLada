package unila.rizka.pakarlada.model;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by japra_awok on 19/04/2017.
 */

@Parcel
public class Penyakit extends SugarRecord {
    public String kode, nama, deskripsi, penangananmekanis, penanganankimiawi, penangananbudidaya, gambar;

    public Penyakit() {
    }

    public Penyakit(String kode, String nama, String deskripsi, String penangananmekanis, String penanganankimiawi,
                    String penangananbudidaya, String gambar) {
        this.kode = kode;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.penangananmekanis = penangananmekanis;
        this.penanganankimiawi = penanganankimiawi;
        this.penangananbudidaya = penangananbudidaya;
        this.gambar = gambar;
    }

    public List<PenyakitGejala> getPenyakitGejala(){
        List<PenyakitGejala> penyakitGejalas = Select.from(PenyakitGejala.class)
                .where(Condition.prop("penyakitid").eq(getId()))
                .list();

        return penyakitGejalas;
    }
}
