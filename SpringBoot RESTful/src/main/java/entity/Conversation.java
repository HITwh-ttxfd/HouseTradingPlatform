package entity;

public class Conversation {
    private String id;
    private String username;
    private String date;
    private String recentMessage;
    private String status;
    public Conversation(String id,String username,String date,String recentMessage,String status){
        this.id=id;
        this.username=username;
        this.date=date;
        this.recentMessage=recentMessage;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getRecentMessage() {
        return recentMessage;
    }
}
