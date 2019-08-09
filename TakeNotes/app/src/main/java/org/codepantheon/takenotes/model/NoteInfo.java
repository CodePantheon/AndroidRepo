package org.codepantheon.takenotes.model;

public final class NoteInfo {
    private long mId;
    private String mTitle;
    private String mContent;
    private String mSummary;
    private String mModifiedDate;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getModifiedDate() {
        return mModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.mModifiedDate = modifiedDate;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        this.mSummary = summary;
    }
}
