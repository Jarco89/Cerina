package nl.schutrup.cerina.abac.service;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Library;
import nl.schutrup.cerina.abac.model.subject.Subject;
import nl.schutrup.cerina.irma.client.session.result.ServerResult;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PolicyEnforcementPointTest {


    @InjectMocks
    private PolicyEnforcementPoint policyEnforcementPoint;

    @Mock
    private PolicyDecisionPoint policyDecisionPoint;

    @Mock
    private ContextHandler contextHandler;

    @Test
    public void testEnforcementPermit() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, IllegalAccessException, URISyntaxException {
        String token = "1234";
        String scenario = "scenario-1-jwt";

        // Build access request
        ServerResult serverResult = new ServerResult();
        Library library = new Library();
        serverResult.setToken(token);
        serverResult.setStatus("VALID");

        AccessRequest accessRequest = new AccessRequest(library, serverResult, new Subject(), Operation.ACCESS_REQUEST);

        PolicyResult result = PolicyResult.PERMIT;

        when(contextHandler.buildAccessRequest(token, library, Operation.ACCESS_REQUEST)).thenReturn(accessRequest);
        when(policyDecisionPoint.evaluate(accessRequest, scenario)).thenReturn(result);

        ServerResult serverResult1 = policyEnforcementPoint.autoriseer(token, scenario, library, Operation.ACCESS_REQUEST);

        assertEquals(serverResult, serverResult1);

    }

    @Test
    public void testEnforcementDeny() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, IllegalAccessException, URISyntaxException {
        String token = "1234";
        String scenario = "scenario-1-jwt";

        // Build access request
        ServerResult serverResult = new ServerResult();
        Library library = new Library();
        serverResult.setToken(token);
        serverResult.setStatus("DENY");

        AccessRequest accessRequest = new AccessRequest(library, serverResult, new Subject(), Operation.ACCESS_REQUEST);

        PolicyResult result = PolicyResult.DENY;

        when(contextHandler.buildAccessRequest(token, library, Operation.ACCESS_REQUEST)).thenReturn(accessRequest);
        when(policyDecisionPoint.evaluate(accessRequest, scenario)).thenReturn(result);

        ServerResult serverResult1 = policyEnforcementPoint.autoriseer(token, scenario, library, Operation.ACCESS_REQUEST);

        assertEquals(serverResult, serverResult1);

    }
}