package nl.schutrup.cerina.irma.client.session.result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "status",
        "disclosed",
        "proofStatus",
        "token"
})

public class ServerResult {

    @JsonProperty("type")
    private String type;
    @JsonProperty("status")
    private String status;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty("disclosed")
    private List<List<Disclosed>> disclosed = null;
    @JsonProperty("proofStatus")
    private String proofStatus;
    @JsonProperty("token")
    private String token;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("disclosed")
    public List<List<Disclosed>> getDisclosed() {
        return disclosed;
    }

    @JsonProperty("disclosed")
    public void setDisclosed(List<List<Disclosed>> disclosed) {
        this.disclosed = disclosed;
    }

    @JsonProperty("proofStatus")
    public String getProofStatus() {
        return proofStatus;
    }

    @JsonProperty("proofStatus")
    public void setProofStatus(String proofStatus) {
        this.proofStatus = proofStatus;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonAnySetter
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @Override
    public String toString() {
        return "ServerResult{" +
                "type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", disclosed=" + disclosed +
                ", proofStatus='" + proofStatus + '\'' +
                ", token='" + token + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}

