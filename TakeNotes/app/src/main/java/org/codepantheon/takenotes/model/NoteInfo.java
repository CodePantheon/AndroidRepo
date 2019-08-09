package org.codepantheon.takenotes.model;

public class NoteInfo {

    private long id;
    private String title;
    private String content;
    private String summary;

    public NoteInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoteInfo(long aLong, String string, String string1, String string2) {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSummary() {
        return summary;
    }
}
