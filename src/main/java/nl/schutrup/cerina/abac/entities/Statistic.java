package nl.schutrup.cerina.abac.entities;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="statistics")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "visitor_city")
    private String visitorCity;

    @Column(columnDefinition = "TIMESTAMP", name="visit_date_time")
    private LocalDateTime visitDateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVisitorCity() {
        return visitorCity;
    }

    public void setVisitorCity(String visitorCity) {
        this.visitorCity = visitorCity;
    }

    public LocalDateTime getVisitDateTime() {
        return visitDateTime;
    }

    public void setVisitDateTime(LocalDateTime visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "id=" + id +
                ", visitorLocation='" + visitorCity + '\'' +
                ", visitDateTime=" + visitDateTime +
                '}';
    }
}
