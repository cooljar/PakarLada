package unila.rizka.pakarlada.model;

import java.util.List;

/**
 * Created by japra_awok on 20/04/2017.
 */

public class PohonKeputusan {
    public String gejala, kode;
    public int penyakit_id;
    public List<PohonKeputusan> child;

    public PohonKeputusan() {
    }

    public PohonKeputusan(String gejala, String kode, int penyakit_id, List<PohonKeputusan> child) {
        this.gejala = gejala;
        this.kode = kode;
        this.penyakit_id = penyakit_id;
        this.child = child;
    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public int getPenyakit_id() {
        return penyakit_id;
    }

    public void setPenyakit_id(int penyakit_id) {
        this.penyakit_id = penyakit_id;
    }

    public List<PohonKeputusan> getChild() {
        return child;
    }

    public void setChild(List<PohonKeputusan> child) {
        this.child = child;
    }
}
