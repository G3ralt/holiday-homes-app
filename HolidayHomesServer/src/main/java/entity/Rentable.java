package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "rentable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rentable.findAll", query = "SELECT r FROM Rentable r")
    , @NamedQuery(name = "Rentable.findByRentableName", query = "SELECT r FROM Rentable r WHERE r.rentableName = :rentableName")
    , @NamedQuery(name = "Rentable.findByStreet", query = "SELECT r FROM Rentable r WHERE r.street = :street")
    , @NamedQuery(name = "Rentable.findByCity", query = "SELECT r FROM Rentable r WHERE r.city = :city")
    , @NamedQuery(name = "Rentable.findByZipcode", query = "SELECT r FROM Rentable r WHERE r.zipcode = :zipcode")
    , @NamedQuery(name = "Rentable.findByCountry", query = "SELECT r FROM Rentable r WHERE r.country = :country")
    , @NamedQuery(name = "Rentable.findByPrice", query = "SELECT r FROM Rentable r WHERE r.price = :price")
    , @NamedQuery(name = "Rentable.findByImgURL", query = "SELECT r FROM Rentable r WHERE r.imgURL = :imgURL")
    , @NamedQuery(name = "Rentable.findByDescription", query = "SELECT r FROM Rentable r WHERE r.description = :description")
    , @NamedQuery(name = "Rentable.findByAdminName", query = "SELECT r FROM Rentable r WHERE r.adminName = :adminName")})
public class Rentable implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "rentable_name")
    private String rentableName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "street")
    private String street;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "city")
    private String city;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "zipcode")
    private int zipcode;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "country")
    private String country;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private double price;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "imgURL")
    private String imgURL;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "description")
    private String description;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "admin_name")
    private String adminName;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rentableName")
    private Collection<Booking> bookingCollection;
    
    @Transient
    private double rating;
    

    public Rentable() {
    }

    public Rentable(String rentableName) {
        this.rentableName = rentableName;
    }

    public Rentable(String rentableName, String street, String city, int zipcode, String country, double price, String imgURL, String description, String adminName) {
        this.rentableName = rentableName;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.country = country;
        this.price = price;
        this.imgURL = imgURL;
        this.description = description;
        this.adminName = adminName;
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

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    @XmlTransient
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
}
