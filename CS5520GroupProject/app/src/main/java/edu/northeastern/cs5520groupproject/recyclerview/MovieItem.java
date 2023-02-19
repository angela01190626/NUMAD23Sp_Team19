package edu.northeastern.cs5520groupproject.recyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MovieItem implements Parcelable {
    private String name;
    private String releaseDate;
    private String imageLink;

    private String type;

    ExecutorService executor = Executors.newFixedThreadPool(10);

    public MovieItem(){

    }

    protected MovieItem(Parcel in) {
        name = in.readString();
        releaseDate = in.readString();
        imageLink = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(releaseDate);
        dest.writeString(imageLink);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getType() {return type;}

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImage() {
        return imageLink;
    }

    public void setImage(String link) {
        imageLink = link;
//        try {
//            Callable<Void> callable = new ImageRequest(link);
//            Future<Void> future = executor.submit(callable);
//            future.get();
//
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }

    }


//    class ImageRequest implements Callable<Void> {
//        private final String link;
//
//        ImageRequest(String link) {
//            this.link = link;
//        }
//
//        @Override
//        public Void call() throws Exception {
//            URL url = new URL(link);
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            Thread.sleep(5000);
//
//            image.setImageBitmap(bmp);
//            return null;
//        }
//    }
}
