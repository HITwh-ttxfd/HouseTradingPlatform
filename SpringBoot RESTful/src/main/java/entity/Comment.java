package entity;

public class Comment {
    private String authorID;
    private String date;
    private String houseID;
    private String content;
    private float score;

    public Comment(String authorID, String date,String houseID, String content, String score) {
        this.authorID = authorID;
        this.date = date;
        this.houseID = houseID;
        this.content = content;
        this.score=Float.parseFloat(score);
    }

    public float getScore() {
        return score;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
    public String getHouseID() {
        return houseID;
    }
}
