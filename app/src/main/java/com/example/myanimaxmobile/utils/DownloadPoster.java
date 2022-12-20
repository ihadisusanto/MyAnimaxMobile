package com.example.myanimaxmobile.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadPoster extends AsyncTask<String,Void, Bitmap> {
    ImageView bmImage;

    public DownloadPoster(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls){
        String urldisplay = urls[0];
        Bitmap imageBitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            imageBitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            Log.e("Download Poster" , "Error in Downloading Image");
            e.printStackTrace();
        }
        return imageBitmap;
    }

    protected void onPostExecute(Bitmap result) {

        bmImage.setImageBitmap(result);
    }
}
