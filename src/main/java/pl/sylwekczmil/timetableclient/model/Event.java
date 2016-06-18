
package pl.sylwekczmil.timetableclient.model;


import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Event implements Serializable {

    private Integer idEvent;
    private String name;
    private String lecturer;
    private int start;
    private int end;
    private int day;
   
    public Event() {
    }

    public Event(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public Event(Integer idEvent, String name, String lecturer, int start, int end, int day) {
        this.idEvent = idEvent;
        this.name = name;
        this.lecturer = lecturer;
        this.start = start;
        this.end = end;
        this.day = day;
    }
    
    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvent != null ? idEvent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.idEvent == null && other.idEvent != null) || (this.idEvent != null && !this.idEvent.equals(other.idEvent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
