package unila.rizka.pakarlada.model;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.parceler.Parcel;

/**
 * Created by japra_awok on 19/04/2017.
 */

@Parcel
public class PenyakitGejala extends SugarRecord {
    public int penyakitid, gejalaid;

    public PenyakitGejala() {
    }

    public PenyakitGejala(int penyakitid, int gejalaid) {
        this.penyakitid = penyakitid;
        this.gejalaid = gejalaid;
    }

    public Gejala getGejala(){
        Gejala gejala = Select.from(Gejala.class)
                .where(Condition.prop("ID").eq(gejalaid))
                .first();

        return gejala;
    }
}
