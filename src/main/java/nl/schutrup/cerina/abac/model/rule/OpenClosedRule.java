package nl.schutrup.cerina.abac.model.rule;

import nl.schutrup.cerina.abac.model.action.Operation;
import nl.schutrup.cerina.abac.model.policy.PolicyResult;
import nl.schutrup.cerina.abac.model.resource.Resource;
import nl.schutrup.cerina.abac.model.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

@Component
public class OpenClosedRule extends Rule {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenClosedRule.class);

    @Autowired
    private StandardEnvironment environment;

    @Override
    public PolicyResult evaluate(Subject subject, Resource resource, Operation operation) {
        Boolean isOpen = environment.getProperty("is.open", Boolean.class);


        if (isOpen) {
            LOGGER.info("De bibliotheek is geopend.");
            return PolicyResult.PERMIT;
        }
        LOGGER.info("De bibliotheek is gesloten.");
        return PolicyResult.DENY;
    }

    @Override
    public String toString() {
        return "OpenClosedRule{" +
                "environment=" + environment +
                '}';
    }
}
