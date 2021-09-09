package nl.schutrup.cerina.abac.model.resource;

import java.util.List;
import java.util.Objects;

public class Resource {

    public Resource() {
    }

    private String name;

    public List<ResourceAttribute> resourceAttributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResourceAttribute> getResourceAttributes() {
        return resourceAttributes;
    }

    public void setResourceAttributes(List<ResourceAttribute> resourceAttributes) {
        this.resourceAttributes = resourceAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(name, resource.name) &&
                Objects.equals(resourceAttributes, resource.resourceAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, resourceAttributes);
    }

    @Override
    public String toString() {
        return "Resource{" +
                "name='" + name + '\'' +
                ", resourceAttributes=" + resourceAttributes +
                '}';
    }
}
