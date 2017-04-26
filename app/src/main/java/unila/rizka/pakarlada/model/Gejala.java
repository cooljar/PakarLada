package unila.rizka.pakarlada.model;

import com.orm.SugarRecord;

import org.parceler.Parcel;

/**
 * Created by japra_awok on 17/04/2017.
 */

@Parcel
public class Gejala extends SugarRecord {
    public String kode, gejala;

    public Gejala() {
    }

    public Gejala(String kode, String gejala) {
        this.kode = kode;
        this.gejala = gejala;
    }
}
