package entity;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "rentable")
public class Rentable implements Serializable {

    @JoinColumn(name = "admin_name", referencedColumnName = "USER_NAME")
    @ManyToOne
    private User adminName;

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "rentable_name")
    private String rentableName;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "street")
    private String street;

    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "zipcode")
    private int zipcode;

    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "country")
    private String country;

    @NotNull
    @Column(name = "price")
    private double price;

    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "imgURL")
    private String imgURL;

    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rentableName")
    private Collection<Booking> bookingCollection;

    @Transient
    private double rating;

    @Transient
    @Expose
    private boolean userHasVoted;

    public Rentable() {
    }

    public Rentable(String rentableName) {
        this.rentableName = rentableName;
    }

    public Rentable(String rentableName, String street, String city, int zipcode, String country, double price, String imgURL, String description) {
        this.rentableName = rentableName;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.country = country;
        this.price = price;
        this.imgURL = imgURL;
        this.description = description;
    }

    public String getRentableName() {
        return rentableName;
    }

    public void setRentableName(String rentableName) {
        this.rentableName = rentableName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Booking> getBookingCollection() {
        return bookingCollection;
    }

    public void setBookingCollection(Collection<Booking> bookingCollection) {
        this.bookingCollection = bookingCollection;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isUserHasVoted() {
        return userHasVoted;
    }

    public void setUserHasVoted(boolean userHasVoted) {
        this.userHasVoted = userHasVoted;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rentableName != null ? rentableName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rentable)) {
            return false;
        }
        Rentable other = (Rentable) object;
        if ((this.rentableName == null && other.rentableName != null) || (this.rentableName != null && !this.rentableName.equals(other.rentableName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rentable[ rentableName=" + rentableName + " ]";
    }

    public User getAdminName() {
        return adminName;
    }

    public void setAdminName(User adminName) {
        this.adminName = adminName;
    }
}
