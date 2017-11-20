package entity;

import java.util.List;

public class User_Map {

    public User_Map(User u) {
        this.username = u.getUserName();
        this.roles = u.getRolesAsStrings();
    }

    public String username;
    public List<String> roles;
}
