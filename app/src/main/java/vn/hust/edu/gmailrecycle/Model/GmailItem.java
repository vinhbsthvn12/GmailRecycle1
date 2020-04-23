package vn.hust.edu.gmailrecycle.Model;

import java.util.Random;

public class GmailItem {
    String gmail;
    String subject;
    String content;
    String time;
    boolean isFavorite;
    int color;

    public GmailItem(String gmail, String subject, String content, String time) {
        this.gmail = gmail;
        this.subject = subject;
        this.content = content;
        this.time = time;
        isFavorite = false;

        Random random = new Random();
        color = random.nextInt();

    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getGmail() {
        return gmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public int getColor() {
        return color;
    }
}
