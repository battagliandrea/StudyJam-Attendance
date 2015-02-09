package com.gdgcatania.info.studyjamattendance.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrea on 09/02/2015.
 */
public class DefaultAvatarCreator {


    private static final int blue = Color.rgb(32, 147, 205);
    private static final int verde = Color.rgb(103,91,116);
    private static final int light_blue = Color.rgb(103,91,116);
    private static final int light_orage = Color.rgb(249,164,62);
    private static final int light_red = Color.rgb(245,133,89);
    private static final int pink = Color.rgb(241,99,100);
    private static final int violet = Color.rgb(241,99,100);
    private static final int giallo = Color.rgb(228,198,46);


    /** Create a standard avatar for users
     *
     * @param letter 1st letter of username
     * @param context activity context
     * @return the standard avatar as Bitmap
     */
    public static Bitmap getDefaultAvatar(String letter, Context context){


        float ONE_DP = 1 * context.getResources().getDisplayMetrics().density;

        Bitmap bm = Bitmap.createBitmap(Math.round(50*ONE_DP), Math.round(50*ONE_DP),Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(bm);

        Paint paint = new Paint();
        paint.setColor(getColorFromLetter(letter));

        paint.setStyle(Paint.Style.FILL);
        cv.drawPaint(paint);

        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(30);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(Math.round(30*ONE_DP));
        paint.setTextAlign(Paint.Align.CENTER);
        cv.drawText(String.valueOf(letter.toUpperCase()),
                Math.round(50*ONE_DP)/2,
                Math.round(35*ONE_DP),
                paint);

        return bm;


    }


    /** Get color associated whith letter
     *
     * @param letter 1st letter of username
     * @return the color
     */
    public static int getColorFromLetter(String letter){

        final Map<String,Integer> map;
        map = new HashMap<>();
        map.put("a", blue);
        map.put("b", light_blue);
        map.put("c", verde);
        map.put("d", light_orage);
        map.put("e", light_red);
        map.put("f", giallo);
        map.put("g", pink);
        map.put("h", violet);
        map.put("i", blue);
        map.put("j", light_blue);
        map.put("k", verde);
        map.put("l", giallo);
        map.put("m", light_orage);
        map.put("n", light_red);
        map.put("o", pink);
        map.put("p", violet);
        map.put("q", pink);
        map.put("r", light_red);
        map.put("s", light_orage);
        map.put("t", giallo);
        map.put("u", verde);
        map.put("v", light_blue);
        map.put("w", blue);
        map.put("x", verde);
        map.put("y", pink);
        map.put("z", light_orage);

        return map.get(letter.toLowerCase()).intValue();
    }


    /**
     * Clip a Bitmap
     *
     * @param bitmap the image you need to clip
     * @return the clipped bitmap
     */
    public static Bitmap getclip(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        // paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2,
                bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

}
