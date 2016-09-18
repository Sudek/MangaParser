package com.mangatracker.core.models;


public class Manga {

    private String uid;
    private String title;
    private String source;
    private String url;
    private String description;
    private boolean completed;
    private String lastChapterName;
    private String lastChapterUpdate;
    /**
     * If flag is up do fetch update
     */

    private String thumbnailUrl;

    /**
     * Last time server checked for update
     */

    private long lastUpdate;

    public Manga() {}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastChapterName() {
        return lastChapterName;
    }

    public void setLastChapterName(String lastChapterName) {
        this.lastChapterName = lastChapterName;
    }

    public String getLastChapterUpdate() {
        return lastChapterUpdate;
    }

    public void setLastChapterUpdate(String lastChapterUpdate) {
        this.lastChapterUpdate = lastChapterUpdate;
    }

}
