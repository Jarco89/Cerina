package nl.schutrup.cerina.abac.service;

import nl.schutrup.cerina.irma.client.IrmaServerClient;
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
public class PolicyInformationPointTest {

    @InjectMocks
    private PolicyInformationPoint policyInformationPoint;

    @Mock
    private IrmaServerClient irmaServerClient;

    @Test
    public void testGetAdditionalInfo() throws InvalidKeySpecException, URISyntaxException, NoSuchAlgorithmException, IllegalAccessException, IOException {
        String token = "12345";
        ServerResult serverResult = new ServerResult();
        serverResult.setToken(token);
        when(irmaServerClient.retrieveSessionResultJWT(token)).thenReturn(serverResult);
        ServerResult res = policyInformationPoint.getAdditionalInfo(token);
        assertEquals(token, res.getToken());
    }

}