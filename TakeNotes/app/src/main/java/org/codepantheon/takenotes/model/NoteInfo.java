package org.codepantheon.takenotes.model;

public final class NoteInfo {
    private long id;
    private String title;
    private String content;
    private String summary;
    private String modifiedDate;

    public NoteInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoteInfo(long id, String title, String content, String summary) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.summary = summary;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
