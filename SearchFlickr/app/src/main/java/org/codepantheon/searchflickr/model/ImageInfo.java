package org.codepantheon.searchflickr.model;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public final class ImageInfo {
    private static final String IMAGE_URL_FORMAT = "https://farm%d.staticflickr.com/%s/%s_%s.jpg";

    @SerializedName("id")
    private String mId;

    @SerializedName("owner")
    private String mPages;

    @SerializedName("secret")
    private String mSecret;

    @SerializedName("server")
    private String mServer;

    @SerializedName("farm")
    private int mFarm;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("ispublic")
    private int mIsPublic;

    @SerializedName("isfriend")
    private int mIsFriend;

    @SerializedName("isfamily")
    private int mIsFamily;

    public String getImageUrl(){
        return String.format(Locale.getDefault(), IMAGE_URL_FORMAT, mFarm, mServer, mId, mSecret);
    }

    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ImageInfo)) {
            return false;
        }

        return getImageUrl().equals(((ImageInfo)obj).getImageUrl());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
