package org.codepantheon.searchflickr.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.codepantheon.searchflickr.R;
import org.codepantheon.searchflickr.model.ImageInfo;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    private List<ImageInfo> m_imageSources = new ArrayList<ImageInfo>();

    public void setImageSource(List<ImageInfo> imageSources) {
        m_imageSources = imageSources;
        //m_imageSources.add(new ImageInfo());
    }

    @NonNull
    @Override
    public ImageAdapter.ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("SearchFlickr", "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.image_item, parent, false);

        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        Log.i("SearchFlickr", "onBindViewHolder");
        holder.updateImage(m_imageSources.get(position));
    }

    @Override
    public int getItemCount() {
        return m_imageSources.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iv_Image);
        }

        public void updateImage(ImageInfo imageInfo) {

            Picasso.with(itemView.getContext()).load(imageInfo.getImageUrl()).into(imageView);
            //Picasso.with(itemView.getContext()).load("https://farm66.staticflickr.com/65535/48438083587_7afffdd5f6.jpg").into(imageView);
        }
    }

}
