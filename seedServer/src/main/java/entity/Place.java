package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "SEED_PLACE")
public class Place implements Serializable{

    public Place(){
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 255, name = "CITY", nullable = false)
    private String city;
    @Column(length = 25, name = "ZIP", nullable = false)
    private int zip;
    @Column(length = 255, name = "STREET", nullable = false)
    private String street;
    @Column(length = 255, name = "GPS_LOCATION", nullable = true)
    private String gpsLocation;
    @Column(length = 255, name = "DESCRIPTION", nullable = false)
    private String description;
    @Column(length = 25, name = "RATING", nullable = false)
    private int rating;
    @Column(length = 255, name = "IMAGE_URI", nullable = false)
    private String imgUri;

    public Place(String city, int zip, String street, String gpsLocation, String description, int rating, String imgUri) {
        this.city = city;
        this.zip = zip;
        this.street = street;
        this.gpsLocation = gpsLocation;
        this.description = description;
        this.rating = rating;
        this.imgUri = imgUri;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
   

    
}