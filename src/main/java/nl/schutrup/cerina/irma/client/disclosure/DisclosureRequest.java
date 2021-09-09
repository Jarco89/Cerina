package nl.schutrup.cerina.irma.client.disclosure;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "iat",
        "sub",
        "sprequest"
})
public class DisclosureRequest {

    @JsonProperty("iat")
    private Long iat;
    @JsonProperty("sub")
    private String sub;
    @JsonProperty("sprequest")
    private SpRequest sprequest;

    @JsonProperty("iat")
    public Long getIat() {
        return iat;
    }

    @JsonProperty("iat")
    public void setIat(Long iat) {
        this.iat = iat;
    }

    @JsonProperty("sub")
    public String getSub() {
        return sub;
    }

    @JsonProperty("sub")
    public void setSub(String sub) {
        this.sub = sub;
    }

    @JsonProperty("sprequest")
    public SpRequest getSpRequest() {
        return sprequest;
    }

    @JsonProperty("sprequest")
    public void setSprequest(SpRequest sprequest) {
        this.sprequest = sprequest;
    }

}