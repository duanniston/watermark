package br.com.duanniston.watermark.app.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created by duanniston.cabral on 01/08/2016.
 */

public class WaterMark extends BaseClass {

    private Context mContext;

    private WaterMark() {

    }

    public WaterMark(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    public Bitmap getImageWaterMarkFromView(@NonNull Bitmap src, @NonNull View view) {

        if (view == null || src == null) {
            Log.e(TAG, "view or src is null !!");
            return null;
        }

        ViewGroup vg = (ViewGroup) view;

        for (int i = 0; i < vg.getChildCount(); i++) {
            View childAt = vg.getChildAt(i);
            ViewGroup.LayoutParams params = childAt.getLayoutParams();

            if (params.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                params.width = src.getWidth();
                vg.getChildAt(i).setLayoutParams(params);
            }
        }


        view.setMinimumWidth(src.getWidth());
        view.setMinimumHeight(src.getHeight());

        Bitmap bitmapView = loadBitmapFromView(view);

        if (bitmapView == null) {
            Log.e(TAG, "loadBitmapFromView error !!, bitmapView is null");
            return null;
        }

        Bitmap waBitmap = addWaterMark(src, bitmapView);


        if (waBitmap == null) {
            Log.e(TAG, "addWaterMark error !!, waBitmap is null");
            return null;
        }

        return waBitmap;

    }

    @Nullable
    public Bitmap getImageWaterMarkFromView(@NonNull Bitmap src, @LayoutRes int view) {

        if (view == 0 || src == null) {
            Log.e(TAG, "view or src is null !!");
            return null;
        }

        LayoutInflater mInflater = LayoutInflater.from(mContext);

        View viewLayout = mInflater.inflate(view, null);


        if (viewLayout == null) {
            Log.e(TAG, "viewLayout is null !!");
        }

        ViewGroup vg = (ViewGroup) viewLayout;

        for (int i = 0; i < vg.getChildCount(); i++) {
            View childAt = vg.getChildAt(i);
            ViewGroup.LayoutParams params = childAt.getLayoutParams();

            if (params.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                params.width = src.getWidth();
                vg.getChildAt(i).setLayoutParams(params);
            }
        }


        viewLayout.setMinimumWidth(src.getWidth());
        viewLayout.setMinimumHeight(src.getHeight());

        Bitmap bitmapView = loadBitmapFromView(viewLayout);

        if (bitmapView == null) {
            Log.e(TAG, "loadBitmapFromView error !!, bitmapView is null");
            return null;
        }

        Bitmap waBitmap = addWaterMark(src, bitmapView);


        if (waBitmap == null) {
            Log.e(TAG, "addWaterMark error !!, waBitmap is null");
            return null;
        }

        return waBitmap;

    }

    @Nullable
    public Bitmap getImageWaterMarkFromView(@NonNull Uri uri, @LayoutRes int view) {

        if (view == 0 || uri == null) {
            Log.e(TAG, "view or uri is null !!");
            return null;
        }

        LayoutInflater mInflater = LayoutInflater.from(mContext);

        View viewLayout = mInflater.inflate(view, null);


        if (viewLayout == null) {
            Log.e(TAG, "viewLayout is null !!");
        }

        Bitmap src;

        try {
            src = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ViewGroup vg = (ViewGroup) viewLayout;

        for (int i = 0; i < vg.getChildCount(); i++) {
            View childAt = vg.getChildAt(i);
            ViewGroup.LayoutParams params = childAt.getLayoutParams();

            if (params.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                params.width = src.getWidth();
                vg.getChildAt(i).setLayoutParams(params);
            }
        }


        viewLayout.setMinimumWidth(src.getWidth());
        viewLayout.setMinimumHeight(src.getHeight());

        Bitmap bitmapView = loadBitmapFromView(viewLayout);

        if (bitmapView == null) {
            Log.e(TAG, "loadBitmapFromView error !!, bitmapView is null");
            return null;
        }

        Bitmap waBitmap = addWaterMark(src, bitmapView);


        if (waBitmap == null) {
            Log.e(TAG, "addWaterMark error !!, waBitmap is null");
            return null;
        }

        return waBitmap;

    }

    @Nullable
    public Bitmap loadBitmapFromView(@NonNull View view) {


        if (view == null) {
            Log.e(TAG, "view is null !!");
            return null;
        }

        try {
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.draw(canvas);
            return bitmap;
        } catch (Exception ex) {
            Log.e(TAG, "loadBitmapFromView error !! ", ex);
            return null;
        }

    }

    @Nullable
    public Bitmap addWaterMark(@NonNull Bitmap src, @NonNull Bitmap waterMark) {

        if (src == null || waterMark == null) {

            Log.e(TAG, "src or waterMark is null !!");
            return null;
        }


        try {
            int w = src.getWidth();
            int h = src.getHeight();
            Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
            Canvas canvas = new Canvas(result);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.drawBitmap(waterMark, 0, 0, null);
            return result;

        } catch (Exception ex) {
            Log.e(TAG, "addWaterMark error !! ", ex);
            return null;
        }


    }
}
