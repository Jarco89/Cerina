package nl.schutrup.cerina.abac.model.subject;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Subject {

    private String token;
    private List<SubjectAttribute> attributeList;

    public Subject() {
        this.attributeList = new ArrayList<>();
    }

    public Subject(String token)  {
        this.token = token;
        this.attributeList = new ArrayList<>();
    }

    public void addAttribute (SubjectAttribute attribute) {
        this.attributeList.add(attribute);
    }

    public List<SubjectAttribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<SubjectAttribute> attributeList) {
        this.attributeList = attributeList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(token, subject.token) &&
                Objects.equals(attributeList, subject.attributeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, attributeList);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "token='" + token + '\'' +
                ", attributeList=" + attributeList +
                '}';
    }
}
