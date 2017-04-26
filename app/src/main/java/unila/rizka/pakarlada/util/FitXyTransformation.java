package unila.rizka.pakarlada.util;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * Created by japra_awok on 18/04/2017.
 */

public class FitXyTransformation implements Transformation {

    int mSize ;

    /*
    true: If the size is apply as the final height of the bitmap.
    false: If the size is apply as the final width of the bitmap.
    * */
    boolean isHeightScale;

    public FitXyTransformation(int size, boolean isHeightScale) {
        mSize  = size;
        this.isHeightScale = isHeightScale;
    }

    @Override public Bitmap transform(Bitmap source) {
        float scale;
        int newSize;
        Bitmap scaledBitmap;

        if (isHeightScale) {
            scale = (float) mSize / source.getHeight();
            newSize = Math.round(source.getWidth() * scale);
            scaledBitmap = Bitmap.createScaledBitmap(source, newSize, mSize, true);
        } else {
            scale = (float) mSize / source.getWidth();
            newSize = Math.round(source.getHeight() * scale);
            scaledBitmap = Bitmap.createScaledBitmap(source, mSize, newSize, true);
        }
        if (scaledBitmap != source) {
            source.recycle();
        }

        return scaledBitmap;
    }

    @Override
    public String key() {
        return "scaleRespectRatio" + mSize + isHeightScale;
    }
}
