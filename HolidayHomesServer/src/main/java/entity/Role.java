package entity;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name = "USER_ROLE")
public class Role implements Serializable {

    @JoinTable(name = "SEED_USER_USER_ROLE", joinColumns = {
        @JoinColumn(name = "roles_ROLE_NAME", referencedColumnName = "ROLE_NAME")}, inverseJoinColumns = {
        @JoinColumn(name = "users_USER_NAME", referencedColumnName = "USER_NAME")})
    @ManyToMany
    private Collection<User> userCollection;

  @ManyToMany(mappedBy = "roles")
  private List<User> users;

  private static final long serialVersionUID = 1L;
  
  @Id
  @Expose
  @Column(length = 30, name = "ROLE_NAME")
  private String roleName ;

  public Role(String roleName) {
    this.roleName = roleName;
  }

  public Role() {
  }

  public List<User> getUsers() {
    return users;
  }

  public void addUser(User user) {
    if(users == null){
      users = new ArrayList();
    }
    users.add(user);
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }
 
}