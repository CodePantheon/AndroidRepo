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
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageAdapter.ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v("SearchFlickr", "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.image_item, parent, false);

        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        Log.v("SearchFlickr", "onBindViewHolder");
        holder.updateImage(m_imageSources.get(position));
    }

    @Override
    public int getItemCount() {
        return m_imageSources.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private ImageInfo mImageInfo;

        private ImageHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_Image);
        }

        private void updateImage(ImageInfo imageInfo) {
            if(imageInfo.equals(mImageInfo)){
                return;
            }

            mImageInfo = imageInfo;
            Picasso.with(itemView.getContext()).load(mImageInfo.getImageUrl()).into(mImageView);
        }
    }
}
