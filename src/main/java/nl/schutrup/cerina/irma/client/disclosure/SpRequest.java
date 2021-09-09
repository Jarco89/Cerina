package nl.schutrup.cerina.irma.client.disclosure;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "request"
})
public class SpRequest {

    @JsonProperty("request")
    private Request request;

    @JsonProperty("request")
    public Request getRequest() {
        return request;
    }

    @JsonProperty("request")
    public void setRequest(Request request) {
        this.request = request;
    }

}