package nl.schutrup.cerina.irma.client.issuance;

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
        "bsn",
        "naam",
        "stad",
        "leeftijd"
})
public class IssuanceRequest {

    @JsonProperty("bsn")
    private String bsn;
    @JsonProperty("naam")
    private String naam;
    @JsonProperty("stad")
    private String stad;
    @JsonProperty("leeftijd")
    private String leeftijd;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("bsn")
    public String getBsn() {
        return bsn;
    }

    @JsonProperty("bsn")
    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    @JsonProperty("naam")
    public String getNaam() {
        return naam;
    }

    @JsonProperty("naam")
    public void setNaam(String naam) {
        this.naam = naam;
    }

    @JsonProperty("stad")
    public String getStad() {
        return stad;
    }

    @JsonProperty("stad")
    public void setStad(String stad) {
        this.stad = stad;
    }

    @JsonProperty("leeftijd")
    public String getLeeftijd() {
        return leeftijd;
    }

    @JsonProperty("leeftijd")
    public void setLeeftijd(String leeftijd) {
        this.leeftijd = leeftijd;
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
        return "IssuanceRequest{" +
                "bsn='" + bsn + '\'' +
                ", naam='" + naam + '\'' +
                ", stad='" + stad + '\'' +
                ", leeftijd='" + leeftijd + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}