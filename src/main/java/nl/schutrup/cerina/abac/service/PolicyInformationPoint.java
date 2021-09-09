package nl.schutrup.cerina.abac.service;

import nl.schutrup.cerina.abac.model.policy.AbstractPolicy;
import nl.schutrup.cerina.abac.model.resource.Library;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.irma.client.IrmaServerClient;
import nl.schutrup.cerina.irma.client.session.result.ServerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
public class PolicyInformationPoint {

    @Autowired
    private IrmaServerClient irmaServerClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyInformationPoint.class);

    public ServerResult getAdditionalInfo (String token) throws InvalidKeySpecException, URISyntaxException, NoSuchAlgorithmException, IllegalAccessException, IOException {
        ServerResult serverResult = irmaServerClient.retrieveSessionResultJWT(token);

        return serverResult;
    }
}
