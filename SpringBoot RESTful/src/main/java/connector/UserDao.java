package connector;

import entity.User;

public interface UserDao {
    public void add(User user);
    public User find(String type,String username);
}