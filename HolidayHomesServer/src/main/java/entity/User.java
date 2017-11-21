package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;
import security.IUser;
import security.PasswordStorage;

@Entity(name = "SEED_USER")
public class User implements IUser, Serializable {

    @ManyToMany(mappedBy = "userCollection")
    private Collection<Role> roleCollection;
    
//    @OneToMany(mappedBy = "adminName")
    private Collection<Rentable> rentableCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userName")
    private Collection<Booking> bookingCollection;
    
    @OneToMany(mappedBy = "userName")
    private Collection<Place> userPlaceCollection;

    //You will need to change this to save a Hashed/salted password 
    @Column(length = 255, name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;

    @Id
    @Column(length = 35, name = "USER_NAME", nullable = false)
    private String userName;

    @ManyToMany
    List<Role> roles;

    public User() {
    }

    public User(String userName, String password) throws PasswordStorage.CannotPerformOperationException {
        this.userName = userName;
        this.passwordHash = PasswordStorage.createHash(password);
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new ArrayList();
        }
        roles.add(role);
        role.addUser(this);
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public List<String> getRolesAsStrings() {
        if (roles.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList();
        for (Role role : roles) {
            rolesAsStrings.add(role.getRoleName());
        }
        return rolesAsStrings;
    }

    @Override
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String password) throws PasswordStorage.CannotPerformOperationException {
        this.passwordHash = PasswordStorage.createHash(password);
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @XmlTransient
    public Collection<Role> getRoleCollection() {
        return roleCollection;
    }

    public void setRoleCollection(Collection<Role> roleCollection) {
        this.roleCollection = roleCollection;
    }

    @XmlTransient
    public Collection<Booking> getBookingCollection() {
        return bookingCollection;
    }

    public void setBookingCollection(Collection<Booking> bookingCollection) {
        this.bookingCollection = bookingCollection;
    }

    @XmlTransient
    public Collection<Place> getUserPlaceCollection() {
        return userPlaceCollection;
    }

    public void setUserPlaceCollection(Collection<Place> userPlaceCollection) {
        this.userPlaceCollection = userPlaceCollection;
    }

    @XmlTransient
    public Collection<Rentable> getRentableCollection() {
        return rentableCollection;
    }

    public void setRentableCollection(Collection<Rentable> rentableCollection) {
        this.rentableCollection = rentableCollection;
    }

}