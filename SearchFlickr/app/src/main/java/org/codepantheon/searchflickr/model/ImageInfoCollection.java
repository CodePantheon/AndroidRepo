package org.codepantheon.searchflickr.model;


import com.google.gson.annotations.SerializedName;

public final class ImageInfoCollection {
    @SerializedName("page")
    private String mPage;

    @SerializedName("pages")
    private String mPages;

    @SerializedName("perpage")
    private String mPerPage;

    @SerializedName("total")
    private String mTotal;

    @SerializedName("photo")
    private ImageInfo[] mImageInfos;

    public ImageInfo[] getImageInfos(){
        return mImageInfos;
    }
}
