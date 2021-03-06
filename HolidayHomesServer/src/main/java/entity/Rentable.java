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

    @NotNull
    @Expose
    @Column(name = "gps_lat")
    private double gpsLat;
    
    @NotNull
    @Expose
    @Column(name = "gps_long")
    private double gpsLong;

    @Id
    @NotNull
    @Size(min = 1, max = 100)
    @Expose
    @Column(name = "rentable_name")
    private String rentableName;

    @NotNull
    @Size(min = 1, max = 100)
    @Expose
    @Column(name = "street")
    private String street;

    @NotNull
    @Size(min = 1, max = 45)
    @Expose
    @Column(name = "city")
    private String city;

    @NotNull
    @Expose
    @Column(name = "zipcode")
    private int zipCode;

    @NotNull
    @Size(min = 1, max = 45)
    @Expose
    @Column(name = "country")
    private String country;

    @NotNull
    @Expose
    @Column(name = "price")
    private double price;

    @NotNull
    @Expose
    @Size(min = 1, max = 1000)
    @Column(name = "imgURL")
    private String imgURL;

    @NotNull
    @Size(min = 1, max = 1000)
    @Expose
    @Column(name = "description")
    private String description;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rentable")
    private Collection<Booking> bookingCollection;

    @Expose
    @Transient
    private double rating; // Average rating for the rentable
    
    @Expose
    @JoinColumn(name = "admin_name", referencedColumnName = "USER_NAME")
    @ManyToOne
    private User admin; //Admin who added the rentable

    @Transient
    @Expose
    private int userRating; //Current user`s rating
    
    @Transient
    @Expose
    private Collection<String> availableWeeks;

    public Rentable() {
    }

    public Rentable(String rentableName) {
        this.rentableName = rentableName;
    }
    
    public Rentable(String rentableName, String street, String city, int zipcode, String country, double price, String imgURL, String description, User admin) {
        this.rentableName = rentableName;
        this.street = street;
        this.city = city;
        this.zipCode = zipcode;
        this.country = country;
        this.price = price;
        this.imgURL = imgURL;
        this.description = description;
        this.admin = admin;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Collection<String> getAvailableWeeks() {
        return availableWeeks;
    }

    public void setAvailableWeeks(Collection<String> availableWeeks) {
        this.availableWeeks = availableWeeks;
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

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipcode) {
        this.zipCode = zipcode;
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

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
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

    public double getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public double getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(double gpsLong) {
        this.gpsLong = gpsLong;
    }
}
