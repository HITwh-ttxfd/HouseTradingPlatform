package connector;

import entity.User;

public interface UserDao {
    public int add(User user);
    public User find(String type,String username);
}