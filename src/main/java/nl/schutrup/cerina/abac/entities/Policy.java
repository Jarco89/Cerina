package nl.schutrup.cerina.abac.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="policies")
public class Policy {

    @Id
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "current_value")
    private String currentValue;

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

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Policy policy = (Policy) o;
        return id == policy.id &&
                Objects.equals(name, policy.name) &&
                Objects.equals(currentValue, policy.currentValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, currentValue);
    }

    @Override
    public String toString() {
        return "Policy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentValue='" + currentValue + '\'' +
                '}';
    }
}
