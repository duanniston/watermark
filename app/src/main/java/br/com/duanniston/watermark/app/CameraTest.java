package br.com.duanniston.watermark.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

import br.com.duanniston.watermark.R;
import br.com.duanniston.watermark.app.framework.WaterMark;
import uk.co.senab.photoview.PhotoViewAttacher;


public class CameraTest extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    PhotoViewAttacher mAttacher;
    private Uri uriImg;
    private Bitmap original;
    private Bitmap waterMark;
    private Target target;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.image_view_test);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getImage();
            }
        });
        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                imageView.setImageBitmap(bitmap);
                original = bitmap;

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

    }

    private void getImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (original == null) {

            Toast.makeText(this, "add image first.", Toast.LENGTH_LONG).show();

            return super.onOptionsItemSelected(item);
        }

        switch (item.getItemId()) {
            case R.id.menu_water_mark_add:

                waterMark = generateWaterMark(original);
                imageView.setImageBitmap(waterMark);

                return true;

            case R.id.menu_water_mark_remove:
                imageView.setImageBitmap(original);
                return true;

            case R.id.menu_water_mark_export:

                if (waterMark == null) {
                    Toast.makeText(this, "generate watermark first.", Toast.LENGTH_LONG).show();

                    return super.onOptionsItemSelected(item);
                }

                saveToInternalStorage(waterMark);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveToInternalStorage(final Bitmap bitmapImage) {
        Handler hl = new Handler(getMainLooper());

        hl.post(new Runnable() {
            @Override
            public void run() {


                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + File.separator + "waterMark";

                File mypath = new File(path + "/watermark_" + System.currentTimeMillis() + ".png");


                FileOutputStream fos = null;

                try {
                    mypath.mkdirs();
                    mypath.createNewFile();
                    fos = new FileOutputStream(mypath);

                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 80, fos);
                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    try {
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {


            if (data != null) {
                //Get image
                uriImg = data.getData();
                Picasso.with(CameraTest.this).load(uriImg).resize(1000, 1500).error(android.R.drawable.ic_dialog_alert).placeholder(android.R.drawable.ic_menu_gallery).into(target);


            }
        }

    }

    private Bitmap generateWaterMark(Bitmap src) {

        WaterMark waterMark = new WaterMark(CameraTest.this);


        return waterMark.getImageWaterMarkFromView(src, R.layout.watermark_all);
    }


}
