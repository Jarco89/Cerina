package nl.schutrup.cerina.abac.service;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.irma.client.session.result.ServerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolicyEnforcementPoint {

    @Autowired
    private PolicyDecisionPoint policyDecisionPoint;

    @Autowired
    private ContextHandler contextHandler;

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyEnforcementPoint.class);

    private static final String REFUSED = "REFUSED";

    public ServerResult autoriseer(String token, String scenario, Resource resource, Operation operation) {
        try {
            // Build access request
            AccessRequest accessRequest = contextHandler.buildAccessRequest(token, resource, operation);
            PolicyResult result = policyDecisionPoint.evaluate(accessRequest, scenario);
            return doEnforcement(result, accessRequest);
        } catch (Exception e) {
            throw new IllegalStateException((e));
        }
    }

    private ServerResult doEnforcement(PolicyResult result, AccessRequest accessRequest) {
        if (PolicyResult.DENY.equals(result)) {
            LOGGER.info("Policy returned denied for {}", accessRequest.getSubject().toString());
            accessRequest.getServerResult().setStatus(REFUSED);
        }

        // Translate to native response
        return accessRequest.getServerResult();

    }

}
