package nl.schutrup.cerina.irma.client.issuance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "gevaccineerd",
        "aantalvaccinaties",
        "vaccinatiepaspoort",
        "antilichamen",
        "antilichamendatum",
        "sneltest",
        "sneltest2",
        "sneltestdatum",

})
public class CoronaIssuanceRequest {

        @JsonProperty("gevaccineerd")
        private String gevaccineerd;

        @JsonProperty("aantalvaccinaties")
        private String aantalvaccinaties;

        @JsonProperty("vaccinatiepaspoort")
        private String vaccinatiepaspoort;

        @JsonProperty("antilichamen")
        private String antilichamen;

        @JsonProperty("antilichamendatum")
        private String antilichamendatum;

        @JsonProperty("sneltest")
        private String sneltest;

        @JsonProperty("sneltest-2")
        private String sneltest2;

        @JsonProperty("sneltestdatum")
        private String sneltestdatum;


        @JsonProperty("gevaccineerd")
        public String getGevaccineerd() {
            return gevaccineerd;
        }

        @JsonProperty("gevaccineerd")
        public void setGevaccineerd(String gevaccineerd) {
            this.gevaccineerd = gevaccineerd;
        }

        @JsonProperty("aantalvaccinaties")
        public String getAantalvaccinaties() {
            return aantalvaccinaties;
        }

        @JsonProperty("aantalvaccinaties")
        public void setAantalvaccinaties(String aantalvaccinaties) {
            this.aantalvaccinaties = aantalvaccinaties;
        }

        @JsonProperty("vaccinatiepaspoort")
        public String getVaccinatiepaspoort() {
            return vaccinatiepaspoort;
        }

        @JsonProperty("vaccinatiepaspoort")
        public void setVaccinatiepaspoort(String vaccinatiepaspoort) {
            this.vaccinatiepaspoort = vaccinatiepaspoort;
        }

        @JsonProperty("antilichamen")
        public String getAntilichamen() {
            return antilichamen;
        }

        @JsonProperty("antilichamen")
        public void setAntilichamen(String antilichamen) {
            this.antilichamen = antilichamen;
        }

        @JsonProperty("antilichamendatum")
        public String getAntilichamendatum() {
            return antilichamendatum;
        }

        @JsonProperty("antilichamendatum")
        public void setAntilichamendatum(String antilichamendatum) {
            this.antilichamendatum = antilichamendatum;
        }

        @JsonProperty("sneltest")
        public String getSneltest() {
            return sneltest;
        }

        @JsonProperty("sneltest")
        public void setSneltest(String sneltest) {
            this.sneltest = sneltest;
        }

        @JsonProperty("sneltest2")
        public String getSneltest2() {
            return sneltest2;
        }

        @JsonProperty("sneltest2")
        public void setSneltest2(String sneltest2) {
            this.sneltest2 = sneltest2;
        }

        @JsonProperty("sneltestdatum")
        public String getSneltestdatum() {
            return sneltestdatum;
        }

        @JsonProperty("sneltestdatum")
        public void setSneltestdatum(String sneltestdatum) {
            this.sneltestdatum = sneltestdatum;
        }

    @Override
    public String toString() {
        return "CoronaIssuanceRequest{" +
                "gevaccineerd='" + gevaccineerd + '\'' +
                ", aantalvaccinaties='" + aantalvaccinaties + '\'' +
                ", vaccinatiepaspoort='" + vaccinatiepaspoort + '\'' +
                ", antilichamen='" + antilichamen + '\'' +
                ", antilichamendatum='" + antilichamendatum + '\'' +
                ", sneltest='" + sneltest + '\'' +
                ", sneltest2='" + sneltest2 + '\'' +
                ", sneltestdatum='" + sneltestdatum + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoronaIssuanceRequest that = (CoronaIssuanceRequest) o;
        return Objects.equals(gevaccineerd, that.gevaccineerd) &&
                Objects.equals(aantalvaccinaties, that.aantalvaccinaties) &&
                Objects.equals(vaccinatiepaspoort, that.vaccinatiepaspoort) &&
                Objects.equals(antilichamen, that.antilichamen) &&
                Objects.equals(antilichamendatum, that.antilichamendatum) &&
                Objects.equals(sneltest, that.sneltest) &&
                Objects.equals(sneltest2, that.sneltest2) &&
                Objects.equals(sneltestdatum, that.sneltestdatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gevaccineerd, aantalvaccinaties, vaccinatiepaspoort, antilichamen, antilichamendatum, sneltest, sneltest2, sneltestdatum);
    }
}
