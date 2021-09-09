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
        "token",
        "sessionPtr"
})
public class ServerResponse {

    @JsonProperty("token")
    private String token;
    @JsonProperty("sessionPtr")
    private SessionPtr sessionPtr;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("sessionPtr")
    public SessionPtr getSessionPtr() {
        return sessionPtr;
    }

    @JsonProperty("sessionPtr")
    public void setSessionPtr(SessionPtr sessionPtr) {
        this.sessionPtr = sessionPtr;
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
        return "ServerResponse{" +
                "token='" + token + '\'' +
                ", sessionPtr=" + sessionPtr +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}