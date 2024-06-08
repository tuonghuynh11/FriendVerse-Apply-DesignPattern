package com.example.friendverse.Model.ConvertImage;

import android.net.Uri;

import java.io.IOException;

public class ImageConverterAdapter implements ImageConverter{
    private ImageToJpgConverter imageToJpgConverters;

    public ImageConverterAdapter(ImageToJpgConverter imageToJpgConverters) {
        this.imageToJpgConverters = imageToJpgConverters;
    }

    @Override
    public Uri convertImage(Uri inputUri) {
        try{
            return imageToJpgConverters.convertPngToJpg(inputUri);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
