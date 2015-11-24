package com.m5b10.photofilter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

    //get reference to ImageView and images
    ImageView taisImageView;    //declare ImageView obj
    Drawable kenpachiyuchiro;   //declare Drawable obj
    Bitmap bitmapImage; //declare Bitmap obj

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taisImageView = (ImageView) findViewById(R.id.taisImageView);

        // CODE SEGMENT TO STORE PHOTO AS BITMAP THEN CALL invertImage()
        kenpachiyuchiro = getResources().getDrawable(R.drawable.kenpachiyuchiro);
        bitmapImage = ((BitmapDrawable) kenpachiyuchiro).getBitmap();   //converts jpg to Bitmap to work with individual pixels
        Bitmap newPhoto = invertImage(bitmapImage);
        taisImageView.setImageBitmap(newPhoto);

        //how to store pics to user's device
        MediaStore.Images.Media.insertImage(getContentResolver(), newPhoto, "title", "description");



        /** CODE SEGMENT TO OVERLAY IMAGES
        Drawable[] layers = new Drawable[2];
        layers[0] = getResources().getDrawable(R.drawable.kenpachiyuchiro);
        layers[1] = getResources().getDrawable(R.drawable.clear);
        LayerDrawable layerDrawable = new LayerDrawable(layers);    //convert layers from memory into LayerDrawable obj
        taisImageView.setImageDrawable(layerDrawable);
         **/
    }

    //Invert Bitmap image
    public static Bitmap invertImage(Bitmap original){

        Bitmap finalImage = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());

        int A, R, G, B; //alpha, red, green, blue
        int pixelColor;
        int height = original.getHeight();
        int width = original.getWidth();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                pixelColor = original.getPixel(x, y);
                //get alpha, red, green, and blue value of each pixel stored in pixelColor
                A = Color.alpha(pixelColor);
                R = 255 - Color.red(pixelColor);    //subtract from 255 to invert color
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);
                finalImage.setPixel(x, y, Color.argb(A, R, G, B));  //

            }
        }

        return finalImage;
    }



}
