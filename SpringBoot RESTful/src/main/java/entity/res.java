package entity;

public class res {
    private final String username;
    private final String radio;
    private final String message;

    public res(String username, String radio, String message) {
        this.username = username;
        this.radio = radio;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getRadio() {
        return radio;
    }

    public String getMessage() {
        return message;
    }
}
