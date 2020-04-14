package entity;

public class User {
    private final String username;
    private final String password;
    private final String name;
    private final String phoneNumber;
    private final String id;
    private final boolean isBuyer;
    public User(String username, String password, String name, String phoneNumber, String id, boolean isBuyer) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.isBuyer = isBuyer;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getBuyer() {
        return isBuyer;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getId() {
        return id;
    }
}
