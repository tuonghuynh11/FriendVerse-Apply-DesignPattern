package com.example.friendverse.Model.ConvertImage;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageToJpgConverter {
    private final ContentResolver contentResolver;

    public ImageToJpgConverter(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public Uri convertPngToJpg(Uri inputUri) throws IOException {
        String inputPath = inputUri.getPath();
        String fileNameWithoutExtension = inputPath.substring(inputPath.lastIndexOf('/') + 1, inputPath.lastIndexOf('.'));
        String newFileName = fileNameWithoutExtension + ".jpg";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, newFileName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
        Uri outputUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        if (outputUri == null) {
            throw new IOException("Failed to create new MediaStore entry.");
        }
        InputStream inputStream = contentResolver.openInputStream(inputUri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        if (bitmap == null) {
            throw new IOException("Failed to decode input image.");
        }
        OutputStream outputStream = contentResolver.openOutputStream(outputUri);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.close();
        inputStream.close();
        return outputUri;
    }
}
