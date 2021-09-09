package nl.schutrup.cerina.abac.service;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.subject.SubjectAttribute;
import nl.schutrup.cerina.irma.client.session.result.Disclosed;
import nl.schutrup.cerina.irma.client.session.result.ServerResult;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import static nl.schutrup.cerina.config.IrmaUserAttributeNames.IRMA_DEMO_MIJNOVERHEID_ADDRESS_CITY;
import static nl.schutrup.cerina.config.IrmaUserAttributeNames.IRMA_DEMO_MIJNOVERHEID_FULLNAME_FIRSTNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ContextHandlerTest {

    @InjectMocks
    private ContextHandler contextHandler;

    @Mock
    private PolicyInformationPoint policyInformationPoint;

    @Test
    public void testBuildAccessRequestWithSubjectAttributes() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, IllegalAccessException, URISyntaxException {
        String token = "12345";
        String value = "Amsterdam";
        String name = "John";
        ServerResult serverResult = new ServerResult();
        serverResult.setToken(token);

        Disclosed disclosedCity = new Disclosed();
        disclosedCity.setRawvalue(value);
        disclosedCity.setId(IRMA_DEMO_MIJNOVERHEID_ADDRESS_CITY);

        Disclosed disclosedName = new Disclosed();
        disclosedName.setRawvalue(name);
        disclosedName.setId(IRMA_DEMO_MIJNOVERHEID_FULLNAME_FIRSTNAME);

        List<List<Disclosed>> disclosedList = new ArrayList<>();
        List<Disclosed> discloses = new ArrayList<>();
        discloses.add(disclosedCity);
        discloses.add(disclosedName);

        disclosedList.add(discloses);

        serverResult.setDisclosed(disclosedList);

        when(policyInformationPoint.getAdditionalInfo(token)).thenReturn(serverResult);

        AccessRequest accessRequest = contextHandler.buildAccessRequest(token, any(), Operation.READ_REQUEST);

        List<SubjectAttribute> subjectAttributeList = accessRequest.getSubject().getAttributeList();

        assertEquals(false, subjectAttributeList.isEmpty());

        SubjectAttribute first = subjectAttributeList.get(0);
        SubjectAttribute second = subjectAttributeList.get(1);

        assertEquals(second.getValue(), name);
        assertEquals(first.getValue(), value);
        assertEquals(accessRequest.getSubject().getToken(), token);
    }

    @Test
    public void testBuildAccessRequestWithNOSubjectAttributes() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, IllegalAccessException, URISyntaxException {
        String token = "12345";
        ServerResult serverResult = new ServerResult();
        serverResult.setToken(token);


        List<List<Disclosed>> disclosedList = new ArrayList<>();
        List<Disclosed> discloses = new ArrayList<>();

        disclosedList.add(discloses);

        serverResult.setDisclosed(disclosedList);

        when(policyInformationPoint.getAdditionalInfo(token)).thenReturn(serverResult);

        AccessRequest accessRequest = contextHandler.buildAccessRequest(token, any(), Operation.READ_REQUEST);

        List<SubjectAttribute> subjectAttributeList = accessRequest.getSubject().getAttributeList();

        assertEquals(true, subjectAttributeList.isEmpty());
    }
}