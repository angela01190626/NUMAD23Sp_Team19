package edu.northeastern.cs5520groupproject.recyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MovieItem {
    private String name;
    private String releaseDate;
    private ImageView image;
    ExecutorService executor = Executors.newFixedThreadPool(10);


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(String link) {
        try {
            Callable<Bitmap> callable = new ImageRequest(link);
            Future<Bitmap> future = executor.submit(callable);
            Bitmap bitmap = future.get();
            image.setImageBitmap(bitmap);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    class ImageRequest implements Callable<Bitmap> {
        private final String link;

        ImageRequest(String link) {
            this.link = link;
        }

        @Override
        public Bitmap call() throws Exception {
            URL url = new URL(link);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        }
    }
}
