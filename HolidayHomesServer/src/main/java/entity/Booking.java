package entity;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "booking")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b")
    , @NamedQuery(name = "Booking.findById", query = "SELECT b FROM Booking b WHERE b.id = :id")
    , @NamedQuery(name = "Booking.findByWeekNumber", query = "SELECT b FROM Booking b WHERE b.weekNumber = :weekNumber")})
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    
    @Expose
    @Basic(optional = false)
    @NotNull
    @Column(name = "week_number")
    private String weekNumber;
    
    @Expose
    @JoinColumn(name = "rentable_name", referencedColumnName = "rentable_name")
    @ManyToOne(optional = false)
    private Rentable rentable;
    
    @Expose
    @JoinColumn(name = "user_name", referencedColumnName = "USER_NAME")
    @ManyToOne(optional = false)
    private User user;

    public Booking() {
    }

    public Booking(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(String weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Rentable getRentableName() {
        return rentable;
    }

    public void setRentableName(Rentable rentableName) {
        this.rentable = rentableName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Booking[ id=" + id + " ]";
    }

}
