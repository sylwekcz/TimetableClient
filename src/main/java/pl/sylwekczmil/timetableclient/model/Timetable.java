package pl.sylwekczmil.timetableclient.model;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Timetable implements Serializable {

    private Integer idTimetable;
    private String name;
    private int week;
    private Collection<User> userCollection;

    public Timetable() {
    }

    public Timetable(Integer idTimetable) {
        this.idTimetable = idTimetable;
    }

    public Timetable(Integer idTimetable, String name, int week) {
        this.idTimetable = idTimetable;
        this.name = name;
        this.week = week;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }
    
    

    public Integer getIdTimetable() {
        return idTimetable;
    }

    public void setIdTimetable(Integer idTimetable) {
        this.idTimetable = idTimetable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTimetable != null ? idTimetable.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Timetable)) {
            return false;
        }
        Timetable other = (Timetable) object;
        if ((this.idTimetable == null && other.idTimetable != null) || (this.idTimetable != null && !this.idTimetable.equals(other.idTimetable))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.sylwekczmil.timetableserver.Timetable[ idTimetable=" + idTimetable + " ]";
    }

}
