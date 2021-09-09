package nl.schutrup.cerina.service;

import nl.schutrup.cerina.irma.client.IrmaServerClient;
import nl.schutrup.cerina.irma.client.disclosure.DisclosureRequest;
import nl.schutrup.cerina.irma.client.disclosure.Request;
import nl.schutrup.cerina.irma.client.disclosure.SpRequest;
import nl.schutrup.cerina.irma.client.issuance.CoronaIssuanceRequest;
import nl.schutrup.cerina.irma.client.issuance.IssuanceRequest;
import nl.schutrup.cerina.irma.client.session.result.ServerResult;
import nl.schutrup.cerina.irma.client.session.start.response.ServerResponse;
import org.irmacard.api.common.AttributeDisjunction;
import org.irmacard.api.common.AttributeDisjunctionList;
import org.irmacard.credentials.info.AttributeIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static nl.schutrup.cerina.config.IrmaUserAttributeNames.*;

@Component
public class IrmaClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IrmaClientService.class);

    @Autowired
    private IrmaServerClient irmaServerClient;

    public ServerResponse retrieveNewSessionJWT(String scenario) throws Exception {
        AttributeDisjunctionList attributeDisjunctions;
        DisclosureRequest disclosureRequest;

        switch (scenario) {
            case "scenario-1-jwt":
            case "scenario-5-jwt":
            case "scenario-16-jwt":
                attributeDisjunctions = buildList(IRMA_DEMO_MIJNOVERHEID_ADDRESS_CITY); // Request only woonplaats
                return irmaServerClient.retrieveNewSessionWithJWT(attributeDisjunctions);
            case "scenario-4-jwt":
                disclosureRequest = buildRequest(CERINA_DEMO_CERINA_ISSUER_CERINA_CREDENTIAL_NAAM, CERINA_DEMO_CERINA_ISSUER_CERINA_CREDENTIAL_STAD, CERINA_DEMO_CERINA_ISSUER_CERINA_CREDENTIAL_LEEFTIJD);
                return irmaServerClient.retrieveNewSessionWithJWT(disclosureRequest);
            case "scenario-6-jwt":
                attributeDisjunctions = buildList(IRMA_DEMO_MIJNOVERHEID_ADDRESS_CITY, IRMA_DEMO_MIJNOVERHEID_FULLNAME_FIRSTNAME); // Now disjunction
                return irmaServerClient.retrieveNewSessionWithJWT(attributeDisjunctions);
            case "scenario-2-jwt": // Conjunction
            case "scenario-7-jwt":
            case "scenario-8-jwt":
            case "scenario-9-jwt":
            case "scenario-10-jwt":
            case "scenario-11-jwt":
                disclosureRequest = buildRequest(IRMA_DEMO_MIJNOVERHEID_ADDRESS_CITY, IRMA_DEMO_MIJNOVERHEID_FULLNAME_FIRSTNAME);
                return irmaServerClient.retrieveNewSessionWithJWT(disclosureRequest);
            case "scenario-12-jwt":
                disclosureRequest = buildRequest(IRMA_DEMO_MIJNOVERHEID_ADDRESS_ZIPCODE);
                return irmaServerClient.retrieveNewSessionWithJWT(disclosureRequest);
            case "scenario-13-jwt":
                disclosureRequest = buildRequest(IRMA_DEMO_MIJNOVERHEID_FULLNAME_FIRSTNAME);
                return irmaServerClient.retrieveNewSessionWithJWT(disclosureRequest);
            case "scenario-15-jwt":
                List<String> a = new ArrayList<>();
                a.add(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_GEVACCINEERD);
                a.add(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_AANTAL_VACCINATIES);

                List<String> b = new ArrayList<>();
                b.add(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_VACCINATIEPASPOORT);
                b.add(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_SNELTEST);

                List<String> c = new ArrayList<>();
                c.add(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_ANTILICHAMEN);
                c.add(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_ANTILICHAMEN_DATUM);

                List<String> d = new ArrayList<>();
                d.add(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_SNELTEST);
                d.add(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_SNELTESTDATUM);

                List<List<String>> partialList = new ArrayList<>();
                // builds an OR statement
                partialList.add(a);
                partialList.add(b);
                partialList.add(c);
                partialList.add(d);

                DisclosureRequest disclosureRequest1 = buildDisjunctionConjunctionRequest(partialList);
                return irmaServerClient.retrieveNewSessionWithJWT(disclosureRequest1);
            default:
                throw new RuntimeException("Unsupported request: " + scenario);
        }

    }

    public AttributeDisjunctionList buildList(String... fields) {

        AttributeDisjunctionList attributeDisjunctions = new AttributeDisjunctionList();
        AttributeDisjunction attributeDisjunction = new AttributeDisjunction("");

        for (String field : fields) {
            AttributeIdentifier attributeIdentifier = new AttributeIdentifier(field);
            attributeDisjunction.add(attributeIdentifier);
        }

        attributeDisjunctions.add(attributeDisjunction);

        return attributeDisjunctions;
    }

    public DisclosureRequest buildDisjunctionConjunctionRequest(List<List<String>> partialList) {
        List<List<List<String>>> disclosureList = new ArrayList<>();
        disclosureList.add(partialList);

        return build(disclosureList);
    }

    public DisclosureRequest buildRequest(String... fields) {

        List<String> discloses = new ArrayList<>();
        for (String field : fields) {
            discloses.add(field);
        }
        List<List<List<String>>> disclosureList = new ArrayList<>();
        List<List<String>> partialList = new ArrayList<>();
        partialList.add(discloses);
        disclosureList.add(partialList);
        return build(disclosureList);
    }

    public DisclosureRequest build(List<List<List<String>>> disclosureList) {
        DisclosureRequest disclosureRequest = new DisclosureRequest();
        Request request = new Request();
        request.setContext("https://irma.app/ld/request/disclosure/v2");
        request.setDisclose(disclosureList);

        SpRequest spRequest = new SpRequest();
        spRequest.setRequest(request);

        disclosureRequest.setIat(System.currentTimeMillis() / 1000L);
        disclosureRequest.setSub("verification_request");
        disclosureRequest.setSprequest(spRequest);

        return disclosureRequest;
    }

    public ServerResponse getIssuanceSession(IssuanceRequest issuanceRequest) throws Exception {
        try {
            return irmaServerClient.getIssuanceSession(issuanceRequest);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public ServerResponse getIssuanceSession(CoronaIssuanceRequest issuanceRequest) throws Exception {
        try {
            return irmaServerClient.getIssuanceSession(issuanceRequest);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    public ServerResult retrieveSessionResult(String token) throws Exception {
        try {
            return irmaServerClient.retrieveSessionResult(token);
        } catch (Exception e) {
            throw e;
        }
    }

    public ServerResult retrieveSessionResultJWT(String token) throws Exception {
        return irmaServerClient.retrieveSessionResultJWT(token);
    }


    /**

    public ServerResponse retrieveNewSessionScenario1() throws Exception {
        return irmaServerClient.retrieveNewSessionScenario1();
    }

    // OLD
    public ServerResponse retrieveNewSessionScenario2() throws Exception {
        return irmaServerClient.retrieveNewSessionScenario2();
    }
 **/
}
