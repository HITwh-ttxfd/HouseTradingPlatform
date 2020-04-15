package entity;

public class Comment {
    private String authorID;
    private String date;
    private String houseID;
    private String content;

    public Comment(String authorID, String date,String houseID, String content) {
        this.authorID = authorID;
        this.date = date;
        this.houseID = houseID;
        this.content = content;
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
