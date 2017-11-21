package entity;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "user_place")
@XmlRootElement
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "place_name")
    private String placeName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "description")
    private String description;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "imgURL")
    private String imgURL;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "gps_lat")
    private double gpsLat;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "gps_long")
    private double gpsLong;
    
    @JoinColumn(name = "user_name", referencedColumnName = "USER_NAME")
    @ManyToOne
    private User userName;
    
    @Transient
    private double rating;
    
    @Transient
    @Expose
    private boolean userHasVoted;
    
    
    public Place() {
    }

    public Place(String locationName) {
        this.placeName = locationName;
    }

    public Place(String locationName, String description, String imgURL, double gpsLat, double gpsLong) {
        this.placeName = locationName;
        this.description = description;
        this.imgURL = imgURL;
        this.gpsLat = gpsLat;
        this.gpsLong = gpsLong;
    }

    public String getLocationName() {
        return placeName;
    }

    public void setLocationName(String locationName) {
        this.placeName = locationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
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

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        this.userName = userName;
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
        hash += (placeName != null ? placeName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Place)) {
            return false;
        }
        Place other = (Place) object;
        if ((this.placeName == null && other.placeName != null) || (this.placeName != null && !this.placeName.equals(other.placeName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UserPlace[ locationName=" + placeName + " ]";
    }

}
