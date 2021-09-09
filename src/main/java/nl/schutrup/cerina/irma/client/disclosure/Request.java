package nl.schutrup.cerina.irma.client.disclosure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "@context",
        "disclose"
})
public class Request {

    @JsonProperty("@context")
    private String context;
    @JsonProperty("disclose")
    private List<List<List<String>>> disclose = null;

    @JsonProperty("@context")
    public String getContext() {
        return context;
    }

    @JsonProperty("@context")
    public void setContext(String context) {
        this.context = context;
    }

    @JsonProperty("disclose")
    public List<List<List<String>>> getDisclose() {
        return disclose;
    }

    @JsonProperty("disclose")
    public void setDisclose(List<List<List<String>>> disclose) {
        this.disclose = disclose;
    }

}
