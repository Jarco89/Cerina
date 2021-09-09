package nl.schutrup.cerina.abac.service;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyConstants;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.abac.model.subject.SubjectAttribute;
import nl.schutrup.cerina.irma.client.session.result.Disclosed;
import nl.schutrup.cerina.irma.client.session.result.ServerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.stream.Collectors;

import static nl.schutrup.cerina.config.IrmaUserAttributeNames.*;

@Component
public class ContextHandler {

    @Autowired
    private PolicyInformationPoint policyInformationPoint;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextHandler.class);

    // Mapping from Cerina attributes to IRMA attributes 1 Cerina attribute to 1..N IRMA attributes.
    private static HashMap<String, List<String>> mapping = new HashMap<>();

    static {
        mapping.put(PolicyConstants.STAD, Arrays.asList(IRMA_DEMO_MIJNOVERHEID_ADDRESS_CITY, CERINA_DEMO_CERINA_ISSUER_CERINA_CREDENTIAL_STAD));
        mapping.put(PolicyConstants.VOORNAAM, Arrays.asList(IRMA_DEMO_MIJNOVERHEID_FULLNAME_FIRSTNAME, CERINA_DEMO_CERINA_ISSUER_CERINA_CREDENTIAL_NAAM));
        mapping.put(PolicyConstants.LEEFTIJD, Arrays.asList(CERINA_DEMO_CERINA_ISSUER_CERINA_CREDENTIAL_LEEFTIJD));
        mapping.put(PolicyConstants.AMSTERDAM_POSTCODE, Arrays.asList(IRMA_DEMO_MIJNOVERHEID_ADDRESS_ZIPCODE));

        mapping.put(PolicyConstants.CORONA_GEVACINEERD, Arrays.asList(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_GEVACCINEERD));
        mapping.put(PolicyConstants.AANTAL_CORONA_VACCINATIES, Arrays.asList(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_AANTAL_VACCINATIES));

        mapping.put(PolicyConstants.CORONA_VACCINATIE_PASPOORT, Arrays.asList(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_VACCINATIEPASPOORT));

        mapping.put(PolicyConstants.CORONA_ANTILICHAMEN, Arrays.asList(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_ANTILICHAMEN));
        mapping.put(PolicyConstants.CORONA_ANTILICHAMEN_DATUM, Arrays.asList(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_ANTILICHAMEN_DATUM));

        mapping.put(PolicyConstants.CORONA_SNELTEST, Arrays.asList(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_SNELTEST));
        mapping.put(PolicyConstants.CORONA_SNELTEST_DATUM, Arrays.asList(CERINA_DEMO_CERINA_ISSUER_CERINA_CORONA_CREDENTIAL_SNELTESTDATUM));

    }


    public AccessRequest buildAccessRequest(String token, Resource resource, Operation operation) throws InvalidKeySpecException, URISyntaxException, NoSuchAlgorithmException, IllegalAccessException, IOException {
        ServerResult serverResult = policyInformationPoint.getAdditionalInfo(token);

        List<SubjectAttribute> attributeList = serverResult
                .getDisclosed()
                .stream()
                .flatMap(List::stream)
                .map(disclosed -> convert(disclosed)).collect(Collectors.toList());

        Subject subject = new Subject(token);
        subject.setAttributeList(attributeList);
        AccessRequest accessRequest = new AccessRequest(resource, serverResult, subject, operation);
        return accessRequest;
    }


    public SubjectAttribute convert(Disclosed disclosed) {
        SubjectAttribute attribute = new SubjectAttribute();
        LOGGER.info("translation disclosed: {}", disclosed);

        Map<String, List<String>> result = mapping
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(disclosed.getId()))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

        if (!result.isEmpty()) {
            LOGGER.info("Result is present: {}", result);

            String attributeName = result.entrySet().stream().findFirst().get().getKey(); // There is a result.
            LOGGER.info("Disclosed {} -> Attributename -> {} attributevalue -> {}", disclosed.getId(), attributeName, disclosed.getRawvalue());
            attribute.setName(attributeName);
            attribute.setValue(disclosed.getRawvalue());

            return attribute;
        } else {
            LOGGER.info("Result is not present");
        }
        return null;
    }


}
