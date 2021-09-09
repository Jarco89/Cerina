package nl.schutrup.cerina.irma.client.session.start.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "u",
        "irmaqr"
})
public class SessionPtr {

    @JsonProperty("u")
    private String u;
    @JsonProperty("irmaqr")
    private String irmaqr;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("u")
    public String getU() {
        return u;
    }

    @JsonProperty("u")
    public void setU(String u) {
        this.u = u;
    }

    @JsonProperty("irmaqr")
    public String getIrmaqr() {
        return irmaqr;
    }

    @JsonProperty("irmaqr")
    public void setIrmaqr(String irmaqr) {
        this.irmaqr = irmaqr;
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
        return "SessionPtr{" +
                "u='" + u + '\'' +
                ", irmaqr='" + irmaqr + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
