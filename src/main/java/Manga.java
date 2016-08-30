import java.util.Date;

public class Manga {
  private String uid;
  private String title;
  private String cover;
  private Date latestChapter;
  private Date lastTimeUpdate;
  private Date released;
  private boolean completed;

  public Manga() {
  }

  public Manga(String title) {
    this.title = title;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(final String uid) {
    this.uid = uid;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(final String cover) {
    this.cover = cover;
  }

  public Date getLatestChapter() {
    return latestChapter;
  }

  public void setLatestChapter(final Date latestChapter) {
    this.latestChapter = latestChapter;
  }

  public Date getLastTimeUpdate() {
    return lastTimeUpdate;
  }

  public void setLastTimeUpdate(final Date lastTimeUpdate) {
    this.lastTimeUpdate = lastTimeUpdate;
  }

  public Date getReleased() {
    return released;
  }

  public void setReleased(final Date released) {
    this.released = released;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(final boolean completed) {
    this.completed = completed;
  }
}
