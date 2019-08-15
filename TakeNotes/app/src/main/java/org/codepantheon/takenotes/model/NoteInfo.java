package org.codepantheon.takenotes.model;

import android.text.TextUtils;

import java.io.Serializable;

public final class NoteInfo implements Serializable {
    private static final String DEFAULT_TITLE = "Note Title";
    private static final int INVALID_ID = -1;

    private long id = INVALID_ID;
    private String title = "";
    private String content = "";
    private String summary = "";
    private String modifiedDate = "";

    public NoteInfo() {}

    public NoteInfo(long id, String title, String content, String modifiedDate) {
        this.id = id;
        this.title = title.equals("") ? DEFAULT_TITLE : title;
        this.modifiedDate = modifiedDate;
        this.content = content;
        this.summary = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getSummary() {
        return summary;
    }

    public boolean isEmpty(){
        return (TextUtils.isEmpty(title) || title.equals(DEFAULT_TITLE)) && TextUtils.isEmpty(content);
    }

    public boolean isNewNote(){
        return id == INVALID_ID;
    }

    private void setSummary(String summary) {
        this.summary = summary;
    }
}
