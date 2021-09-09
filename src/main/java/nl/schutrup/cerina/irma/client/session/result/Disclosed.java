package nl.schutrup.cerina.irma.client.session.result;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "rawvalue",
        "id",
        "value"
})
public class Disclosed {

    @JsonProperty("status")
    private String status;
    @JsonProperty("rawvalue")
    private String rawvalue;
    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private Value value;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("rawvalue")
    public String getRawvalue() {
        return rawvalue;
    }

    @JsonProperty("rawvalue")
    public void setRawvalue(String rawvalue) {
        this.rawvalue = rawvalue;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("value")
    public Value getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Value value) {
        this.value = value;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    @Override
    public String toString() {
        return "Disclosed{" +
                "status='" + status + '\'' +
                ", rawvalue='" + rawvalue + '\'' +
                ", id='" + id + '\'' +
                ", value=" + value +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disclosed disclosed = (Disclosed) o;
        return Objects.equals(status, disclosed.status) &&
                Objects.equals(rawvalue, disclosed.rawvalue) &&
                Objects.equals(id, disclosed.id) &&
                Objects.equals(value, disclosed.value) &&
                Objects.equals(additionalProperties, disclosed.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, rawvalue, id, value, additionalProperties);
    }
}