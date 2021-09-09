package nl.schutrup.cerina.abac.model.policy;


import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.rule.Rule;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public abstract class AbstractPolicy {

    protected long id;
    protected String value;
    protected String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public abstract PolicyResult doAutorisationValidation(Subject requestor, Resource resource, Operation operation);

    @Override
    public String toString() {
        return "AbstractPolicy{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPolicy that = (AbstractPolicy) o;
        return id == that.id &&
                Objects.equals(value, that.value) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, name);
    }
}

