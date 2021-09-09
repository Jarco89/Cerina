package nl.schutrup.cerina.abac.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="resources")
public class Resource {

    @Id
    private long id;
    @Column(name = "name")
    private String name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
